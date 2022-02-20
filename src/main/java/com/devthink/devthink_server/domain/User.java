package com.devthink.devthink_server.domain;


import com.devthink.devthink_server.dto.UserProfileData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;



@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private String name = "";

    private String nickname;

    private String phoneNum;

    private String role;

    private String blogAddr;

    private String gitNickname;

    private Integer point;

    @Builder.Default
    private String imageUrl = "";

    @Builder.Default
    private boolean deleted = false;

    @Builder.Default
    private Integer reported = 0;

    public void changeWith(User source) {
        nickname = source.getNickname();
        role = source.getRole();
        imageUrl = source.getImageUrl();
        gitNickname = source.getGitNickname();
        blogAddr = source.getBlogAddr();
        password = source.getPassword();
        point = source.getPoint();
    }

    public void destroy() {
        deleted = true;
    }

    public boolean authenticate(String password) {
        return !deleted && password.equals(this.password);
    }

    public void upPoint(int point) {
        this.point += point;
    }

    public void downPoint(int point) {
        this.point -= point;
    }

    /**
     * User 객체를 UserProfileData 객체로 변환합니다.
     * 탈퇴한 회원의 경우 삭제여부(deleted)컬럼 외의 나머지 정보는 null 값으로 채워 전달합니다.
     *
     * @return 변환된 UserProfileData 객체
     */
    public UserProfileData toUserProfileData() {
        if (isDeleted()) {
            return UserProfileData.builder()
                    .deleted(deleted)
                    .build();
        } else {
            return UserProfileData.builder()
                    .id(id)
                    .nickname(nickname)
                    .imageUrl(imageUrl)
                    .deleted(deleted)
                    .build();
        }
    }

    public void setReported() {
        reported = reported + 1;
    }

    public void changePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);

    }
}
