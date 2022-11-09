package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T11ResultBanciTimeFirstMatch;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T11BanciFirstResultReturn;
import beijing.transport.beijing_proj.mapper.T11ResultBanciTimeFirstMatchMapper;
import beijing.transport.beijing_proj.service.T11ResultBanciTimeFirstMatchService;
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
 * 接驳线路运营时间匹配指数：最早班车的匹配度 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T11ResultBanciTimeFirstMatchServiceImpl extends ServiceImpl<T11ResultBanciTimeFirstMatchMapper, T11ResultBanciTimeFirstMatch> implements T11ResultBanciTimeFirstMatchService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T11ResultBanciTimeFirstMatchMapper t11ResultBanciTimeFirstMatchMapper;
    @Override
    public T11BanciFirstResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T11ResultBanciTimeFirstMatch> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("gongjiao_line_begin", queryDTO.getLineBegin());
            wrapper.eq("gongjiao_line_end", queryDTO.getLineEnd());
        }
        List<T11ResultBanciTimeFirstMatch> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T11ResultBanciTimeFirstMatch> list1 = t11ResultBanciTimeFirstMatchMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T11BanciFirstResultReturn t11BanciFirstResultReturn = new T11BanciFirstResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T11ResultBanciTimeFirstMatch> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t11BanciFirstResultReturn.setRedisKey(s);
                t11BanciFirstResultReturn.setT11ResultBanciTimeFirstMatches(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t11BanciFirstResultReturn.setRedisKey(s);
                t11BanciFirstResultReturn.setT11ResultBanciTimeFirstMatches(list);
            }
        }
        return t11BanciFirstResultReturn;
    }

    @Override
    public List<T11ResultBanciTimeFirstMatch> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {

        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T11ResultBanciTimeFirstMatch> t11ResultBanciTimeFirstMatches = jsonArray.toJavaList(T11ResultBanciTimeFirstMatch.class);
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
        column.add("match_rate_string");
        column.add("match_rate_value");
        column.add("first_sub_time");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t11ResultBanciTimeFirstMatches.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T11ResultBanciTimeFirstMatch t11ResultBanciTimeFirstMatch = t11ResultBanciTimeFirstMatches.get(i);
            dataMap.put("gongjiao_line_name", t11ResultBanciTimeFirstMatch.getGongjiaoLineName());
            dataMap.put("guidao_line_name", t11ResultBanciTimeFirstMatch.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", t11ResultBanciTimeFirstMatch.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", t11ResultBanciTimeFirstMatch.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", t11ResultBanciTimeFirstMatch.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", t11ResultBanciTimeFirstMatch.getGuidaoLineEnd());
            dataMap.put("gongjiao_station_orderid", t11ResultBanciTimeFirstMatch.getGongjiaoStationOrderid());
            dataMap.put("gongjiao_station_name", t11ResultBanciTimeFirstMatch.getGongjiaoStationName());
            dataMap.put("guidao_station_orderid", t11ResultBanciTimeFirstMatch.getGuidaoStationOrderid());
            dataMap.put("guidao_station_name", t11ResultBanciTimeFirstMatch.getGuidaoStationName());
            dataMap.put("gongjiao_station_first_time", t11ResultBanciTimeFirstMatch.getGongjiaoStationFirstTime());
            dataMap.put("guidao_station_first_time", t11ResultBanciTimeFirstMatch.getGuidaoStationFirstTime());
            dataMap.put("match_rate_string", t11ResultBanciTimeFirstMatch.getMatchRateString());
            dataMap.put("match_rate_value", t11ResultBanciTimeFirstMatch.getMatchRateValue());
            dataMap.put("first_sub_time", t11ResultBanciTimeFirstMatch.getFirstSubTime());
            dataMap.put("run_date", t11ResultBanciTimeFirstMatch.getRunDate());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("t11ResultBanciTimeFirstMatch", column, data, request, response);


    }

    private List<T11ResultBanciTimeFirstMatch> ListSplit(List<T11ResultBanciTimeFirstMatch> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T11ResultBanciTimeFirstMatch> getT2ResultListSplit(List<T11ResultBanciTimeFirstMatch> list, QueryDTO queryDTO) {
        List<T11ResultBanciTimeFirstMatch> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
