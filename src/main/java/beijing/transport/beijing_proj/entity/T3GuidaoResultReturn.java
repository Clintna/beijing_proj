package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T3ResultGuidao;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/10/14
 * @Description:
 */
@Data
public class T3GuidaoResultReturn {
    private List<T3ResultGuidao> t3ResultGuidaos;
    private String redisKey;
}
