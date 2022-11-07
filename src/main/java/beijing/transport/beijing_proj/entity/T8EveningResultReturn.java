package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T8ResultEvening;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/10/14
 * @Description:
 */
@Data
public class T8EveningResultReturn {
    private List<T8ResultEvening> t8ResultEvenings;
    private String redisKey;
}
