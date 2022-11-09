package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T1Result;
import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T1ResultReturn;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标1：线路中非重复的OD客运量占全部客运量的比 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-08
 */
public interface T1ResultService extends IService<T1Result> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T1ResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T1Result> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
