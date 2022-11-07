package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T3ResultGuidao;
import beijing.transport.beijing_proj.bean.T4Result;
import beijing.transport.beijing_proj.entity.*;
import beijing.transport.beijing_proj.mapper.T4ResultMapper;
import beijing.transport.beijing_proj.service.T4ResultService;
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
 * 指标4计算结果：线路早晚高峰与全天运营速度系数比 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T4ResultServiceImpl extends ServiceImpl<T4ResultMapper, T4Result> implements T4ResultService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T4ResultMapper t4ResultMapper;
    @Override
    public T4ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T4Result> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        List<T4Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T4Result> list1 = t4ResultMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T4ResultReturn t4ResultReturn = new T4ResultReturn();
        if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

            List<T4Result> newList = ListSplit(list, queryDTO);
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(newList));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t4ResultReturn.setRedisKey(s);
            t4ResultReturn.setT4Results(newList);
        } else {
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(list));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t4ResultReturn.setRedisKey(s);
            t4ResultReturn.setT4Results(list);
        }
        return t4ResultReturn;
    }

    @Override
    public List<T4Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T4Result> t4Results = jsonArray.toJavaList(T4Result.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("run_date");
        column.add("line_begin");
        column.add("line_end");
        column.add("speed_normal");
        column.add("speed_morning");
        column.add("speed_evening");
        column.add("speed_idle");


        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t4Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T4Result guidao = t4Results.get(i);
            dataMap.put("line_name", guidao.getLineName());
            dataMap.put("run_date", guidao.getRunDate());
            dataMap.put("line_begin", guidao.getLineBegin());
            dataMap.put("line_end", guidao.getLineEnd());
            dataMap.put("speed_normal", guidao.getSpeedNormal());
            dataMap.put("speed_morning", guidao.getSpeedMorning());
            dataMap.put("speed_evening", guidao.getSpeedEvening());
            dataMap.put("speed_idle", guidao.getSpeedIdle());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("T4Result", column, data, request, response);
    }


    private List<T4Result> ListSplit(List<T4Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T4Result> getT2ResultListSplit(List<T4Result> list, QueryDTO queryDTO) {
        List<T4Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
