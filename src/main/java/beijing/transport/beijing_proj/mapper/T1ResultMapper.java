package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T1Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标1：线路中非重复的OD客运量占全部客运量的比 Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-08
 */
@Mapper
public interface T1ResultMapper extends BaseMapper<T1Result> {

}
