package beijing.transport.beijing_proj.user.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.shiro.realms.CustomerRealm;
import beijing.transport.beijing_proj.user.bean.UserInfo;
import beijing.transport.beijing_proj.user.service.UserInfoService;
import beijing.transport.beijing_proj.utils.JwtUtils;
import beijing.transport.beijing_proj.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-27
 */
@RestController
@RequestMapping("/userInfo")
@Slf4j
@RequiredArgsConstructor
public class UserInfoController {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private HttpSession httpSession;
    private final UserInfoService userInfoService;
    private final JwtUtils jwtUtils;
    private final CustomerRealm customerRealm;


    @PostMapping("register")
    public GongjiaoResponse register(@Valid @RequestBody UserInfo userInfo) {
        return new GongjiaoResponse().success().put("userId", userInfoService.createStUserInfo(userInfo));
    }

    @PostMapping("login")
    public GongjiaoResponse login(@RequestBody Map<String, String> map) {
        String userName = map.get("userName");
        String password = map.get("password");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(userName, password));
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            throw new RuntimeException("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            throw new RuntimeException("密码错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserInfo stUserInfo = userInfoService.getByUserName(userName);
        log.info("用户{}登录，登录时间{}", userName, new Date());
        if (null != stUserInfo) {
            userInfoService.updateLoginTime(stUserInfo);
            httpSession.setAttribute("loginUserInfo", stUserInfo);

            String token = jwtUtils.generateToken(stUserInfo.getUserId());
            if (token != null) {

                redisUtil.set(token, JSON.toJSONString(stUserInfo));
                redisUtil.expire(token, 1L, TimeUnit.DAYS);

                return Objects.requireNonNull(new GongjiaoResponse().success()
                        .put("stUserInfo", stUserInfo))
                        .put("token", token);
            }
        }
        return new GongjiaoResponse().fail().message("密码错误，登陆失败");
    }

    @PostMapping("logout")
    public GongjiaoResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        String userName = (String) subject.getPrincipal();
        try {
            subject.logout();
            Cache<Object, AuthenticationInfo> authenticationCache = customerRealm.getAuthenticationCache();
            if (null != authenticationCache) {
                authenticationCache.remove(userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GongjiaoResponse().success();
    }

}
