package kirris.blog.controller;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import kirris.blog.exception.BadRequestException;
import kirris.blog.exception.NotFoundException;
import kirris.blog.exception.UnauthorizedException;
import kirris.blog.repository.AuthRepository;
import kirris.blog.domain.posts.PostsRequestDto;
import kirris.blog.domain.posts.PostsResponseDto;
import kirris.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsRepository postsRepository;
    private final AuthRepository authRepository;

    //포스트 등록
    @Transactional
    @PostMapping("/auth")
    public ResponseEntity<PostsResponseDto> write(@Valid @RequestBody PostsRequestDto post, BindingResult result,
                                                  @RequestAttribute(name = "user", required = false) AuthResponseDto user) {
        if(result.hasErrors())
            throw new BadRequestException();

        Auth userInfo = authRepository.findById(user.getId())
                .orElseThrow(() -> new UnauthorizedException("user not found"));

        post.sanitizeHtml();
        post.handleTags();

        return ResponseEntity.ok().body(new PostsResponseDto(postsRepository.save(post, userInfo)));
    }

    //포스트 목록
    @Transactional(readOnly = true)
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostsResponseDto>> list(@RequestParam(defaultValue = "1") int page) {
        if(page < 1)
            throw new BadRequestException();

        //==http 커스텀 헤더 last-page 설정==//
        long counts = postsRepository.countAll();
        String countPosts = counts % 9 == 0 ?
                String.valueOf(counts / 9) : String.valueOf(counts / 9 + 1);

        if(countPosts.equals("0"))
            countPosts = "1";

        HttpHeaders headers = new HttpHeaders();
        headers.add("last-page", countPosts); //String으로 저장
        headers.add("count-posts", String.valueOf(counts));

        List<PostsResponseDto> responseBody = postsRepository.findAll(page).stream()
                                                .map(PostsResponseDto::new)
                                                .collect(Collectors.toList());

        //==제목, 내용 길이 제한==//
        responseBody.forEach(post -> {
            post.removeHtmlAndShortenTitleAndBody();
            post.tagsToArray();
        });

        return ResponseEntity.ok().headers(headers).body(responseBody);
    }

    //포스트 읽기
    @Transactional(readOnly = true)
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostsResponseDto> read(@PathVariable("id") Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        PostsResponseDto post = new PostsResponseDto(entity);
        post.tagsToArray();
        return ResponseEntity.ok().body(post);
    }

    //포스트 수정
    @Transactional
    @PutMapping("/auth/own/{id}")
    public ResponseEntity<PostsResponseDto> update(@PathVariable("id") Long id, @RequestBody PostsRequestDto post) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        entity.update(post.getTitle(), post.getBody()/*, post.getTags()*/);
        return ResponseEntity.ok().body(new PostsResponseDto(entity));
        //merge 지양
    }

    //포스트 삭제
    @Transactional
    @DeleteMapping("/auth/own/{id}")
    public ResponseEntity remove(@PathVariable("id") Long id) {
        postsRepository.delete(id);
        return ResponseEntity.noContent().build(); //204
    }
}