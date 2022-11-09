package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标6计算结果：早高峰公交站间速度可靠性
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t16_result_speed_morning_starndard")
public class T16ResultSpeedMorningStarndard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 线路名称
     */
    @TableField("line_name")
    private String lineName;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 线路运行速度的标准差
     */
    @TableField("standard")
    private Float standard;

    /**
     * 线路起始站点
     */
    @TableField("line_begin")
    private String lineBegin;

    /**
     * 线路终点站
     */
    @TableField("line_end")
    private String lineEnd;


}
