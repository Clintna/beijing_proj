package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T12Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T12ResultReturn;
import beijing.transport.beijing_proj.mapper.T12ResultMapper;
import beijing.transport.beijing_proj.service.T12ResultService;
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
 * 指标12计算结果：公交-轨道共线重复距离（只计算共线线路）	计算方法：读取t_station_gis_nearby_gongxian，计算公交ab站点的距离 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T12ResultServiceImpl extends ServiceImpl<T12ResultMapper, T12Result> implements T12ResultService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T12ResultMapper t12ResultMapper;
    @Override
    public T12ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T12Result> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("gongjiao_line_begin", queryDTO.getLineBegin());
            wrapper.eq("gongjiao_line_end", queryDTO.getLineEnd());
        }
        List<T12Result> list = t12ResultMapper.selectList(wrapper);
        T12ResultReturn t12ResultReturn = new T12ResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T12Result> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t12ResultReturn.setRedisKey(s);
                t12ResultReturn.setT12Results(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t12ResultReturn.setRedisKey(s);
                t12ResultReturn.setT12Results(list);
            }
        }
        return t12ResultReturn;
    }

    @Override
    public List<T12Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T12Result> t12Results = jsonArray.toJavaList(T12Result.class);
        List<String> column = new ArrayList<>();
        column.add("gongjiao_line_name");
        column.add("guidao_line_name");
        column.add("gongjiao_line_begin");
        column.add("guidao_line_begin");
        column.add("gongjiao_line_end");
        column.add("guidao_line_end");
        column.add("gongjiao_station_orderid_a");
        column.add("gongjiao_station_orderid_b");
        column.add("gongjiao_station_name_b");
        column.add("gongjiao_station_name_a");
        column.add("guidao_station_orderid_a");
        column.add("guidao_station_orderid_b");
        column.add("guidao_station_name_a");
        column.add("guidao_station_name_b");
        column.add("gongjiao_station_ab_distance");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t12Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T12Result t12Result = t12Results.get(i);
            dataMap.put("gongjiao_line_name", t12Result.getGongjiaoLineName());
            dataMap.put("guidao_line_name", t12Result.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", t12Result.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", t12Result.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", t12Result.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", t12Result.getGuidaoLineEnd());
            dataMap.put("gongjiao_station_orderid_a", t12Result.getGongjiaoStationOrderidA());
            dataMap.put("gongjiao_station_orderid_b", t12Result.getGongjiaoStationOrderidB());
            dataMap.put("gongjiao_station_name_a", t12Result.getGongjiaoStationNameA());
            dataMap.put("gongjiao_station_name_b", t12Result.getGongjiaoStationNameB());
            dataMap.put("guidao_station_orderid_a", t12Result.getGuidaoStationOrderidA());
            dataMap.put("guidao_station_orderid_b", t12Result.getGuidaoStationOrderidB());
            dataMap.put("guidao_station_name_a", t12Result.getGuidaoStationNameA());
            dataMap.put("guidao_station_name_b", t12Result.getGuidaoStationNameB());
            dataMap.put("gongjiao_station_ab_distance", t12Result.getGongjiaoStationAbDistance());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("t12Result", column, data, request, response);
    }


    private List<T12Result> ListSplit(List<T12Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T12Result> getT2ResultListSplit(List<T12Result> list, QueryDTO queryDTO) {
        List<T12Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
