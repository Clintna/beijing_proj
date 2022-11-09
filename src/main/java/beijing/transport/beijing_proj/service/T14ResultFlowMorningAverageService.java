package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T14ResultFlowMorningAverage;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T14MorningAverageResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标14计算结果：计算每一条公交线路早高峰的平均满载率（晚高峰时间段为7:00~9:00），平均满载率=各AB路段满载率的平均值。 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T14ResultFlowMorningAverageService extends IService<T14ResultFlowMorningAverage> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T14MorningAverageResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T14ResultFlowMorningAverage> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
