package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标8计算结果：晚高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为17:00~19:00）
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t8_result_evening")
public class T8ResultEvening implements Serializable {

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
     * 公交站点a顺序编号
     */
    @TableField("gongjiao_station_orderid_a")
    private Integer gongjiaoStationOrderidA;

    /**
     * 公交站点b顺序编号
     */
    @TableField("gongjiao_station_orderid_b")
    private Integer gongjiaoStationOrderidB;

    /**
     * 公交站点a名称
     */
    @TableField("gongjiao_station_name_a")
    private String gongjiaoStationNameA;

    /**
     * 公交站点b名称
     */
    @TableField("gongjiao_station_name_b")
    private String gongjiaoStationNameB;

    /**
     * 公交运营速度
     */
    @TableField("gongjiao_speed")
    private Integer gongjiaoSpeed;

    /**
     * 轨道运营速度
     */
    @TableField("guidao_speed")
    private Integer guidaoSpeed;

    /**
     * 轨道运营速度/公交运营速度
     */
    @TableField("speed_rate")
    private String speedRate;

    /**
     * 运行日期
     */
    @TableField("run_date")
    private LocalDate runDate;
}
