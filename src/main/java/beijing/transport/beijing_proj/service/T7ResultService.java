package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.bean.T7Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import beijing.transport.beijing_proj.entity.T6ResultReturn;
import beijing.transport.beijing_proj.entity.T7ResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标7计算结果：公交-轨道共线重复距离比 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T7ResultService extends IService<T7Result> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T7ResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T7Result> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
