package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标10计算结果：公交下轨道上接驳统计数据
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t10_result")
public class T10Result implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公交线路id(lineon和lineoff）
     */
    @TableField("gongjiao_off_line_id")
    private String gongjiaoOffLineId;

    /**
     * 公交下车站点id
     */
    @TableField("gongjiao_stop_off")
    private Integer gongjiaoStopOff;

    /**
     * 轨道线路id(lineon和liineoff)
     */
    @TableField("guidao_on_line_id")
    private String guidaoOnLineId;

    /**
     * 公交上车站点id
     */
    @TableField("guidao_stop_on")
    private Integer guidaoStopOn;

    /**
     * 运行时间
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 公交下车的总数量
     */
    @TableField("gongjiao_off_sum")
    private Integer gongjiaoOffSum;

    /**
     * 轨道接驳的总数量
     */
    @TableField("guidao_on_exchange")
    private Integer guidaoOnExchange;

    /**
     * 公交下车的总数量/轨道接驳的总数量
     */
    @TableField("rate")
    private Float rate;


}
