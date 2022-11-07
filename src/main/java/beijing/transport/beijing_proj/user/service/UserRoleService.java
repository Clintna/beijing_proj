package beijing.transport.beijing_proj.user.service;

import beijing.transport.beijing_proj.user.bean.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-27
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据角色代码名获得角色信息
     *
     * @param roleCode
     * @return
     */
    UserRole getStUserRoleByRoleCode(String roleCode);

    /**
     * 根据角色id获得角色信息
     *
     * @param roleId
     * @return
     */
    UserRole getStUserRoleByRoleId(String roleId);
}
