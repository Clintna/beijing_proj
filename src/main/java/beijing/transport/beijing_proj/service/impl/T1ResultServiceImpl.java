package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T1Result;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T1ResultReturn;
import beijing.transport.beijing_proj.mapper.T1ResultMapper;
import beijing.transport.beijing_proj.service.T1ResultService;
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
 * 指标1：线路中非重复的OD客运量占全部客运量的比 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-08
 */
@Service
public class T1ResultServiceImpl extends ServiceImpl<T1ResultMapper, T1Result> implements T1ResultService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T1ResultMapper t1ResultMapper;

    @Override
    public T1ResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T1Result> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T1Result> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T1Result> list1 = t1ResultMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T1ResultReturn t1ResultReturn = new T1ResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T1Result> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t1ResultReturn.setRedisKey(s);
                t1ResultReturn.setT1Results(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t1ResultReturn.setRedisKey(s);
                t1ResultReturn.setT1Results(list);
            }
        }
        return t1ResultReturn;
    }

    @Override
    public List<T1Result> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T1Result> t1Results = jsonArray.toJavaList(T1Result.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("run_date");
        column.add("line_id");
        column.add("line_begin");
        column.add("line_end");
        column.add("direction");
        column.add("flow_od_duplicate");
        column.add("flow_od_no_duplicate");
        column.add("flow_all");
        column.add("rate_od_no_duplicate");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t1Results.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T1Result t1Result = t1Results.get(i);
            dataMap.put("line_name", t1Result.getLineName());
            dataMap.put("run_date", t1Result.getRunDate());
            dataMap.put("line_id", t1Result.getLineId());
            dataMap.put("line_begin", t1Result.getLineBegin());
            dataMap.put("line_end", t1Result.getLineEnd());
            dataMap.put("direction", t1Result.getDirection());
            dataMap.put("flow_od_duplicate", t1Result.getFlowOdDuplicate());
            dataMap.put("flow_od_no_duplicate", t1Result.getFlowOdNoDuplicate());
            dataMap.put("flow_all", t1Result.getFlowAll());
            dataMap.put("rate_od_no_duplicate", t1Result.getRateOdNoDuplicate());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("t1Result", column, data, request, response);

    }


    private List<T1Result> ListSplit(List<T1Result> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T1Result> getT2ResultListSplit(List<T1Result> list, QueryDTO queryDTO) {
        List<T1Result> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
