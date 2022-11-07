package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.bean.T4Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import beijing.transport.beijing_proj.entity.T4ResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标4计算结果：线路早晚高峰与全天运营速度系数比 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T4ResultService extends IService<T4Result> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T4ResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T4Result> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
