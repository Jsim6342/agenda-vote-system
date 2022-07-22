package com.agenda.vote.user.interfaces.request;


import com.agenda.vote.config.security.certification.SHA256;
import com.agenda.vote.user.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;

    @NotNull(message = "의결권을 입력해주세요.")
    private Integer votingRightCount;


    public void setEncryptPassword(String encryptPassword) {
        password = encryptPassword;
    }
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .votingRightCount(votingRightCount)
                .build();
    }
}
