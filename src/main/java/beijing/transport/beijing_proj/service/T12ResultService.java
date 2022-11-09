package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T12Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.entity.T12ResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标12计算结果：公交-轨道共线重复距离（只计算共线线路）	计算方法：读取t_station_gis_nearby_gongxian，计算公交ab站点的距离 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
public interface T12ResultService extends IService<T12Result> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T12ResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T12Result> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
