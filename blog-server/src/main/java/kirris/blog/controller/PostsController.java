package kirris.blog.controller;

import kirris.blog.service.PostsService;
import kirris.blog.domain.PostsRequestDto;
import kirris.blog.domain.PostsResponseDto;
import kirris.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/")
    public List<PostsResponseDto> list() {
        return postsRepository.findAll().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }
}
