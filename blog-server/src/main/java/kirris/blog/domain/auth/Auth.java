package kirris.blog.domain.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "user")
public class Auth{

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Size(min = 3, max = 20)
    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @Builder
    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //==비밀번호 제외 유저 정보 반환
    public AuthResponseDto deletePassword() {
        return AuthResponseDto.builder()
                .id(id)
                .username(username)
                .build();
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}
