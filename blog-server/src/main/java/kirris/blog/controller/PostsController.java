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

        //==유효성 검사
        if(result.hasErrors())
            throw new BadRequestException();

        //==작성자 정보 가져오기
        Auth userInfo = authRepository.findById(user.getId())
                .orElseThrow(() -> new UnauthorizedException("user not found"));

        //==html태그 검증, 태그 문자열 변경, 썸네일 추출
        post.sanitizeHtml();
        post.handleTags();
        post.getThumbnail();

        //==저장
        return ResponseEntity.ok().body(new PostsResponseDto(postsRepository.save(post, userInfo)));
    }

    //포스트 목록
    @Transactional(readOnly = true)
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostsResponseDto>> list(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(required = false) String tag) {

        //==파라미터 유효성
        if(page < 1)
            throw new BadRequestException();

        //==헤더에 last-page, count-posts 저장
        long counts = postsRepository.countAll();
        String countPosts = counts % 9 == 0 ?
                String.valueOf(counts / 9) : String.valueOf(counts / 9 + 1);

        if(countPosts.equals("0"))
            countPosts = "1";


        //==last page를 초과하는 페이지 요청시 게시글이 존재하지 않습니다 반환 필요==//


        HttpHeaders headers = new HttpHeaders();
        headers.add("last-page", countPosts); //String으로 저장
        headers.add("count-posts", String.valueOf(counts)); //전체 게시글 수

        //==데이터 가져오기
        List<Posts> results = tag == null ? postsRepository.findAll(page): postsRepository.findAllByTag(page, tag);
        List<PostsResponseDto> responseBody = results.stream().map(PostsResponseDto::new).collect(Collectors.toList());

        //==html태그 제거 및 내용 길이 제한, 태그 처리
        responseBody.forEach(post -> {
            post.removeHtmlAndShortenTitleAndBody();
            post.tagsToArray();
        });

        //결과 데이터 반환
        return ResponseEntity.ok().headers(headers).body(responseBody);
    }

    //포스트 읽기
    @Transactional(readOnly = true)
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostsResponseDto> read(@PathVariable("id") Long id) {

        //==글번호로 해당 게시글 가져오기
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        //==응답 dto에 저장, 태그 처리
        PostsResponseDto post = new PostsResponseDto(entity);
        post.tagsToArray();

        return ResponseEntity.ok().body(post);
    }

    //포스트 수정
    @Transactional
    @PutMapping("/auth/own/{id}")
    public ResponseEntity<PostsResponseDto> update(@PathVariable("id") Long id, @RequestBody PostsRequestDto post) {

        //==글번호로 게시글 찾기
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        //==태그 처리, 찾은 게시글 수정
        post.handleTags();
        entity.update(post.getTitle(), post.getBody(), post.getHandledTags());

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