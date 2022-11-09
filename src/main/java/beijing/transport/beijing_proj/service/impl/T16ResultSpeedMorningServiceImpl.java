package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T16ResultSpeedMorning;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T16MorningResultReturn;
import beijing.transport.beijing_proj.mapper.T16ResultSpeedMorningMapper;
import beijing.transport.beijing_proj.service.T16ResultSpeedMorningService;
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
 * 指标16计算结果：计算每一条公交线路AB段在早高峰的速度（晚高峰时间段为7:00~9:00） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T16ResultSpeedMorningServiceImpl extends ServiceImpl<T16ResultSpeedMorningMapper, T16ResultSpeedMorning> implements T16ResultSpeedMorningService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T16ResultSpeedMorningMapper t16ResultSpeedMorningMapper;
    @Override
    public T16MorningResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T16ResultSpeedMorning> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T16ResultSpeedMorning> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T16ResultSpeedMorning> list1 = t16ResultSpeedMorningMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T16MorningResultReturn t16MorningResultReturn = new T16MorningResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T16ResultSpeedMorning> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16MorningResultReturn.setRedisKey(s);
                t16MorningResultReturn.setT16ResultSpeedMornings(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16MorningResultReturn.setRedisKey(s);
                t16MorningResultReturn.setT16ResultSpeedMornings(list);
            }
        }
        return t16MorningResultReturn;
    }

    @Override
    public List<T16ResultSpeedMorning> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T16ResultSpeedMorning> t16ResultSpeedMornings = jsonArray.toJavaList(T16ResultSpeedMorning.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("line_begin");
        column.add("line_end");
        column.add("station_orderid_a");
        column.add("station_orderid_b");
        column.add("station_name_a");
        column.add("station_name_b");
        column.add("speed");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t16ResultSpeedMornings.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T16ResultSpeedMorning t16ResultSpeedMorning = t16ResultSpeedMornings.get(i);
            dataMap.put("line_name", t16ResultSpeedMorning.getLineName());
            dataMap.put("line_begin", t16ResultSpeedMorning.getLineBegin());
            dataMap.put("line_end", t16ResultSpeedMorning.getLineEnd());
            dataMap.put("station_orderid_a", t16ResultSpeedMorning.getStationOrderidA());
            dataMap.put("station_orderid_b", t16ResultSpeedMorning.getStationOrderidB());
            dataMap.put("station_name_a", t16ResultSpeedMorning.getStationNameA());
            dataMap.put("station_name_b", t16ResultSpeedMorning.getStationNameB());
            dataMap.put("speed", t16ResultSpeedMorning.getSpeed());
            dataMap.put("run_date", t16ResultSpeedMorning.getRunDate());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("t16ResultSpeedMorning", column, data, request, response);
    }
    private List<T16ResultSpeedMorning> ListSplit(List<T16ResultSpeedMorning> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T16ResultSpeedMorning> getT2ResultListSplit(List<T16ResultSpeedMorning> list, QueryDTO queryDTO) {
        List<T16ResultSpeedMorning> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
