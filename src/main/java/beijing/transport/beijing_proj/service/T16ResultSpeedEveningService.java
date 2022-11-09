package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T16ResultSpeedEvening;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T16EveningResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标16计算结果：计算每一条公交线路AB段在晚高峰的速度（晚高峰时间段为17:00~19:00） 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T16ResultSpeedEveningService extends IService<T16ResultSpeedEvening> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T16EveningResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T16ResultSpeedEvening> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
