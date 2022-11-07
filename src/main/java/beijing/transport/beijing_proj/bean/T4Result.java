package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标4计算结果：线路早晚高峰与全天运营速度系数比
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t4_result")
public class T4Result implements Serializable {

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
     * 线路起点站
     */
    @TableField("line_begin")
    private String lineBegin;

    /**
     * 线路终点站
     */
    @TableField("line_end")
    private String lineEnd;

    /**
     * 全天运营速度（公里/小时）
     */
    @TableField("speed_normal")
    private Float speedNormal;

    /**
     * 早高峰速度（公里/小时）
     */
    @TableField("speed_morning")
    private Float speedMorning;

    /**
     * 晚高峰速度（公里/小时）
     */
    @TableField("speed_evening")
    private Float speedEvening;

    /**
     * 非早晚高峰全天时间段的运营速度（公里/小时）
     */
    @TableField("speed_idle")
    private Float speedIdle;


}
