package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T4Result;
import beijing.transport.beijing_proj.bean.T7Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T7ResultReturn;
import beijing.transport.beijing_proj.mapper.T7ResultMapper;
import beijing.transport.beijing_proj.service.T7ResultService;
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
 * 指标7计算结果：公交-轨道共线重复距离比 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T7ResultServiceImpl extends ServiceImpl<T7ResultMapper, T7Result> implements T7ResultService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T7ResultMapper t7ResultMapper;
    @Override
    public T7ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T7Result> wrapper = new QueryWrapper<>();
        wrapper.eq("gongjiao_line_name", queryDTO.getLineName());
        List<T7Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T7Result> list1 = t7ResultMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T7ResultReturn t7ResultReturn = new T7ResultReturn();
        if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

            List<T7Result> newList = ListSplit(list, queryDTO);
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(newList));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t7ResultReturn.setRedisKey(s);
            t7ResultReturn.setT7Results(newList);
        } else {
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(list));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t7ResultReturn.setRedisKey(s);
            t7ResultReturn.setT7Results(list);
        }
        return t7ResultReturn;
    }

    @Override
    public List<T7Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T7Result> t7Results = jsonArray.toJavaList(T7Result.class);
        List<String> column = new ArrayList<>();
        column.add("gongjiao_line_name");
        column.add("guidao_line_name");
        column.add("gongjiao_line_begin");
        column.add("guidao_line_begin");
        column.add("gongjiao_line_end");
        column.add("guidao_line_end");
        column.add("gongxian_distance");
        column.add("gongjiao_distance");
        column.add("distance_rate");



        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t7Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T7Result guidao = t7Results.get(i);
            dataMap.put("gongjiao_line_name", guidao.getGongjiaoLineName());
            dataMap.put("guidao_line_name", guidao.getGuidaoLineName());
            dataMap.put("gongjiao_line_begin", guidao.getGongjiaoLineBegin());
            dataMap.put("guidao_line_begin", guidao.getGuidaoLineBegin());
            dataMap.put("gongjiao_line_end", guidao.getGongjiaoLineEnd());
            dataMap.put("guidao_line_end", guidao.getGuidaoLineEnd());
            dataMap.put("gongxian_distance", guidao.getGongxianDistance());
            dataMap.put("gongjiao_distance", guidao.getGongjiaoDistance());
            dataMap.put("distance_rate", guidao.getDistanceRate());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("T7Result", column, data, request, response);
    }

    private List<T7Result> ListSplit(List<T7Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T7Result> getT2ResultListSplit(List<T7Result> list, QueryDTO queryDTO) {
        List<T7Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
