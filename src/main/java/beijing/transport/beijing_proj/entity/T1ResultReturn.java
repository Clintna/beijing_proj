package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T1Result;
import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/08
 * @Description:
 */
@Data
public class T1ResultReturn {
    private List<T1Result> t1Results;
    private String redisKey;
}
