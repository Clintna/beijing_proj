package beijing.transport.beijing_proj.user.service;

import beijing.transport.beijing_proj.user.bean.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-27
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 用户注册
     *
     * @param userInfo
     */
    String createStUserInfo(UserInfo userInfo);

    /**
     * 根据名字获取信息
     *
     * @param userName
     * @return
     */
    UserInfo getByUserName(String userName);

    /**
     * 更新用户登录时间
     *
     * @param one
     * @return
     */
    Boolean updateLoginTime(UserInfo one);
}
