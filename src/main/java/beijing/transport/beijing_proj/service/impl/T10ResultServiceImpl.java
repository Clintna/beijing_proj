package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T10Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T10ResultReturn;
import beijing.transport.beijing_proj.mapper.T10ResultMapper;
import beijing.transport.beijing_proj.service.T10ResultService;
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
 * 指标10计算结果：公交下轨道上接驳统计数据 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T10ResultServiceImpl extends ServiceImpl<T10ResultMapper, T10Result> implements T10ResultService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T10ResultMapper t10ResultMapper;

    @Override
    public T10ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T10Result> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("gongjiao_line_begin", queryDTO.getLineBegin());
            wrapper.eq("gongjiao_line_end", queryDTO.getLineEnd());
        }
        List<T10Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T10Result> list1 = t10ResultMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T10ResultReturn t10ResultReturn = new T10ResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T10Result> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t10ResultReturn.setRedisKey(s);
                t10ResultReturn.setT10Results(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t10ResultReturn.setRedisKey(s);
                t10ResultReturn.setT10Results(list);
            }
        }
        return t10ResultReturn;
    }

    @Override
    public List<T10Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T10Result> t10Results = jsonArray.toJavaList(T10Result.class);
        List<String> column = new ArrayList<>();
        column.add("gongjiao_off_line_id");
        column.add("gongjiao_stop_off");
        column.add("guidao_on_line_id");
        column.add("guidao_stop_on");
        column.add("gongjiao_off_sum");
        column.add("guidao_on_exchange");
        column.add("rate");
        column.add("run_date");
        column.add("gongjiao_line_name");
        column.add("gongjiao_line_begin");
        column.add("gongjiao_line_end");
        column.add("guidao_line_name");
        column.add("guidao_line_begin");
        column.add("guidao_line_end");
        column.add("gongjiao_stop_off_station_name");
        column.add("guidao_stop_on_station_name");



        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t10Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T10Result t9Result = t10Results.get(i);
            dataMap.put("gongjiao_off_line_id", t9Result.getGongjiaoOffLineId());
            dataMap.put("gongjiao_stop_off", t9Result.getGongjiaoStopOff());
            dataMap.put("guidao_on_line_id", t9Result.getGuidaoOnLineId());
            dataMap.put("guidao_stop_on", t9Result.getGuidaoStopOn());
            dataMap.put("gongjiao_off_sum", t9Result.getGongjiaoOffSum());
            dataMap.put("guidao_on_exchange", t9Result.getGuidaoOnExchange());
            dataMap.put("rate", t9Result.getRate());
            dataMap.put("gongjiao_line_name", t9Result.getGongjiaoLineName());
            dataMap.put("gongjiao_line_begin", t9Result.getGongjiaoLineBegin());
            dataMap.put("gongjiao_line_end", t9Result.getGongjiaoLineEnd());
            dataMap.put("guidao_line_name", t9Result.getGuidaoLineName());
            dataMap.put("guidao_line_begin", t9Result.getGuidaoLineBegin());
            dataMap.put("guidao_line_end", t9Result.getGuidaoLineEnd());
            dataMap.put("gongjiao_stop_off_station_name", t9Result.getGongjiaoStopOffStationName());
            dataMap.put("guidao_stop_on_station_name", t9Result.getGuidaoStopOnStationName());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("T10Result", column, data, request, response);
    }


    private List<T10Result> ListSplit(List<T10Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T10Result> getT2ResultListSplit(List<T10Result> list, QueryDTO queryDTO) {
        List<T10Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
