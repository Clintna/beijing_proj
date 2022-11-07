package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标9计算结果：公交和轨道共线区域满载率的比值
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t9_result")
public class T9Result implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公交线路名称
     */
    @TableField("gongjiao_line_name")
    private String gongjiaoLineName;

    /**
     * 公交线路起始站点
     */
    @TableField("gongjiao_line_begin")
    private String gongjiaoLineBegin;

    /**
     * 公交线路结束站点
     */
    @TableField("gongjiao_line_end")
    private String gongjiaoLineEnd;

    /**
     * 轨道站点名称
     */
    @TableField("guidao_line_name")
    private String guidaoLineName;

    /**
     * 轨道站点起始名称
     */
    @TableField("guidao_line_begin")
    private String guidaoLineBegin;

    /**
     * 轨道站点结束名称
     */
    @TableField("guidao_line_end")
    private String guidaoLineEnd;

    /**
     * 共线公交站点顺序编号
     */
    @TableField("gongxian_gongjiao_station_orderid")
    private Integer gongxianGongjiaoStationOrderid;

    /**
     * 共线公交站点名称
     */
    @TableField("gongxian_gongjiao_station_name")
    private String gongxianGongjiaoStationName;

    /**
     * 共线轨道站点顺序编号
     */
    @TableField("gongxian_guidao_station_orderid")
    private Integer gongxianGuidaoStationOrderid;

    /**
     * 共线轨道站点名称
     */
    @TableField("gongxian_guidao_station_name")
    private String gongxianGuidaoStationName;

    /**
     * 运行时间
     */
    @TableField("run_date")
    private LocalDateTime runDate;

    /**
     * 共线公交站点满载率
     */
    @TableField("gongxian_gongjiao_station_load_rate")
    private Float gongxianGongjiaoStationLoadRate;

    /**
     * 共线轨道站点满载率
     */
    @TableField("gongxian_guidao_station_load_rate")
    private Float gongxianGuidaoStationLoadRate;


}
