package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T9Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标9计算结果：公交和轨道共线区域满载率的比值 Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Mapper
public interface T9ResultMapper extends BaseMapper<T9Result> {

}
