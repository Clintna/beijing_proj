package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T15Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T15ResultReturn;
import beijing.transport.beijing_proj.mapper.T15ResultMapper;
import beijing.transport.beijing_proj.service.T15ResultService;
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
 * 公交线路的平均运行速度 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T15ResultServiceImpl extends ServiceImpl<T15ResultMapper, T15Result> implements T15ResultService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T15ResultMapper mapper;
    @Override
    public T15ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T15Result> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T15Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T15Result> list1 = mapper.selectList(wrapper);
            list.addAll(list1);
        });
        T15ResultReturn t15ResultReturn = new T15ResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T15Result> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t15ResultReturn.setRedisKey(s);
                t15ResultReturn.setT15Results(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t15ResultReturn.setRedisKey(s);
                t15ResultReturn.setT15Results(list);
            }
        }
        return t15ResultReturn;
    }

    @Override
    public List<T15Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T15Result> t15Results = jsonArray.toJavaList(T15Result.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("line_begin");
        column.add("line_end");
        column.add("speed");
        column.add("run_date");
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t15Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T15Result t15Result = t15Results.get(i);
            dataMap.put("line_name", t15Result.getLineName());
            dataMap.put("line_begin", t15Result.getLineBegin());
            dataMap.put("line_end", t15Result.getLineEnd());
            dataMap.put("speed", t15Result.getSpeed());
            dataMap.put("run_date", t15Result.getRunDate());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("t15Result", column, data, request, response);
    }
    private List<T15Result> ListSplit(List<T15Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T15Result> getT2ResultListSplit(List<T15Result> list, QueryDTO queryDTO) {
        List<T15Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
