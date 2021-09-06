package kirris.blog.controller;

import kirris.blog.PostsService;
import kirris.blog.domain.PostsRequestDto;
import kirris.blog.domain.PostsResponseDto;
import kirris.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostsService postService;
    private final PostRepository postrepository;

    @Transactional
    @PostMapping("/")
    public Long write(@RequestBody PostsRequestDto posts) {
        return postrepository.save(posts);
        //400 Bad request, 500 Internal Server Error
    }
}
