package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标13：接驳站点之间的步行距离
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t13_result_walk")
public class T13ResultWalk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 轨道站点名称
     */
    @TableField("guidao_station_name")
    private String guidaoStationName;

    /**
     * 轨道线路名称
     */
    @TableField("guidao_line_name")
    private String guidaoLineName;

    /**
     * 公交站点名称
     */
    @TableField("gongjiao_station_name")
    private String gongjiaoStationName;

    /**
     * 公交线路名称
     */
    @TableField("gongjiao_line_name")
    private String gongjiaoLineName;

    /**
     * 轨道站点与公交站点的步行距离
     */
    @TableField("walk_distance")
    private Float walkDistance;

    /**
     * 轨道站点与公交站点的直线距离
     */
    @TableField("direct_distance")
    private Float directDistance;

    /**
     * 轨道线路起始站点
     */
    @TableField("guidao_line_begin")
    private String guidaoLineBegin;

    /**
     * 轨道线路结束站点
     */
    @TableField("guidao_line_end")
    private String guidaoLineEnd;

    /**
     * 公交线路起始站点
     */
    @TableField("gongjiao_line_begin")
    private String gongjiaoLineBegin;

    /**
     * 根据线路结束站点
     */
    @TableField("gongjiao_line_end")
    private String gongjiaoLineEnd;

    /**
     * 轨道站点顺序编号
     */
    @TableField("guidao_station_orderid")
    private Integer guidaoStationOrderid;

    /**
     * 公交站点顺序报告
     */
    @TableField("gongjiao_station_orderid")
    private Integer gongjiaoStationOrderid;


}
