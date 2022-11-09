package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标14计算结果：计算每一条公交线路AB段在晚高峰的断面流量（晚高峰时间段为17:00~19:00）
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t14_result_flow_evening")
public class T14ResultFlowEvening implements Serializable {

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
     * 公交站点a的断面流量
     */
    @TableField("flow_a")
    private Integer flowA;

    /**
     * 公交站点b的断面流量
     */
    @TableField("flow_b")
    private Integer flowB;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 公交站点b的断面流量-公交站点a的断面流量
     */
    @TableField("flow_ab")
    private Integer flowAb;

    /**
     * ab路段的满载率=flow_ab/公交车的定员人数
     */
    @TableField("flow_ab_rate")
    private Float flowAbRate;

    /**
     * 运行方向
     */
    @TableField("direction")
    private Integer direction;


}
