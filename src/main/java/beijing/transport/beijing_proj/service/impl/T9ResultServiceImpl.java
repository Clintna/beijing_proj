package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T9Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T9ResultReturn;
import beijing.transport.beijing_proj.mapper.T9ResultMapper;
import beijing.transport.beijing_proj.service.T9ResultService;
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
 * 指标9计算结果：公交和轨道共线区域满载率的比值 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T9ResultServiceImpl extends ServiceImpl<T9ResultMapper, T9Result> implements T9ResultService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T9ResultMapper t9ResultMapper;

    @Override
    public T9ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T9Result> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("gongjiao_line_begin", queryDTO.getLineBegin());
            wrapper.eq("gongjiao_line_end", queryDTO.getLineEnd());
        }
        List<T9Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T9Result> list1 = t9ResultMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T9ResultReturn t9ResultReturn = new T9ResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T9Result> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t9ResultReturn.setRedisKey(s);
                t9ResultReturn.setT9Results(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t9ResultReturn.setRedisKey(s);
                t9ResultReturn.setT9Results(list);
            }
        }
        return t9ResultReturn;
    }

    @Override
    public List<T9Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T9Result> t9Results = jsonArray.toJavaList(T9Result.class);
        List<String> column = new ArrayList<>();
        column.add("gongjiao_line_name");
        column.add("guidao_line_name");
        column.add("gongjiao_line_begin");
        column.add("guidao_line_begin");
        column.add("gongjiao_line_end");
        column.add("guidao_line_end");

        column.add("gongxian_gongjiao_station_orderid_a");
        column.add("gongxian_gongjiao_station_name_a");
        column.add("gongxian_guidao_station_orderid_a");
        column.add("gongxian_guidao_station_name_a");
        column.add("gongxian_gongjiao_station_load_rate_a");
        column.add("gongxian_guidao_station_load_rate_a");

        column.add("gongxian_gongjiao_station_orderid_b");
        column.add("gongxian_gongjiao_station_name_b");
        column.add("gongxian_guidao_station_orderid_b");
        column.add("gongxian_guidao_station_name_b");
        column.add("gongxian_gongjiao_station_load_rate_b");
        column.add("gongxian_guidao_station_load_rate_b");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t9Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T9Result t9Result = t9Results.get(i);
            dataMap.put("gongjiao_line_name", t9Result.getGongjiaoLineName());
            dataMap.put("guidao_line_name", t9Result.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", t9Result.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", t9Result.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", t9Result.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", t9Result.getGuidaoLineEnd());
            dataMap.put("gongxian_gongjiao_station_orderid_a", t9Result.getGongxianGongjiaoStationOrderidA());
            dataMap.put("gongxian_gongjiao_station_name_a", t9Result.getGongxianGongjiaoStationNameA());
            dataMap.put("gongxian_guidao_station_orderid_a", t9Result.getGongxianGuidaoStationOrderidA());
            dataMap.put("gongxian_guidao_station_name_a", t9Result.getGongxianGuidaoStationNameA());
            dataMap.put("gongxian_gongjiao_station_load_rate_a", t9Result.getGongxianGongjiaoStationLoadRateA());
            dataMap.put("gongxian_guidao_station_load_rate_a", t9Result.getGongxianGuidaoStationLoadRateA());
            dataMap.put("gongxian_gongjiao_station_orderid_b", t9Result.getGongxianGongjiaoStationOrderidB());
            dataMap.put("gongxian_gongjiao_station_name_b", t9Result.getGongxianGongjiaoStationNameB());
            dataMap.put("gongxian_guidao_station_orderid_b", t9Result.getGongxianGuidaoStationOrderidB());
            dataMap.put("gongxian_guidao_station_name_b", t9Result.getGongxianGuidaoStationNameB());
            dataMap.put("gongxian_gongjiao_station_load_rate_b", t9Result.getGongxianGongjiaoStationLoadRateB());
            dataMap.put("gongxian_guidao_station_load_rate_b", t9Result.getGongxianGuidaoStationLoadRateB());
            dataMap.put("run_date", t9Result.getRunDate());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("T9Result", column, data, request, response);
    }

    private List<T9Result> ListSplit(List<T9Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T9Result> getT2ResultListSplit(List<T9Result> list, QueryDTO queryDTO) {
        List<T9Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
