package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T12Result;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/07
 * @Description:
 */
@Data
public class T12ResultReturn {
    private List<T12Result> t12Results;
    private String redisKey;
}
