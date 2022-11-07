package beijing.transport.beijing_proj.shiro.realms;

import beijing.transport.beijing_proj.user.bean.UserInfo;
import beijing.transport.beijing_proj.user.bean.UserRole;
import beijing.transport.beijing_proj.user.service.UserInfoService;
import beijing.transport.beijing_proj.user.service.impl.UserRoleServiceImpl;
import beijing.transport.beijing_proj.utils.ApplicationContextUtil;
import beijing.transport.beijing_proj.utils.CustomerByteSource;
import com.alibaba.fastjson.JSONArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义realm
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerRealm extends AuthorizingRealm {
    @Resource
    private UserInfoService stUserInfoService;
    @Resource
    private UserRoleServiceImpl userRoleService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取主身份信息
        String principal = (String) principals.getPrimaryPrincipal();
        final UserInfo byUserName = stUserInfoService.getByUserName(principal);
        List<String> roles = JSONArray.parseArray(byUserName.getUserRoles()).toJavaList(String.class);
        if (!CollectionUtils.isEmpty(roles)) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            roles.forEach(role -> {
                simpleAuthorizationInfo.addRole(role);
                UserRoleServiceImpl stUserRoleService = (UserRoleServiceImpl) ApplicationContextUtil.getBean("userRoleService");
                UserRole stUserRoleByRoleCode = stUserRoleService.getStUserRoleByRoleCode(role);
                List<String> auths = JSONArray.parseArray(stUserRoleByRoleCode.getRoleAuth()).toJavaList(String.class);
                if (!CollectionUtils.isEmpty(auths)) {
                    auths.forEach(simpleAuthorizationInfo::addStringPermission);
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取当前登录的主题
        String principal = (String) token.getPrincipal();
        // 模拟数据库返回的数据
//        StUserInfoService stUserInfoService = (StUserInfoService) ApplicationContextUtil.getBean("stUserInfoServiceImpl");
        final UserInfo byUserName = stUserInfoService.getByUserName(principal);
        if (!ObjectUtils.isEmpty(byUserName)) {
            return new SimpleAuthenticationInfo(byUserName.getUserName(), byUserName.getPassword(), new CustomerByteSource(byUserName.getPasswordSalt()), this.getName());
        }
        return null;
    }
}
