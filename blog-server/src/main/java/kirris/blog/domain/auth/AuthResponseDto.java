package kirris.blog.domain.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponseDto {

    private Long id;
    private String username;

    @Builder
    public AuthResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
