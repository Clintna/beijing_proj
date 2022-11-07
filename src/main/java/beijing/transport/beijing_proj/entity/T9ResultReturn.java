package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T9Result;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/10/14
 * @Description:
 */
@Data
public class T9ResultReturn {
    private List<T9Result> t9Results;
    private String redisKey;
}
