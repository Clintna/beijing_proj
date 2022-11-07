package beijing.transport.beijing_proj.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 指标7计算结果：公交-轨道共线重复距离比
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Getter
@Setter
@TableName("t7_result")
public class T7Result implements Serializable {

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
     * 轨道站点起始站点
     */
    @TableField("guidao_line_begin")
    private String guidaoLineBegin;

    /**
     * 轨道站点结束站点
     */
    @TableField("guidao_line_end")
    private String guidaoLineEnd;

    /**
     * 共线长度(公里)
     */
    @TableField("gongxian_distance")
    private Float gongxianDistance;

    /**
     * 公交长度(公里)
     */
    @TableField("gongjiao_distance")
    private Float gongjiaoDistance;

    /**
     * 公交共线长度/公交总长度
     */
    @TableField("distance_rate")
    private Float distanceRate;


}
