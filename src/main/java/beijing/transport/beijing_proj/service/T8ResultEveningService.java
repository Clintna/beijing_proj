package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.bean.T8ResultEvening;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import beijing.transport.beijing_proj.entity.T8EveningResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标8计算结果：晚高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为17:00~19:00） 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T8ResultEveningService extends IService<T8ResultEvening> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T8EveningResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T8ResultEvening> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
