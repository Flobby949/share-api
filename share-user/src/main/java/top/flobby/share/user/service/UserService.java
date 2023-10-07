package top.flobby.share.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.flobby.share.user.domain.dto.LoginDTO;
import top.flobby.share.user.domain.entity.User;
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

    /**
     * 登录
     *
     * @param loginDTO dto
     * @return {@link User}
     */
    public User login(LoginDTO loginDTO) {
        // 手机号查找用户
        User savedUser = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, loginDTO.getPhone()));
        // 没有此用户
        if (savedUser == null) {
            throw new RuntimeException("手机号不存在");
        }
        // 密码错误
        if (!savedUser.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        // 登录成功
        return savedUser;
    }
}