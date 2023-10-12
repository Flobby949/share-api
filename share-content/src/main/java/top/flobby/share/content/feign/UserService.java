package top.flobby.share.content.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.content.feign.model.UpdateBonusDTO;
import top.flobby.share.content.feign.model.User;

/**
 * @author : Flobby
 * @program : share-api
 * @description : feign 调用 user 模块
 * @create : 2023-10-12 09:04
 **/

@FeignClient(value = "user-service", path = "user")
public interface UserService {

    @GetMapping("{id}")
    CommonResp<User> getUserById(@PathVariable Long id);

    @PutMapping("bonus")
    CommonResp<User> updateBonus(@RequestBody UpdateBonusDTO updateBonusDTO);
}
