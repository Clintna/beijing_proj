package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T16ResultSpeedEvening;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标16计算结果：计算每一条公交线路AB段在晚高峰的速度（晚高峰时间段为17:00~19:00） Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Mapper
public interface T16ResultSpeedEveningMapper extends BaseMapper<T16ResultSpeedEvening> {

}
