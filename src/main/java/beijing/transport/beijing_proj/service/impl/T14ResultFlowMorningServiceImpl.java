package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T14ResultFlowMorning;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T14MorningResultReturn;
import beijing.transport.beijing_proj.mapper.T14ResultFlowMorningMapper;
import beijing.transport.beijing_proj.service.T14ResultFlowMorningService;
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
 * 指标14计算结果：计算每一条公交线路AB段在早高峰的断面流量（晚高峰时间段为7:00~9:00） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T14ResultFlowMorningServiceImpl extends ServiceImpl<T14ResultFlowMorningMapper, T14ResultFlowMorning> implements T14ResultFlowMorningService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T14ResultFlowMorningMapper mapper;
    @Override
    public T14MorningResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T14ResultFlowMorning> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T14ResultFlowMorning> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T14ResultFlowMorning> list1 = mapper.selectList(wrapper);
            list.addAll(list1);
        });
        T14MorningResultReturn t14MorningResultReturn = new T14MorningResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T14ResultFlowMorning> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14MorningResultReturn.setRedisKey(s);
                t14MorningResultReturn.setT14ResultFlowMornings(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14MorningResultReturn.setRedisKey(s);
                t14MorningResultReturn.setT14ResultFlowMornings(list);
            }
        }
        return t14MorningResultReturn;
    }

    @Override
    public List<T14ResultFlowMorning> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T14ResultFlowMorning> t14ResultFlowMornings = jsonArray.toJavaList(T14ResultFlowMorning.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("line_begin");
        column.add("line_end");
        column.add("station_orderid_a");
        column.add("station_orderid_b");
        column.add("flow_a");
        column.add("flow_b");
        column.add("run_date");
        column.add("flow_ab");
        column.add("flow_ab_rate");
        column.add("direction");


        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t14ResultFlowMornings.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T14ResultFlowMorning t14ResultFlowMorning = t14ResultFlowMornings.get(i);
            dataMap.put("line_name", t14ResultFlowMorning.getLineName());
            dataMap.put("line_begin", t14ResultFlowMorning.getLineBegin());
            dataMap.put("line_end", t14ResultFlowMorning.getLineEnd());
            dataMap.put("station_orderid_a", t14ResultFlowMorning.getStationOrderidA());
            dataMap.put("station_orderid_b", t14ResultFlowMorning.getStationOrderidB());
            dataMap.put("flow_a", t14ResultFlowMorning.getFlowA());
            dataMap.put("flow_b", t14ResultFlowMorning.getFlowB());
            dataMap.put("run_date", t14ResultFlowMorning.getRunDate());
            dataMap.put("flow_ab", t14ResultFlowMorning.getFlowAb());
            dataMap.put("flow_ab_rate", t14ResultFlowMorning.getFlowAbRate());
            dataMap.put("direction", t14ResultFlowMorning.getDirection());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("t14ResultFlowMorning", column, data, request, response);
    }
    private List<T14ResultFlowMorning> ListSplit(List<T14ResultFlowMorning> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T14ResultFlowMorning> getT2ResultListSplit(List<T14ResultFlowMorning> list, QueryDTO queryDTO) {
        List<T14ResultFlowMorning> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
