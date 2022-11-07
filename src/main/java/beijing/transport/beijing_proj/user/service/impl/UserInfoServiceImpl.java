package beijing.transport.beijing_proj.user.service.impl;

import beijing.transport.beijing_proj.shiro.constant.ShiroConstant;
import beijing.transport.beijing_proj.user.bean.UserInfo;
import beijing.transport.beijing_proj.user.mapper.UserInfoMapper;
import beijing.transport.beijing_proj.user.service.UserInfoService;
import beijing.transport.beijing_proj.utils.SaltUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-27
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public String createStUserInfo(UserInfo userInfo) {
        final UserInfo byUserName = getByUserName(userInfo.getUserName());
        if (null != byUserName) {
            throw new RuntimeException("用户名重复");
        }
        String salt = SaltUtil.getSalt(ShiroConstant.SALT_LENGTH);
        userInfo.setPasswordSalt(salt);
        Md5Hash password = new Md5Hash(userInfo.getPassword(), salt, ShiroConstant.HASH_ITERATORS);
        userInfo.setPassword(password.toHex());
        userInfo.setUserId(UUID.randomUUID().toString());
        userInfo.setModifyTime(LocalDateTime.now());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setLastLogin(LocalDateTime.now());
        try {
            save(userInfo);
        } catch (Exception e) {
            log.error("创建用户异常,e:{},用户信息:{}", e.getStackTrace(), JSONObject.toJSONString(userInfo));
            throw new RuntimeException("创建用户异常，请检查相关信息");
        }
        return userInfo.getUserId();
    }

    @Override
    public UserInfo getByUserName(String userName) {
        return getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUserName, userName));
    }

    @Override
    public Boolean updateLoginTime(UserInfo one) {
        one.setLastLogin(LocalDateTime.now());
        return updateById(one);
    }
}
