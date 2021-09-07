package kirris.blog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posts {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String tags; //이게 수정이 될 지...? HashMap / ArrayList

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
