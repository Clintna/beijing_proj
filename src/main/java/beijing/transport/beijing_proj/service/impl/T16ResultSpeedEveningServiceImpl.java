package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T11ResultBanciTime;
import beijing.transport.beijing_proj.bean.T15Result;
import beijing.transport.beijing_proj.bean.T16ResultSpeedEvening;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T16EveningResultReturn;
import beijing.transport.beijing_proj.mapper.T16ResultSpeedEveningMapper;
import beijing.transport.beijing_proj.service.T16ResultSpeedEveningService;
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
 * 指标16计算结果：计算每一条公交线路AB段在晚高峰的速度（晚高峰时间段为17:00~19:00） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T16ResultSpeedEveningServiceImpl extends ServiceImpl<T16ResultSpeedEveningMapper, T16ResultSpeedEvening> implements T16ResultSpeedEveningService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T16ResultSpeedEveningMapper mapper;
    @Override
    public T16EveningResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T16ResultSpeedEvening> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T16ResultSpeedEvening> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T16ResultSpeedEvening> list1 = mapper.selectList(wrapper);
            list.addAll(list1);
        });
        T16EveningResultReturn t16EveningResultReturn = new T16EveningResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T16ResultSpeedEvening> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16EveningResultReturn.setRedisKey(s);
                t16EveningResultReturn.setT16ResultSpeedEvenings(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16EveningResultReturn.setRedisKey(s);
                t16EveningResultReturn.setT16ResultSpeedEvenings(list);
            }
        }
        return t16EveningResultReturn;
    }

    @Override
    public List<T16ResultSpeedEvening> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T16ResultSpeedEvening> t16ResultSpeedEvenings = jsonArray.toJavaList(T16ResultSpeedEvening.class);
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
        for (int i = 0; i < t16ResultSpeedEvenings.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T16ResultSpeedEvening t16ResultSpeedEvening = t16ResultSpeedEvenings.get(i);
            dataMap.put("line_name", t16ResultSpeedEvening.getLineName());
            dataMap.put("line_begin", t16ResultSpeedEvening.getLineBegin());
            dataMap.put("line_end", t16ResultSpeedEvening.getLineEnd());
            dataMap.put("station_orderid_a", t16ResultSpeedEvening.getStationOrderidA());
            dataMap.put("station_orderid_b", t16ResultSpeedEvening.getStationOrderidB());
            dataMap.put("station_name_a", t16ResultSpeedEvening.getStationNameA());
            dataMap.put("station_name_b", t16ResultSpeedEvening.getStationNameB());
            dataMap.put("speed", t16ResultSpeedEvening.getSpeed());
            dataMap.put("run_date", t16ResultSpeedEvening.getRunDate());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("t16ResultSpeedEvening", column, data, request, response);
    }
    private List<T16ResultSpeedEvening> ListSplit(List<T16ResultSpeedEvening> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T16ResultSpeedEvening> getT2ResultListSplit(List<T16ResultSpeedEvening> list, QueryDTO queryDTO) {
        List<T16ResultSpeedEvening> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
