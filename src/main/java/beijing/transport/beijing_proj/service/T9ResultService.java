package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.bean.T9Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T9ResultReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标9计算结果：公交和轨道共线区域满载率的比值 服务类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
public interface T9ResultService extends IService<T9Result> {
    /**
     * 筛选查询
     *
     * @param queryDTO
     * @return
     */
    T9ResultReturn query(QueryDTO queryDTO);

    /**
     * 查询所有
     *
     * @return
     */
    List<T9Result> queryAll();

    /**
     * 导出Excel
     *
     * @param redisKey
     * @param response
     */
    void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response);
}
