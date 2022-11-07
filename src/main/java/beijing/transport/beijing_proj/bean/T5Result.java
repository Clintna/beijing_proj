package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标5计算结果：线路运行时间不均衡系数
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t5_result")
public class T5Result implements Serializable {

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
     * 线路运行时间不均衡系数（标准差）
     */
    @TableField("standard")
    private Float standard;

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


}
