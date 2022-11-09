package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T16ResultSpeedMorning;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标16计算结果：计算每一条公交线路AB段在早高峰的速度（晚高峰时间段为7:00~9:00） Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Mapper
public interface T16ResultSpeedMorningMapper extends BaseMapper<T16ResultSpeedMorning> {

}
