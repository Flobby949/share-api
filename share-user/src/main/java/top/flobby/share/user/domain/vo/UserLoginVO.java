package top.flobby.share.user.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import top.flobby.share.user.domain.entity.User;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 登录返回VO
 * @create : 2023-10-07 14:07
 **/

@Data
@Builder
@NotBlank
@AllArgsConstructor
public class UserLoginVO {
    private User user;
    private String token;
}
