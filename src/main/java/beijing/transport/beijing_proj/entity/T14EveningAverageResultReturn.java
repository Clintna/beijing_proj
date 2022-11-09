package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T14ResultFlowEveningAverage;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/07
 * @Description:
 */
@Data
public class T14EveningAverageResultReturn {
    private List<T14ResultFlowEveningAverage> t14ResultFlowEveningAverages;
    private String redisKey;
}
