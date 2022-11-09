package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T14ResultFlowEveningAverage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标14计算结果：计算每一条公交线路晚高峰的平均满载率（晚高峰时间段为17:00~19:00），平均满载率=各AB路段满载率的平均值。 Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Mapper
public interface T14ResultFlowEveningAverageMapper extends BaseMapper<T14ResultFlowEveningAverage> {

}
