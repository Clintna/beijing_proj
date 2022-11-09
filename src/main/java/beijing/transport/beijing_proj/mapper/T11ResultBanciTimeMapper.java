package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T11ResultBanciTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 接驳线路运营时间匹配指数：公交站点与轨道站点为接驳关系，记录公交站点和轨道站点最早班次时间、最晚班次时间 Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Mapper
public interface T11ResultBanciTimeMapper extends BaseMapper<T11ResultBanciTime> {

}
