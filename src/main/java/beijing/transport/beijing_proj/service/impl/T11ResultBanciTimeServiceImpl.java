package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T11ResultBanciTime;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T11BanciResultReturn;
import beijing.transport.beijing_proj.mapper.T11ResultBanciTimeMapper;
import beijing.transport.beijing_proj.service.T11ResultBanciTimeService;
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
 * 接驳线路运营时间匹配指数：公交站点与轨道站点为接驳关系，记录公交站点和轨道站点最早班次时间、最晚班次时间 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T11ResultBanciTimeServiceImpl extends ServiceImpl<T11ResultBanciTimeMapper, T11ResultBanciTime> implements T11ResultBanciTimeService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T11ResultBanciTimeMapper t11ResultBanciTimeMapper;
    @Override
    public T11BanciResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T11ResultBanciTime> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("gongjiao_line_begin", queryDTO.getLineBegin());
            wrapper.eq("gongjiao_line_end", queryDTO.getLineEnd());
        }
        List<T11ResultBanciTime> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T11ResultBanciTime> list1 = t11ResultBanciTimeMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T11BanciResultReturn t11BanciResultReturn = new T11BanciResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T11ResultBanciTime> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t11BanciResultReturn.setRedisKey(s);
                t11BanciResultReturn.setT11ResultBanciTimes(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t11BanciResultReturn.setRedisKey(s);
                t11BanciResultReturn.setT11ResultBanciTimes(list);
            }
        }
        return t11BanciResultReturn;
    }

    @Override
    public List<T11ResultBanciTime> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T11ResultBanciTime> t11ResultBanciTimes = jsonArray.toJavaList(T11ResultBanciTime.class);
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
        column.add("gongjiao_station_first_time");
        column.add("guidao_station_first_time");
        column.add("gongjiao_station_last_time");
        column.add("guidao_station_last_time");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t11ResultBanciTimes.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T11ResultBanciTime t11ResultBanciTime = t11ResultBanciTimes.get(i);
            dataMap.put("gongjiao_line_name", t11ResultBanciTime.getGongjiaoLineName());
            dataMap.put("guidao_line_name", t11ResultBanciTime.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", t11ResultBanciTime.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", t11ResultBanciTime.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", t11ResultBanciTime.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", t11ResultBanciTime.getGuidaoLineEnd());
            dataMap.put("gongjiao_station_orderid", t11ResultBanciTime.getGongjiaoStationOrderid());
            dataMap.put("gongjiao_station_name", t11ResultBanciTime.getGongjiaoStationName());
            dataMap.put("guidao_station_orderid", t11ResultBanciTime.getGuidaoStationOrderid());
            dataMap.put("guidao_station_name", t11ResultBanciTime.getGuidaoStationName());
            dataMap.put("gongjiao_station_first_time", t11ResultBanciTime.getGongjiaoStationFirstTime());
            dataMap.put("guidao_station_first_time", t11ResultBanciTime.getGuidaoStationFirstTime());
            dataMap.put("gongjiao_station_last_time", t11ResultBanciTime.getGongjiaoStationLastTime());
            dataMap.put("guidao_station_last_time", t11ResultBanciTime.getGuidaoStationLastTime());
            dataMap.put("run_date", t11ResultBanciTime.getRunDate());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("t11ResultBanciTime", column, data, request, response);

    }

    private List<T11ResultBanciTime> ListSplit(List<T11ResultBanciTime> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T11ResultBanciTime> getT2ResultListSplit(List<T11ResultBanciTime> list, QueryDTO queryDTO) {
        List<T11ResultBanciTime> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
