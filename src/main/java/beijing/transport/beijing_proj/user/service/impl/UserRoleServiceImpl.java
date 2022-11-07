package beijing.transport.beijing_proj.user.service.impl;

import beijing.transport.beijing_proj.user.bean.UserRole;
import beijing.transport.beijing_proj.user.mapper.UserRoleMapper;
import beijing.transport.beijing_proj.user.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-27
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public UserRole getStUserRoleByRoleCode(String roleCode) {
        return getOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleCode, roleCode));

    }

    @Override
    public UserRole getStUserRoleByRoleId(String roleId) {
        return getById(roleId);
    }
}
