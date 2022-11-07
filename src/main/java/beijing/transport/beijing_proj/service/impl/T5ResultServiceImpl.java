package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T3ResultGuidao;
import beijing.transport.beijing_proj.bean.T5Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T5ResultReturn;
import beijing.transport.beijing_proj.mapper.T5ResultMapper;
import beijing.transport.beijing_proj.service.T5ResultService;
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
 * 指标5计算结果：线路运行时间不均衡系数 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T5ResultServiceImpl extends ServiceImpl<T5ResultMapper, T5Result> implements T5ResultService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T5ResultMapper t5ResultMapper;
    @Override
    public T5ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T5Result> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        List<T5Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T5Result> list1 = t5ResultMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T5ResultReturn t5ResultReturn = new T5ResultReturn();
        if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

            List<T5Result> newList = ListSplit(list, queryDTO);
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(newList));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t5ResultReturn.setRedisKey(s);
            t5ResultReturn.setT5Results(newList);
        } else {
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(list));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t5ResultReturn.setRedisKey(s);
            t5ResultReturn.setT5Results(list);
        }
        return t5ResultReturn;
    }

    @Override
    public List<T5Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T5Result> t5Results = jsonArray.toJavaList(T5Result.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("run_date");
        column.add("standard");
        column.add("line_begin");
        column.add("line_end");


        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t5Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T5Result guidao = t5Results.get(i);
            dataMap.put("line_name", guidao.getLineName());
            dataMap.put("run_date", guidao.getRunDate());
            dataMap.put("line_begin", guidao.getLineBegin());
            dataMap.put("line_end", guidao.getLineEnd());
            dataMap.put("standard", guidao.getStandard());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("T5Result", column, data, request, response);
    }

    private List<T5Result> ListSplit(List<T5Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T5Result> getT2ResultListSplit(List<T5Result> list, QueryDTO queryDTO) {
        List<T5Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
