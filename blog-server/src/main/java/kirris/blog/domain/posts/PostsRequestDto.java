package kirris.blog.domain.posts;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlStreamEventReceiverWrapper;
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
                .build();
    }

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

    public void handleTags() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tags.length; i++) {
            sb.append(tags[i] + ",");
        }
        handledTags = sb.toString();
    }
}
