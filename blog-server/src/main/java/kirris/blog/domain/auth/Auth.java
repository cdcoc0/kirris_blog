package kirris.blog.domain.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class Auth {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotNull(message = "아이디를 입력하세요.")
    @Size(min = 3, max = 20)
    @Column(length = 20, nullable = false, unique = true)
    private String username;

//    @NotNull(message = "비밀번호를 입력하세요.")
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    //==post랑 엔티티 조인 필요==//

    @Builder
    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //비밀번호 제외 유저 정보 반환
    public AuthResponseDto deletePassword() {
        return AuthResponseDto.builder()
                .id(id)
                .username(username)
                .build();
    }

    //비밀번호 확인
    public boolean matchPassword(String confirmPassword) {
        return true;
    }
}
