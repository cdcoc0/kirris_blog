package kirris.blog.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PostsRequestDto{

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String body;

    private String[] tags;
    private String handledTags;

    private String thumbnail;

    @Builder
    public PostsRequestDto(String title, String body, String[] tags) {
        this.title = title;
        this.body = body;
        this.tags = tags;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .body(body)
                .tags(handledTags)
                .thumbnail(thumbnail)
                .build();
    }

    //==XSS 방지 위해 지정된 태그 이외의 html 태그 제거
    public void sanitizeHtml() {
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowElements("h1", "h2", "b", "i", "u", "s", "p", "ul", "ol", "li", "blockquote", "a", "img")
                .allowUrlProtocols("data", "http")
                .allowAttributes("href", "name", "target").onElements("a")
                .allowAttributes("src").onElements("img")
                .allowAttributes("class").onElements("li")
                .toFactory();

        String sanitized = policy.sanitize(body);
        body = sanitized;
    }

    //==body에서 첫 번째 img태그 추출해 썸네일로 저장
    public void extractThumbnail() {
        int idx = body.indexOf("<img");
        if(idx >= 0) {
            String sub = body.substring(idx);
            sub = sub.substring(0, sub.indexOf(">") + 1);
            thumbnail = sub;
        }
    }

    //==배열로 받은 태그 문자열로 변경
    public void handleTags() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tags.length; i++) {
            sb.append(tags[i] + ",");
        }
        handledTags = sb.toString();
    }
}
