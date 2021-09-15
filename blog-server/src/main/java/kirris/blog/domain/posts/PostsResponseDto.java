package kirris.blog.domain.posts;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String body;
    private String tags;
    private AuthResponseDto user;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.body = entity.getBody();
        this.tags = entity.getTags();
        this.user = entity.getAuth().deletePassword();
    }

    public void shortenTitleAndBody() {
        if(title.length() > 20)
            title = title.substring(0, 25) + " ...";

        if(body.length() > 100)
            body = body.substring(0, 110) + " ...";
    }
}
