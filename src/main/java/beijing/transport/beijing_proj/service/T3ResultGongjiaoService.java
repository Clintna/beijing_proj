package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T3ResultGuidao;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T3GongjiaoResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标3计算结果：线路客流不均衡系数（公交） 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T3ResultGongjiaoService extends IService<T3ResultGongjiao> {

    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T3GongjiaoResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T3ResultGongjiao> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
