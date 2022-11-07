package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标3计算结果：线路客流不均衡系数（轨道）
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t3_result_guidao")
public class T3ResultGuidao implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 线路运行时间不均衡系数（标准差）
     */
    @TableField("standard")
    private Float standard;

    /**
     * 线路名称
     */
    @TableField("line_name")
    private String lineName;

    /**
     * 线路起始站点
     */
    @TableField("line_begin")
    private String lineBegin;

    /**
     * 线路结束站点
     */
    @TableField("line_end")
    private String lineEnd;


}
