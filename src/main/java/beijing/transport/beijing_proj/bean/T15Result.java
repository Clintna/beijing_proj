package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 公交线路的平均运行速度
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t15_result")
public class T15Result implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 线路名称
     */
    @TableField("line_name")
    private String lineName;

    /**
     * 起始站点
     */
    @TableField("line_begin")
    private String lineBegin;

    /**
     * 结束站点
     */
    @TableField("line_end")
    private String lineEnd;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 平均速度
     */
    @TableField("speed")
    private Float speed;


}
