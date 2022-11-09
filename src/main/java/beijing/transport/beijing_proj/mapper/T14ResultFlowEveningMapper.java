package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T14ResultFlowEvening;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标14计算结果：计算每一条公交线路AB段在晚高峰的断面流量（晚高峰时间段为17:00~19:00） Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Mapper
public interface T14ResultFlowEveningMapper extends BaseMapper<T14ResultFlowEvening> {

}
