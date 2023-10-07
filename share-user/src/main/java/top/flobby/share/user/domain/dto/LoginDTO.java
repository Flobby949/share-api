package top.flobby.share.user.domain.dto;

import lombok.Data;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 登录dto
 * @create : 2023-10-07 12:56
 **/

@Data
public class LoginDTO {
    private String phone;
    private String password;
}
