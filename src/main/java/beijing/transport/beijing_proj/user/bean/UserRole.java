package beijing.transport.beijing_proj.user.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-27
 */
@Getter
@Setter
@TableName("user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色代码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private Integer deptId;

    /**
     * 角色对应权限
     */
    @TableField("role_auth")
    private String roleAuth;

    /**
     * 扩展字段
     */
    @TableField("extend_info")
    private String extendInfo;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;


}
