package kirris.blog.controller;

import kirris.blog.domain.Posts;
import kirris.blog.service.PostsService;
import kirris.blog.domain.PostsRequestDto;
import kirris.blog.domain.PostsResponseDto;
import kirris.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostsController {
    private final PostsService postService;
    private final PostsRepository postsRepository;

    @Transactional
    @PostMapping("/")
    public Long write(@RequestBody PostsRequestDto posts) {
        return postsRepository.save(posts);
        //400 Bad request, 500 Internal Server Error
    }

    @Transactional(readOnly = true)
    @GetMapping("/")
    public List<PostsResponseDto> list() {
        return postsRepository.findAll().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public PostsResponseDto read(@PathVariable("id") Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id) {
        postsRepository.delete(id);
        //status = 204; No Content
    }
}
