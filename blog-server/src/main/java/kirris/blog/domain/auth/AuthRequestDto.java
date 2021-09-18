package kirris.blog.domain.auth;

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

    @Builder
    public AuthRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Auth toEntity() {
        return Auth.builder()
                .username(username)
                .password(password)
                .build();
    }

    //==encrypt password
    public void setPassword(String password) {
        this.password = password;
    }
}
