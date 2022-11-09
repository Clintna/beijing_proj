package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 接驳线路运营时间匹配指数：最早班车的匹配度
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t11_result_banci_time_first_match")
public class T11ResultBanciTimeFirstMatch implements Serializable {

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
     * 公交站点顺序编号
     */
    @TableField("gongjiao_station_orderid")
    private Integer gongjiaoStationOrderid;

    /**
     * 公交站点名称
     */
    @TableField("gongjiao_station_name")
    private String gongjiaoStationName;

    /**
     * 轨道站点顺序编号
     */
    @TableField("guidao_station_orderid")
    private Integer guidaoStationOrderid;

    /**
     * 轨道站点名称
     */
    @TableField("guidao_station_name")
    private String guidaoStationName;

    /**
     * 公交最早班车到达公交站的时间
     */
    @TableField("gongjiao_station_first_time")
    private LocalTime gongjiaoStationFirstTime;

    /**
     * 轨道最早班车到达轨道站的时间
     */
    @TableField("guidao_station_first_time")
    private LocalTime guidaoStationFirstTime;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;

    /**
     * 匹配度字符值（比如：1/2）
     */
    @TableField("match_rate_string")
    private String matchRateString;

    /**
     * 匹配度数值（比如：0.5)
     */
    @TableField("match_rate_value")
    private Float matchRateValue;

    /**
     * 早班车接驳时间差=公交最早班车到达公交站的时间-轨道最早班车到达轨道站的时间（单位：分钟）
     */
    @TableField("first_sub_time")
    private Float firstSubTime;


}
