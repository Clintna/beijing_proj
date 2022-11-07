package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/10/14
 * @Description:
 */
@Data
public class T10ResultReturn {
    private List<T10Result> t10Results;
    private String redisKey;
}
