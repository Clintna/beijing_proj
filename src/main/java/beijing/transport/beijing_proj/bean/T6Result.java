package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标6计算结果：线路发车间隔不均衡系数
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t6_result")
public class T6Result implements Serializable {

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
     * 班次间隔时间不均衡系数（标准差）
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
