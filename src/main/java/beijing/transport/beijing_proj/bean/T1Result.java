package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标1：线路中非重复的OD客运量占全部客运量的比
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-08
 */
@Getter
@Setter
@TableName("t1_result")
public class T1Result implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * IC卡的线路id（对应lineon和lineoff）
     */
    @TableField("line_id")
    private String lineId;

    /**
     * 运行时间
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 运行方向
     */
    @TableField("direction")
    private Integer direction;

    /**
     * od重复站点客运量
     */
    @TableField("flow_od_duplicate")
    private Integer flowOdDuplicate;

    /**
     * od非重复站点客运量=线路总客运量-od重复站点客运量
     */
    @TableField("flow_od_no_duplicate")
    private Integer flowOdNoDuplicate;

    /**
     * 线路总客运量
     */
    @TableField("flow_all")
    private Integer flowAll;

    /**
     * 比值：flow_od_no_dupliate/flow_all
     */
    @TableField("rate_od_no_duplicate")
    private Float rateOdNoDuplicate;

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
}
