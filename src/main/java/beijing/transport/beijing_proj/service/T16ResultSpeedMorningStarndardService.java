package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T16ResultSpeedMorningStarndard;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T16MorningStandardResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标6计算结果：早高峰公交站间速度可靠性 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T16ResultSpeedMorningStarndardService extends IService<T16ResultSpeedMorningStarndard> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T16MorningStandardResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T16ResultSpeedMorningStarndard> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
