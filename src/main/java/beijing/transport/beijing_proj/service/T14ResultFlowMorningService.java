package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T14ResultFlowMorning;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T14MorningResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标14计算结果：计算每一条公交线路AB段在早高峰的断面流量（晚高峰时间段为7:00~9:00） 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T14ResultFlowMorningService extends IService<T14ResultFlowMorning> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T14MorningResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T14ResultFlowMorning> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
