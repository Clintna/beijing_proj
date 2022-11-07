package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T4Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标4计算结果：线路早晚高峰与全天运营速度系数比 Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Mapper
public interface T4ResultMapper extends BaseMapper<T4Result> {

}
