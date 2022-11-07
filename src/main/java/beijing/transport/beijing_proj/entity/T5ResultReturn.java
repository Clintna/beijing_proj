package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T5Result;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/10/14
 * @Description:
 */
@Data
public class T5ResultReturn {
    private List<T5Result> t5Results;
    private String redisKey;
}
