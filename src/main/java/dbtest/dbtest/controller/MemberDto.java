package dbtest.dbtest.controller;

import dbtest.dbtest.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MemberDto {

    private String userName;
    private String userId;
    private String userPwd;
    private String userEmail;


    public String getUserEmail() {
        return userEmail;
    }



    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public static MemberDto entityToDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setUserId(member.getUserId());
        dto.setUserEmail(member.getUserEmail());
        dto.setUserPwd(member.getUserPwd());
        dto.setUserName(member.getUserName());
        return dto;
    }

}
