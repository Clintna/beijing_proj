package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T8ResultEvening;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标8计算结果：晚高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为17:00~19:00） Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Mapper
public interface T8ResultEveningMapper extends BaseMapper<T8ResultEvening> {

}
