package top.flobby.share.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.flobby.share.common.enums.BusinessExceptionEnum;
import top.flobby.share.common.exception.BusinessException;
import top.flobby.share.common.util.JwtUtil;
import top.flobby.share.common.util.SnowUtil;
import top.flobby.share.user.domain.dto.LoginDTO;
import top.flobby.share.user.domain.dto.UpdateBonusDTO;
import top.flobby.share.user.domain.entity.BonusEventLog;
import top.flobby.share.user.domain.entity.User;
import top.flobby.share.user.domain.vo.UserLoginVO;
import top.flobby.share.user.mapper.BonusEventLogMapper;
import top.flobby.share.user.mapper.UserMapper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @Resource
    private BonusEventLogMapper bonusEventLogMapper;

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
        String token = JwtUtil.createToken(savedUser.getId(), savedUser.getPhone(), savedUser.getRoles());

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


    /**
     * 根据id获取user
     *
     * @param id id
     * @return {@link User}
     */
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 更新积分
     *
     * @param bonusDTO dto
     * @return {@link User}
     */
    @Transactional(rollbackFor = Exception.class)
    public User updateUserBonus(UpdateBonusDTO bonusDTO) {
        // 获取数据
        User user = userMapper.selectById(bonusDTO.getUserId());
        Integer bonus = bonusDTO.getBonus();
        // 更新数据
        user.setBonus(user.getBonus() + bonus);
        userMapper.updateById(user);
        // 积分日志
        bonusEventLogMapper.insert(BonusEventLog.builder()
                .id(SnowUtil.getSnowflakeNextId())
                .userId(bonusDTO.getUserId())
                .value(bonus)
                .event(bonusDTO.getEvent())
                .description(bonusDTO.getDesc())
                .createTime(new Date())
                .build());
        return user;
    }

    /**
     * 积分明细
     *
     * @param userId id
     * @param pageSize size
     * @param pageNo no
     * @return {@link List}<{@link BonusEventLog}>
     */
    public List<BonusEventLog> userBonusLog(Long userId, Integer pageSize, Integer pageNo) {
        Page<BonusEventLog> page = Page.of(pageNo, pageSize);
        LambdaQueryWrapper<BonusEventLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BonusEventLog::getUserId, userId);
        return bonusEventLogMapper.selectList(page, wrapper);
    }

    /**
     * 签到
     *
     * @param userId id
     */
    @Transactional(rollbackFor = Exception.class)
    public void dailyCheck(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户异常");
        }
        LambdaQueryWrapper<BonusEventLog> wrapper = new LambdaQueryWrapper<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 构造开始时间
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        // 构造结束时间
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date end = calendar.getTime();
        wrapper.eq(BonusEventLog::getUserId, userId).eq(BonusEventLog::getEvent, "DAILY_CHECK").between(BonusEventLog::getCreateTime, start, end);
        // 查询今日是否签到
        BonusEventLog bonusEventLog = bonusEventLogMapper.selectOne(wrapper);
        if (bonusEventLog != null) {
            throw new BusinessException(BusinessExceptionEnum.ALREADY_HAS_CHECK);
        }
        // 签到成功，插入数据
        bonusEventLogMapper.insert(BonusEventLog.builder()
                .id(SnowUtil.getSnowflakeNextId())
                .userId(userId)
                .value(10)
                .event("DAILY_CHECK")
                .description("签到")
                .createTime(new Date())
                .build());

        // 添加积分
        user.setBonus(user.getBonus() + 10);
        userMapper.updateById(user);
    }
}
