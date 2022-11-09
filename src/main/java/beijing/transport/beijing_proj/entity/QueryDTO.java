package beijing.transport.beijing_proj.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/09/26
 * @Description:
 */
@Data
public class QueryDTO {
    /**
     * 当前页
     */
    private int page;
    /**
     * 每一页限制数量
     */
    private int limit;
    /**
     * 线路名称
     */
//    @NotBlank(message = "线路名称不能为空")
    private String lineName;
    /**
     * 线路方向
     */
    private int direction;
    /**
     * 日期集合
     */
    private String dates;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 起点
     */
    private String lineBegin;
    /**
     * 终点
     */
    private String lineEnd;

}
