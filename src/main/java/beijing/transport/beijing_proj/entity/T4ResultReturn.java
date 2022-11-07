package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T4Result;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/10/14
 * @Description:
 */
@Data
public class T4ResultReturn {
    private List<T4Result> t4Results;
    private String redisKey;
}
