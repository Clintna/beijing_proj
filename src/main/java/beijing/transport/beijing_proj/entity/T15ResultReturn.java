package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T15Result;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/07
 * @Description:
 */
@Data
public class T15ResultReturn {
    private List<T15Result> t15Results;
    private String redisKey;
}
