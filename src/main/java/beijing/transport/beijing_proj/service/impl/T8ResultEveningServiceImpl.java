package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T8ResultEvening;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T8EveningResultReturn;
import beijing.transport.beijing_proj.mapper.T8ResultEveningMapper;
import beijing.transport.beijing_proj.service.T8ResultEveningService;
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
 * 指标8计算结果：晚高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为17:00~19:00） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T8ResultEveningServiceImpl extends ServiceImpl<T8ResultEveningMapper, T8ResultEvening> implements T8ResultEveningService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T8ResultEveningMapper t8ResultEveningMapper;

    @Override
    public T8EveningResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T8ResultEvening> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("gongjiao_line_begin", queryDTO.getLineBegin());
            wrapper.eq("gongjiao_line_end", queryDTO.getLineEnd());
        }
        List<T8ResultEvening> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T8ResultEvening> list1 = t8ResultEveningMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T8EveningResultReturn t8EveningResultReturn = new T8EveningResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T8ResultEvening> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t8EveningResultReturn.setRedisKey(s);
                t8EveningResultReturn.setT8ResultEvenings(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t8EveningResultReturn.setRedisKey(s);
                t8EveningResultReturn.setT8ResultEvenings(list);
            }
        }
        return t8EveningResultReturn;
    }

    @Override
    public List<T8ResultEvening> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T8ResultEvening> t8ResultEvenings = jsonArray.toJavaList(T8ResultEvening.class);
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
        for (int i = 0; i < t8ResultEvenings.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T8ResultEvening t8ResultEvening = t8ResultEvenings.get(i);
            dataMap.put("gongjiao_line_name", t8ResultEvening.getGongjiaoLineName());
            dataMap.put("guidao_line_name", t8ResultEvening.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", t8ResultEvening.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", t8ResultEvening.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", t8ResultEvening.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", t8ResultEvening.getGuidaoLineEnd());
            dataMap.put("gongjiao_station_orderid_a", t8ResultEvening.getGongjiaoStationOrderidA());
            dataMap.put("gongjiao_station_orderid_b", t8ResultEvening.getGongjiaoStationOrderidB());
            dataMap.put("gongjiao_station_name_a", t8ResultEvening.getGongjiaoStationNameA());
            dataMap.put("gongjiao_station_name_b", t8ResultEvening.getGongjiaoStationNameB());
            dataMap.put("gongjiao_speed", t8ResultEvening.getGongjiaoSpeed());
            dataMap.put("guidao_speed", t8ResultEvening.getGuidaoSpeed());
            dataMap.put("speed_rate", t8ResultEvening.getSpeedRate());
            dataMap.put("run_date", t8ResultEvening.getRunDate());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("T8ResultEvening", column, data, request, response);
    }

    private List<T8ResultEvening> ListSplit(List<T8ResultEvening> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T8ResultEvening> getT2ResultListSplit(List<T8ResultEvening> list, QueryDTO queryDTO) {
        List<T8ResultEvening> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }

}
