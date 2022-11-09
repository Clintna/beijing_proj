package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T14ResultFlowEveningAverage;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T14EveningAverageResultReturn;
import beijing.transport.beijing_proj.mapper.T14ResultFlowEveningAverageMapper;
import beijing.transport.beijing_proj.service.T14ResultFlowEveningAverageService;
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
 * 指标14计算结果：计算每一条公交线路晚高峰的平均满载率（晚高峰时间段为17:00~19:00），平均满载率=各AB路段满载率的平均值。 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T14ResultFlowEveningAverageServiceImpl extends ServiceImpl<T14ResultFlowEveningAverageMapper, T14ResultFlowEveningAverage> implements T14ResultFlowEveningAverageService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T14ResultFlowEveningAverageMapper mapper;
    @Override
    public T14EveningAverageResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T14ResultFlowEveningAverage> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());

        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T14ResultFlowEveningAverage> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T14ResultFlowEveningAverage> list1 = mapper.selectList(wrapper);
            list.addAll(list1);
        });
        T14EveningAverageResultReturn t14EveningAverageResultReturn = new T14EveningAverageResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T14ResultFlowEveningAverage> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14EveningAverageResultReturn.setRedisKey(s);
                t14EveningAverageResultReturn.setT14ResultFlowEveningAverages(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14EveningAverageResultReturn.setRedisKey(s);
                t14EveningAverageResultReturn.setT14ResultFlowEveningAverages(list);
            }
        }
        return t14EveningAverageResultReturn;
    }

    @Override
    public List<T14ResultFlowEveningAverage> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T14ResultFlowEveningAverage> t14ResultFlowEveningAverages = jsonArray.toJavaList(T14ResultFlowEveningAverage.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("line_begin");
        column.add("line_end");
        column.add("full_rate_average");
        column.add("direction");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t14ResultFlowEveningAverages.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T14ResultFlowEveningAverage t14ResultFlowEveningAverage = t14ResultFlowEveningAverages.get(i);
            dataMap.put("line_name", t14ResultFlowEveningAverage.getLineName());
            dataMap.put("line_begin", t14ResultFlowEveningAverage.getLineBegin());
            dataMap.put("line_end", t14ResultFlowEveningAverage.getLineEnd());
            dataMap.put("full_rate_average", t14ResultFlowEveningAverage.getFullRateAverage());
            dataMap.put("direction", t14ResultFlowEveningAverage.getDirection());
            dataMap.put("run_date", t14ResultFlowEveningAverage.getRunDate());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("t14ResultFlowEveningAverage", column, data, request, response);
    }
    private List<T14ResultFlowEveningAverage> ListSplit(List<T14ResultFlowEveningAverage> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T14ResultFlowEveningAverage> getT2ResultListSplit(List<T14ResultFlowEveningAverage> list, QueryDTO queryDTO) {
        List<T14ResultFlowEveningAverage> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
