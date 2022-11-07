package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T3ResultGuidao;
import beijing.transport.beijing_proj.bean.T6Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T6ResultReturn;
import beijing.transport.beijing_proj.mapper.T6ResultMapper;
import beijing.transport.beijing_proj.service.T6ResultService;
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
 * 指标6计算结果：线路发车间隔不均衡系数 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T6ResultServiceImpl extends ServiceImpl<T6ResultMapper, T6Result> implements T6ResultService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T6ResultMapper t6ResultMapper;
    @Override
    public T6ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T6Result> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        List<T6Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T6Result> list1 = t6ResultMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T6ResultReturn t6ResultReturn = new T6ResultReturn();
        if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

            List<T6Result> newList = ListSplit(list, queryDTO);
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(newList));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t6ResultReturn.setRedisKey(s);
            t6ResultReturn.setT6Results(newList);
        } else {
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(list));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t6ResultReturn.setRedisKey(s);
            t6ResultReturn.setT6Results(list);
        }
        return t6ResultReturn;
    }

    @Override
    public List<T6Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T6Result> t6Results = jsonArray.toJavaList(T6Result.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("run_date");
        column.add("standard");
        column.add("line_begin");
        column.add("line_end");


        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t6Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T6Result guidao = t6Results.get(i);
            dataMap.put("line_name", guidao.getLineName());
            dataMap.put("run_date", guidao.getRunDate());
            dataMap.put("line_begin", guidao.getLineBegin());
            dataMap.put("line_end", guidao.getLineEnd());
            dataMap.put("standard", guidao.getStandard());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("T6Result", column, data, request, response);
    }
    private List<T6Result> ListSplit(List<T6Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T6Result> getT2ResultListSplit(List<T6Result> list, QueryDTO queryDTO) {
        List<T6Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }

}
