package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T11ResultBanciTimeFirstMatch;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T11BanciFirstResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 接驳线路运营时间匹配指数：最早班车的匹配度 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T11ResultBanciTimeFirstMatchService extends IService<T11ResultBanciTimeFirstMatch> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T11BanciFirstResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T11ResultBanciTimeFirstMatch> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
