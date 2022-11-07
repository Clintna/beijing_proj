package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/09/27
 * @Description:
 */
@Data
public class T2GongjiaoResultReturn {
    private List<T2ResultGongjiao> t2ResultGongjiaos;
    private String redisKey;
}
