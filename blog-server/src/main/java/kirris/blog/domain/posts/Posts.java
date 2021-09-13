package kirris.blog.domain.posts;

import kirris.blog.domain.auth.Auth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String tags; //이게 수정이 될 지...? HashMap / ArrayList

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Auth auth;

    @Builder
    public Posts(Long id, String title, String body, String tags) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.tags = tags;
    }

    public void update(String title, String body, String tags) {
        this.title = title;
        this.body = body;
        this.tags = tags;
    }
}
