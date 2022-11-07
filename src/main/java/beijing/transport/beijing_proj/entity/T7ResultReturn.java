package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T7Result;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/10/14
 * @Description:
 */
@Data
public class T7ResultReturn {
    private List<T7Result> t7Results;
    private String redisKey;
}
