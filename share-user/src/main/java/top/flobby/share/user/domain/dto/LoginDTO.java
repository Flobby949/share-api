package top.flobby.share.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 登录dto
 * @create : 2023-10-07 12:56
 **/

@Data
public class LoginDTO {

    @NotBlank(message = "[手机号] 不能为空")
    @Size(min = 11, max = 11, message = "[手机号] 非法")
    private String phone;

    @NotBlank(message = "[密码] 不能为空")
    private String password;
}
