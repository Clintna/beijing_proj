package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标10计算结果：公交下轨道上接驳统计数据 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T10ResultService extends IService<T10Result> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T10ResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T10Result> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
