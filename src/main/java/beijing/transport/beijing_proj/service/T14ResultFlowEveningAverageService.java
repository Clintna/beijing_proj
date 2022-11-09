package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T14ResultFlowEveningAverage;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T14EveningAverageResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标14计算结果：计算每一条公交线路晚高峰的平均满载率（晚高峰时间段为17:00~19:00），平均满载率=各AB路段满载率的平均值。 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T14ResultFlowEveningAverageService extends IService<T14ResultFlowEveningAverage> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T14EveningAverageResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T14ResultFlowEveningAverage> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
