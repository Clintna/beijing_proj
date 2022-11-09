package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标14计算结果：计算每一条公交线路晚高峰的平均满载率（晚高峰时间段为17:00~19:00），平均满载率=各AB路段满载率的平均值。
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t14_result_flow_evening_average")
public class T14ResultFlowEveningAverage implements Serializable {

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
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 平均满载率=各AB路段满载率的平均值。
     */
    @TableField("full_rate_average")
    private Float fullRateAverage;

    /**
     * 运行方向
     */
    @TableField("direction")
    private Integer direction;


}
