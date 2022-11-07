package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.bean.T8ResultMorning;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import beijing.transport.beijing_proj.entity.T8MorningResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标8计算结果：早高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为7:00~9:00） 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T8ResultMorningService extends IService<T8ResultMorning> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T8MorningResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T8ResultMorning> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
