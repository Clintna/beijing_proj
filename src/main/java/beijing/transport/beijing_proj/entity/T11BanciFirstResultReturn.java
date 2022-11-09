package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T11ResultBanciTimeFirstMatch;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/07
 * @Description:
 */
@Data
public class T11BanciFirstResultReturn {
    private List<T11ResultBanciTimeFirstMatch> t11ResultBanciTimeFirstMatches;
    private String redisKey;
}
