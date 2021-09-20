package kirris.blog.controller;

import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.exception.BadRequestException;
import kirris.blog.domain.posts.PostsRequestDto;
import kirris.blog.domain.posts.PostsResponseDto;
import kirris.blog.repository.PostsRepository;
import kirris.blog.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsRepository postsRepository;
    private final PostsService postsService;

    //포스트 등록
    @PostMapping("/auth")
    public ResponseEntity<PostsResponseDto> write(@Valid @RequestBody PostsRequestDto post, BindingResult result,
                                                  @RequestAttribute(name = "user", required = false) AuthResponseDto user) {

        //==유효성 검사
        if(result.hasErrors())
            throw new BadRequestException();

        return ResponseEntity.ok().body(postsService.write(post, user));
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
        //==html태그 제거 및 내용 길이 제한, 태그 처리
        //결과 데이터 반환
        return ResponseEntity.ok().headers(headers).body(postsService.list(page, tag));
    }

    //포스트 읽기
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostsResponseDto> read(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(postsService.read(id));
    }

    //포스트 수정
    @PutMapping("/auth/own/{id}")
    public ResponseEntity<PostsResponseDto> update(@PathVariable("id") Long id, @RequestBody PostsRequestDto post) {

        return ResponseEntity.ok().body(postsService.update(id, post));
        //merge 지양
    }

    //포스트 삭제
    @DeleteMapping("/auth/own/{id}")
    public ResponseEntity remove(@PathVariable("id") Long id) {

        postsService.remove(id);
        return ResponseEntity.noContent().build(); //204
    }
}