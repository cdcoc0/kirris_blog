package kirris.blog.domain.posts;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PostsRequestDto {

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
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
