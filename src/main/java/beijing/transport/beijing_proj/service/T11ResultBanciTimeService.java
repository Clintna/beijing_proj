package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T11ResultBanciTime;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T11BanciResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 接驳线路运营时间匹配指数：公交站点与轨道站点为接驳关系，记录公交站点和轨道站点最早班次时间、最晚班次时间 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T11ResultBanciTimeService extends IService<T11ResultBanciTime> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T11BanciResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T11ResultBanciTime> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
