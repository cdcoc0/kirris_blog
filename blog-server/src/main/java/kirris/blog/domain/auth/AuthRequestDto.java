package kirris.blog.domain.auth;

import kirris.blog.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AuthRequestDto {

    private Long id;

    @NotNull(message = "아이디를 입력하세요.")
    private String username;

    @NotNull(message = "비밀번호를 입력하세요.")
    private String password;

    @NotNull(message = "비밀번호를 확인해주세요.")
    private String confirmPassword;

    @Builder
    public AuthRequestDto(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Auth toEntity() {
        return Auth.builder()
                .username(username)
                .password(password)
                .build();
    }
}
