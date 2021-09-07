package kirris.blog.controller;

import kirris.blog.domain.Posts;
import kirris.blog.exception.BadRequestException;
import kirris.blog.exception.NotFoundException;
import kirris.blog.service.PostsService;
import kirris.blog.domain.PostsRequestDto;
import kirris.blog.domain.PostsResponseDto;
import kirris.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostsController { //try/catch가 필요한가...?
    private final PostsService postService;
    private final PostsRepository postsRepository;

    @Transactional
    @PostMapping("/")
    public Long write(@Valid @RequestBody PostsRequestDto posts, BindingResult result) {
        if(result.hasErrors()) {
            throw new BadRequestException();
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return postsRepository.save(posts);
        //500 Internal Server Error(try/catch)
    }

    @Transactional(readOnly = true)
    @GetMapping("/")
    public List<PostsResponseDto> list(@RequestParam(defaultValue = "1") int page, HttpServletResponse response) {
        if(page < 1)
            throw new BadRequestException();

        int countPosts = postsRepository.countAll() % 9 == 0 ?
                Math.toIntExact(postsRepository.countAll()) / 9 : Math.toIntExact(postsRepository.countAll() / 9 + 1);
        response.addIntHeader("Last-Page",  countPosts);
        //http 커스텀 헤더 last-page 설정

        return postsRepository.findAll(page).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public PostsResponseDto read(@PathVariable("id") Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));
        return new PostsResponseDto(entity);
    }

    @Transactional
    @PutMapping("/{id}")
    public PostsResponseDto update(@PathVariable("id") Long id, @RequestBody PostsRequestDto post) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(id));

        entity.update(post.getTitle(), post.getBody(), post.getTags());
        return new PostsResponseDto(entity);
        //merge 지양
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id) {
        postsRepository.delete(id);
        //status = 204 No Content //error: 404
        //status만 바꿀 수는 없는가
    }
}

//에러 말고 http status만 바꿀 수도 있나..?
