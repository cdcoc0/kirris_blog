package kirris.blog.controller;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import kirris.blog.exception.BadRequestException;
import kirris.blog.exception.NotFoundException;
import kirris.blog.repository.AuthRepository;
import kirris.blog.service.PostsService;
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
public class PostsController { //try/catch가 필요한가...?

    private final PostsService postsService;
    private final PostsRepository postsRepository;
    private final AuthRepository authRepository;

    //포스트 등록
    @Transactional
    @PostMapping("/auth")
    public ResponseEntity<PostsResponseDto> write(@Valid @RequestBody PostsRequestDto posts, BindingResult result,
                                                  @RequestAttribute(name = "user", required = false) AuthResponseDto user) {
        if(result.hasErrors())
            throw new BadRequestException();

        Auth userInfo = authRepository.findById(user.getId());
        return ResponseEntity.ok().body(new PostsResponseDto(postsRepository.save(posts, userInfo)));
        //return postsRepository.save(posts);
        //500 Internal Server Error(try/catch)
    }

    //포스트 목록
    @Transactional(readOnly = true)
    @GetMapping("/")
    public ResponseEntity<List<PostsResponseDto>> list(@RequestParam(defaultValue = "1") int page) {
        if(page < 1)
            throw new BadRequestException();

        //==http 커스텀 헤더 last-page 설정==//
        String countPosts = postsRepository.countAll() % 9 == 0 ?
                String.valueOf(postsRepository.countAll() / 9) : String.valueOf(postsRepository.countAll() / 9 + 1);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Last-Page", countPosts);
        //response.addIntHeader("Last-Page",  countPosts);

        //==내용 길이 제한 필요==//


        List<PostsResponseDto> responseBody = postsRepository.findAll(page).stream()
                                                .map(PostsResponseDto::new)
                                                .collect(Collectors.toList());

        return ResponseEntity.ok().headers(headers).body(responseBody);
    }

    //포스트 읽기
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<PostsResponseDto> read(@PathVariable("id") Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        return ResponseEntity.ok().body(new PostsResponseDto(entity));
        //return new PostsResponseDto(entity);
    }

    //포스트 수정
    @Transactional
    @PutMapping("/auth/{id}")
    public ResponseEntity<PostsResponseDto> update(@PathVariable("id") Long id, @RequestBody PostsRequestDto post) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        entity.update(post.getTitle(), post.getBody(), post.getTags());
        return ResponseEntity.ok().body(new PostsResponseDto(entity));
        //return new PostsResponseDto(entity);
        //merge 지양
    }

    //포스트 삭제
    @Transactional
    @DeleteMapping("/auth/{id}")
    public ResponseEntity remove(@PathVariable("id") Long id) {
        postsRepository.delete(id);
        return ResponseEntity.noContent().build(); //204
        //return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}