package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.bean.T5Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import beijing.transport.beijing_proj.entity.T5ResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标5计算结果：线路运行时间不均衡系数 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T5ResultService extends IService<T5Result> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T5ResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T5Result> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
