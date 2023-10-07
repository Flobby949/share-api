package top.flobby.share.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.flobby.share.common.enums.BusinessExceptionEnum;
import top.flobby.share.common.exception.BusinessException;
import top.flobby.share.common.util.JwtUtil;
import top.flobby.share.common.util.SnowUtil;
import top.flobby.share.user.domain.dto.LoginDTO;
import top.flobby.share.user.domain.entity.User;
import top.flobby.share.user.domain.vo.UserLoginVO;
import top.flobby.share.user.mapper.UserMapper;

import java.util.Date;

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
     * @return {@link UserLoginVO}
     */
    public UserLoginVO login(LoginDTO loginDTO) {
        // 手机号查找用户
        User savedUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getPhone, loginDTO.getPhone()));
        // 没有此用户
        if (savedUser == null) {
            throw new BusinessException(BusinessExceptionEnum.PHONE_NOT_EXIST);
        }
        // 密码错误
        if (!savedUser.getPassword().equals(loginDTO.getPassword())) {
            throw new BusinessException(BusinessExceptionEnum.PASSWORD_ERROR);
        }
        // 登录成功
        String token = JwtUtil.createToken(savedUser.getId(), savedUser.getPhone());

        return UserLoginVO.builder()
                .user(savedUser)
                .token(token)
                .build();
    }

    /**
     * 注册
     *
     * @param loginDTO dto
     * @return {@link Long}
     */
    public Long register(LoginDTO loginDTO) {
        // 手机号查找用户
        User savedUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getPhone, loginDTO.getPhone()));
        // 手机号已被注册
        if (savedUser != null) {
            throw new BusinessException(BusinessExceptionEnum.PHONE_EXIST);
        }
        User newUser = User.builder()
                .id(SnowUtil.getSnowflakeNextId())
                .phone(loginDTO.getPhone())
                .password(loginDTO.getPassword())
                .nickname("New User")
                .roles("user")
                .avatarUrl("https://i2.100024.xyz/2023/10/07/kh58mh.webp")
                .bonus(100)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        userMapper.insert(newUser);
        return newUser.getId();
    }
}
