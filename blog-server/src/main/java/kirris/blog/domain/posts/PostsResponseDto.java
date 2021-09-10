package kirris.blog.domain.posts;

import kirris.blog.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String body;
    private String tags;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.body = entity.getBody();
        this.tags = entity.getTags();
    }
}