package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标12计算结果：公交-轨道共线重复距离（只计算共线线路）	计算方法：读取t_station_gis_nearby_gongxian，计算公交ab站点的距离
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Getter
@Setter
@TableName("t12_result")
public class T12Result implements Serializable {

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
     * 轨道站点a顺序编号
     */
    @TableField("guidao_station_orderid_a")
    private Integer guidaoStationOrderidA;

    /**
     * 轨道站点b顺序编号
     */
    @TableField("guidao_station_orderid_b")
    private Integer guidaoStationOrderidB;

    /**
     * 轨道站点a名称
     */
    @TableField("guidao_station_name_a")
    private String guidaoStationNameA;

    /**
     * 轨道站点b名称
     */
    @TableField("guidao_station_name_b")
    private String guidaoStationNameB;

    /**
     * 公交站点ab的长度(公里)
     */
    @TableField("gongjiao_station_ab_distance")
    private Integer gongjiaoStationAbDistance;


}
