package top.flobby.share.user.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.flobby.share.user.mapper.UserMapper;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 用户业务类
 * @create : 2023-10-07 12:48
 **/

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public Long count() {
        return userMapper.selectCount(null);
    }
}
