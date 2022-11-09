package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T14ResultFlowMorningAverage;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T14MorningAverageResultReturn;
import beijing.transport.beijing_proj.mapper.T14ResultFlowMorningAverageMapper;
import beijing.transport.beijing_proj.service.T14ResultFlowMorningAverageService;
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
 * 指标14计算结果：计算每一条公交线路早高峰的平均满载率（晚高峰时间段为7:00~9:00），平均满载率=各AB路段满载率的平均值。 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T14ResultFlowMorningAverageServiceImpl extends ServiceImpl<T14ResultFlowMorningAverageMapper, T14ResultFlowMorningAverage> implements T14ResultFlowMorningAverageService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T14ResultFlowMorningAverageMapper morningAverageMapper;
    @Override
    public T14MorningAverageResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T14ResultFlowMorningAverage> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T14ResultFlowMorningAverage> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T14ResultFlowMorningAverage> list1 = morningAverageMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T14MorningAverageResultReturn t14MorningAverageResultReturn = new T14MorningAverageResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T14ResultFlowMorningAverage> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14MorningAverageResultReturn.setRedisKey(s);
                t14MorningAverageResultReturn.setT14ResultFlowMorningAverages(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14MorningAverageResultReturn.setRedisKey(s);
                t14MorningAverageResultReturn.setT14ResultFlowMorningAverages(list);
            }
        }
        return t14MorningAverageResultReturn;
    }

    @Override
    public List<T14ResultFlowMorningAverage> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T14ResultFlowMorningAverage> t14ResultFlowMorningAverages = jsonArray.toJavaList(T14ResultFlowMorningAverage.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("line_begin");
        column.add("line_end");
        column.add("full_rate_average");
        column.add("direction");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t14ResultFlowMorningAverages.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T14ResultFlowMorningAverage t14ResultFlowMorningAverage = t14ResultFlowMorningAverages.get(i);
            dataMap.put("line_name", t14ResultFlowMorningAverage.getLineName());
            dataMap.put("line_begin", t14ResultFlowMorningAverage.getLineBegin());
            dataMap.put("line_end", t14ResultFlowMorningAverage.getLineEnd());
            dataMap.put("full_rate_average", t14ResultFlowMorningAverage.getFullRateAverage());
            dataMap.put("direction", t14ResultFlowMorningAverage.getDirection());
            dataMap.put("run_date", t14ResultFlowMorningAverage.getRunDate());


            data.add(dataMap);
        }

        ExportExcel.exportExcel("t14ResultFlowMorningAverage", column, data, request, response);
    }
    private List<T14ResultFlowMorningAverage> ListSplit(List<T14ResultFlowMorningAverage> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T14ResultFlowMorningAverage> getT2ResultListSplit(List<T14ResultFlowMorningAverage> list, QueryDTO queryDTO) {
        List<T14ResultFlowMorningAverage> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
