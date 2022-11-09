package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.bean.T11ResultBanciTime;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/11/07
 * @Description:
 */
@Data
public class T11BanciResultReturn {
    private List<T11ResultBanciTime> t11ResultBanciTimes;
    private String redisKey;
}
