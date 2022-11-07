package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T2ResultGuidao;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import beijing.transport.beijing_proj.entity.T2GuidaoResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标2计算结果：计算轨道线路公里客运周转量 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T2ResultGuidaoService extends IService<T2ResultGuidao> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T2GuidaoResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T2ResultGuidao> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
