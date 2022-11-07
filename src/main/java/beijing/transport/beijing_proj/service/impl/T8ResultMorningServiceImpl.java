package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T8ResultMorning;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T8MorningResultReturn;
import beijing.transport.beijing_proj.mapper.T8ResultMorningMapper;
import beijing.transport.beijing_proj.service.T8ResultMorningService;
import beijing.transport.beijing_proj.utils.ExportExcel;
import beijing.transport.beijing_proj.utils.RedisUtil;
import beijing.transport.beijing_proj.utils.TaskIdGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 指标8计算结果：早高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为7:00~9:00） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T8ResultMorningServiceImpl extends ServiceImpl<T8ResultMorningMapper, T8ResultMorning> implements T8ResultMorningService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T8ResultMorningMapper t8ResultMorningMapper;
    @Override
    public T8MorningResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T8ResultMorning> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        List<T8ResultMorning> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T8ResultMorning> list1 = t8ResultMorningMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T8MorningResultReturn t8MorningResultReturn = new T8MorningResultReturn();
        if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

            List<T8ResultMorning> newList = ListSplit(list, queryDTO);
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(newList));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t8MorningResultReturn.setRedisKey(s);
            t8MorningResultReturn.setT8ResultMornings(newList);
        } else {
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(list));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t8MorningResultReturn.setRedisKey(s);
            t8MorningResultReturn.setT8ResultMornings(list);
        }
        return t8MorningResultReturn;
    }

    @Override
    public List<T8ResultMorning> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T8ResultMorning> t8ResultMornings = jsonArray.toJavaList(T8ResultMorning.class);
        List<String> column = new ArrayList<>();
        column.add("gongjiao_line_name");
        column.add("guidao_line_name");
        column.add("gongjiao_line_begin");
        column.add("guidao_line_begin");
        column.add("gongjiao_line_end");
        column.add("guidao_line_end");
        column.add("gongjiao_station_orderid_a");
        column.add("gongjiao_station_orderid_b");
        column.add("gongjiao_station_name_a");
        column.add("gongjiao_station_name_b");
        column.add("gongjiao_speed");
        column.add("guidao_speed");
        column.add("speed_rate");
        column.add("run_date");




        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t8ResultMornings.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T8ResultMorning t8ResultMorning = t8ResultMornings.get(i);
            dataMap.put("gongjiao_line_name", t8ResultMorning.getGongjiaoLineName());
            dataMap.put("guidao_line_name", t8ResultMorning.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", t8ResultMorning.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", t8ResultMorning.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", t8ResultMorning.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", t8ResultMorning.getGuidaoLineEnd());
            dataMap.put("gongjiao_station_orderid_a", t8ResultMorning.getGongjiaoStationOrderidA());
            dataMap.put("gongjiao_station_orderid_b", t8ResultMorning.getGongjiaoStationOrderidB());
            dataMap.put("gongjiao_station_name_a", t8ResultMorning.getGongjiaoStationNameA());
            dataMap.put("gongjiao_station_name_b", t8ResultMorning.getGongjiaoStationNameB());
            dataMap.put("gongjiao_speed", t8ResultMorning.getGongjiaoSpeed());
            dataMap.put("guidao_speed", t8ResultMorning.getGuidaoSpeed());
            dataMap.put("speed_rate", t8ResultMorning.getSpeedRate());
            dataMap.put("run_date", t8ResultMorning.getRunDate());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("T8ResultEvening", column, data, request, response);
    }
    private List<T8ResultMorning> ListSplit(List<T8ResultMorning> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T8ResultMorning> getT2ResultListSplit(List<T8ResultMorning> list, QueryDTO queryDTO) {
        List<T8ResultMorning> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }

}
