package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/09/28
 * @Description:
 */
@Data
public class T3GongjiaoResultReturn {
    private List<T3ResultGongjiao> t3ResultGongjiaos;
    private String redisKey;
}
