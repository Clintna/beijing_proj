package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T16ResultSpeedEveningStarndard;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/07
 * @Description:
 */
@Data
public class T16EveningStandardResultReturn {
    private List<T16ResultSpeedEveningStarndard> t16ResultSpeedEveningStarndards;
    private String redisKey;
}
