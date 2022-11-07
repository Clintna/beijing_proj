package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标2计算结果：计算公交线路公里客运周转量
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t2_result_gongjiao")
public class T2ResultGongjiao implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 线路名称（与t_station_main_gongjiao的）
     */
    @TableField("line_name")
    private String lineName;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 线路公里客运周转量
     */
    @TableField("exchange_distance")
    private Float exchangeDistance;

    /**
     * 班次起始站点
     */
    @TableField("line_begin")
    private String lineBegin;

    /**
     * 班次终点站
     */
    @TableField("line_end")
    private String lineEnd;

    /**
     * IC卡累计乘车长度(公里)
     */
    @TableField("ic_distance_sum")
    private String icDistanceSum;

    /**
     * IC卡累计乘车次数
     */
    @TableField("ic_card_num")
    private String icCardNum;

    /**
     * 线路方向
     */
    @TableField("direction")
    private Integer direction;


}
