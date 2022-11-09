package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标16计算结果：计算每一条公交线路AB段在早高峰的速度（晚高峰时间段为7:00~9:00）
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t16_result_speed_morning")
public class T16ResultSpeedMorning implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公交线路名称
     */
    @TableField("line_name")
    private String lineName;

    /**
     * 公交线路起始站点
     */
    @TableField("line_begin")
    private String lineBegin;

    /**
     * 公交线路结束站点
     */
    @TableField("line_end")
    private String lineEnd;

    /**
     * 公交站点a顺序编号
     */
    @TableField("station_orderid_a")
    private Integer stationOrderidA;

    /**
     * 公交站点b顺序编号
     */
    @TableField("station_orderid_b")
    private Integer stationOrderidB;

    /**
     * 公交站点a名称
     */
    @TableField("station_name_a")
    private String stationNameA;

    /**
     * 公交站点b名称
     */
    @TableField("station_name_b")
    private String stationNameB;

    /**
     * ab站点的运行速度（公里/小时）
     */
    @TableField("speed")
    private Float speed;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;


}
