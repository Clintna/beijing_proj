package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标9计算结果：公交ab站点和轨道ab站点为共线关系，计算公交ab和轨道ab满载率
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-08
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
     * 共线公交站点顺序编号a
     */
    @TableField("gongxian_gongjiao_station_orderid_a")
    private Integer gongxianGongjiaoStationOrderidA;

    /**
     * 共线公交站点名称a
     */
    @TableField("gongxian_gongjiao_station_name_a")
    private String gongxianGongjiaoStationNameA;

    /**
     * 共线轨道站点顺序编号a
     */
    @TableField("gongxian_guidao_station_orderid_a")
    private Integer gongxianGuidaoStationOrderidA;

    /**
     * 共线轨道站点名称a
     */
    @TableField("gongxian_guidao_station_name_a")
    private String gongxianGuidaoStationNameA;

    /**
     * 运行时间
     */
    @TableField("run_date")
    private LocalDateTime runDate;

    /**
     * 共线公交站点a满载率
     */
    @TableField("gongxian_gongjiao_station_load_rate_a")
    private Float gongxianGongjiaoStationLoadRateA;

    /**
     * 共线轨道站点a满载率
     */
    @TableField("gongxian_guidao_station_load_rate_a")
    private Float gongxianGuidaoStationLoadRateA;

    /**
     * 共线公交站点顺序编号b
     */
    @TableField("gongxian_gongjiao_station_orderid_b")
    private Integer gongxianGongjiaoStationOrderidB;

    /**
     * 共线公交站点名称b
     */
    @TableField("gongxian_gongjiao_station_name_b")
    private String gongxianGongjiaoStationNameB;

    /**
     * 共线轨道站点顺序编号b
     */
    @TableField("gongxian_guidao_station_orderid_b")
    private Integer gongxianGuidaoStationOrderidB;

    /**
     * 共线轨道站点名称b
     */
    @TableField("gongxian_guidao_station_name_b")
    private String gongxianGuidaoStationNameB;

    /**
     * 共线公交站点b满载率
     */
    @TableField("gongxian_gongjiao_station_load_rate_b")
    private Float gongxianGongjiaoStationLoadRateB;

    /**
     * 共线轨道站点b满载率
     */
    @TableField("gongxian_guidao_station_load_rate_b")
    private Float gongxianGuidaoStationLoadRateB;


}
