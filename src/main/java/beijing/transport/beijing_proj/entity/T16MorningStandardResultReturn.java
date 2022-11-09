package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T16ResultSpeedMorningStarndard;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/07
 * @Description:
 */
@Data
public class T16MorningStandardResultReturn {
    private List<T16ResultSpeedMorningStarndard> t16MorningStandardResultReturns;
    private String redisKey;
}
