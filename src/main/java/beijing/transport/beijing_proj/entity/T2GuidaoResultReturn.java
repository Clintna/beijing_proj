package beijing.transport.beijing_proj.entity;

import beijing.transport.beijing_proj.bean.T2ResultGuidao;
import lombok.Data;

import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/09/28
 * @Description:
 */
@Data
public class T2GuidaoResultReturn {
    private List<T2ResultGuidao> t2ResultGongjiaos;
    private String redisKey;
}
