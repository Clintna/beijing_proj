package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T13ResultWalk;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T13WalkResultReturn;
import beijing.transport.beijing_proj.mapper.T13ResultWalkMapper;
import beijing.transport.beijing_proj.service.T13ResultWalkService;
import beijing.transport.beijing_proj.utils.ExportExcel;
import beijing.transport.beijing_proj.utils.RedisUtil;
import beijing.transport.beijing_proj.utils.TaskIdGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 指标13：接驳站点之间的步行距离 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T13ResultWalkServiceImpl extends ServiceImpl<T13ResultWalkMapper, T13ResultWalk> implements T13ResultWalkService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T13ResultWalkMapper mapper;
    @Override
    public T13WalkResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T13ResultWalk> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("gongjiao_line_begin", queryDTO.getLineBegin());
            wrapper.eq("gongjiao_line_end", queryDTO.getLineEnd());
        }
        List<T13ResultWalk> list = mapper.selectList(wrapper);
        T13WalkResultReturn t13WalkResultReturn = new T13WalkResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T13ResultWalk> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t13WalkResultReturn.setRedisKey(s);
                t13WalkResultReturn.setT13ResultWalks(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t13WalkResultReturn.setRedisKey(s);
                t13WalkResultReturn.setT13ResultWalks(list);
            }
        }
        return t13WalkResultReturn;
    }

    @Override
    public List<T13ResultWalk> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T13ResultWalk> t13ResultWalks = jsonArray.toJavaList(T13ResultWalk.class);
        List<String> column = new ArrayList<>();
        column.add("gongjiao_line_name");
        column.add("guidao_line_name");
        column.add("gongjiao_line_begin");
        column.add("guidao_line_begin");
        column.add("gongjiao_line_end");
        column.add("guidao_line_end");
        column.add("gongjiao_station_orderid");
        column.add("gongjiao_station_name");
        column.add("guidao_station_orderid");
        column.add("guidao_station_name");
        column.add("walk_distance");
        column.add("direct_distance");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t13ResultWalks.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T13ResultWalk t13ResultWalk = t13ResultWalks.get(i);
            dataMap.put("gongjiao_line_name", t13ResultWalk.getGongjiaoLineName());
            dataMap.put("guidao_line_name", t13ResultWalk.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", t13ResultWalk.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", t13ResultWalk.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", t13ResultWalk.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", t13ResultWalk.getGuidaoLineEnd());
            dataMap.put("gongjiao_station_orderid", t13ResultWalk.getGongjiaoStationOrderid());
            dataMap.put("gongjiao_station_name", t13ResultWalk.getGongjiaoStationName());
            dataMap.put("guidao_station_orderid", t13ResultWalk.getGuidaoStationOrderid());
            dataMap.put("guidao_station_name", t13ResultWalk.getGuidaoStationName());
            dataMap.put("walk_distance", t13ResultWalk.getWalkDistance());
            dataMap.put("direct_distance", t13ResultWalk.getDirectDistance());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("t13ResultWalk", column, data, request, response);
    }

    private List<T13ResultWalk> ListSplit(List<T13ResultWalk> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T13ResultWalk> getT2ResultListSplit(List<T13ResultWalk> list, QueryDTO queryDTO) {
        List<T13ResultWalk> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
