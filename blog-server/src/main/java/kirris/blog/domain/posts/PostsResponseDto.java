package kirris.blog.domain.posts;

import kirris.blog.domain.auth.AuthResponseDto;
import lombok.Getter;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.Date;
import java.util.StringTokenizer;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String body;
    private String handledTags;
    private String[] tags;
    private Date publishedDate;
    private String thumbnail;
    private AuthResponseDto user;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.body = entity.getBody();
        this.handledTags = entity.getTags();
        this.user = entity.getAuth().deletePassword();
        this.publishedDate = entity.getPublished_date();
        this.thumbnail = entity.getThumbnail();
    }

    //==body에서 html태그 삭제, 길이 제한
    public void removeHtmlAndShortenTitleAndBody() {
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowElements()
                .toFactory();

        body = policy.sanitize(body);

        if(title.length() > 25)
            title = title.substring(0, 25) + " ...";

        if(body.length() > 110)
            body = body.substring(0, 110) + " ...";
    }

    //==태그 배열로 정렬
    public void tagsToArray() {
        StringTokenizer st = new StringTokenizer(handledTags, ",");
        tags = new String[st.countTokens()];

        for(int i = 0; i < tags.length; i++) {
            tags[i] = st.nextToken();
        }
    }
}
