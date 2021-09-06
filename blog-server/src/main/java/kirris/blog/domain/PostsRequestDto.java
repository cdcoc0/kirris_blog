package kirris.blog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsRequestDto {
    private String title;
    private String body;
    private String tags;

    @Builder
    public PostsRequestDto(String title, String body, String tags) {
        this.title = title;
        this.body = body;
        this.tags = tags;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .body(body)
                .tags(tags)
                .build();
    }
}
