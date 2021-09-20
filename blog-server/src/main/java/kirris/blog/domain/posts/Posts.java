package kirris.blog.domain.posts;

import kirris.blog.domain.auth.Auth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posts {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(length = 500)
    private String tags;

    @Column(nullable = false)
    private Date published_date;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    //다대일 단방향
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Auth auth;

    @Builder
    public Posts(Long id, String title, String body, String tags, String thumbnail) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.published_date = new Date();
        this.thumbnail = thumbnail;
    }

    public void update(String title, String body, String handledTags, String thumbnail) {
        this.title = title;
        this.body = body;
        this.tags = handledTags;
        this.thumbnail = thumbnail;
    }

    public void addAuth(Auth auth) {
        this.auth = auth;
    }
}
