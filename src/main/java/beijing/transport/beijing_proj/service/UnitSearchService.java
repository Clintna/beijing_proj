package beijing.transport.beijing_proj.service;

import beijing.transport.beijing_proj.entity.UnitSearchReturn;

/**
 * @Author: Jinglin
 * @Date: 2022/11/08
 * @Description:
 */
public interface UnitSearchService {
    /**
     * 链表查询结果
     * @param lineName
     * @return
     */
    UnitSearchReturn get(String lineName);
}
