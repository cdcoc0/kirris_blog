package kirris.blog.domain.posts;

import kirris.blog.domain.auth.Auth;
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

    private Auth auth;

    @Builder
    public PostsRequestDto(String title, String body, String tags, Auth auth) {
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.auth = auth;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .body(body)
                .tags(tags)
                .auth(auth)
                .build();
    }
}
