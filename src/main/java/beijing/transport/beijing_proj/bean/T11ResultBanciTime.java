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
 * 接驳线路运营时间匹配指数：公交站点与轨道站点为接驳关系，记录公交站点和轨道站点最早班次时间、最晚班次时间
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t11_result_banci_time")
public class T11ResultBanciTime implements Serializable {

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
     * 公交最晚班车到达公交站的时间
     */
    @TableField("gongjiao_station_last_time")
    private LocalTime gongjiaoStationLastTime;

    /**
     * 轨道最晚班车到达轨道站的时间
     */
    @TableField("guidao_station_last_time")
    private LocalTime guidaoStationLastTime;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;


}
