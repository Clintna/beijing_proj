package beijing.transport.beijing_proj.mapper;

import beijing.transport.beijing_proj.bean.T12Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 指标12计算结果：公交-轨道共线重复距离（只计算共线线路）	计算方法：读取t_station_gis_nearby_gongxian，计算公交ab站点的距离 Mapper 接口
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Mapper
public interface T12ResultMapper extends BaseMapper<T12Result> {

}
