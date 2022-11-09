package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T14ResultFlowEvening;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T14EveningResultReturn;
import beijing.transport.beijing_proj.mapper.T14ResultFlowEveningMapper;
import beijing.transport.beijing_proj.service.T14ResultFlowEveningService;
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
 * 指标14计算结果：计算每一条公交线路AB段在晚高峰的断面流量（晚高峰时间段为17:00~19:00） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T14ResultFlowEveningServiceImpl extends ServiceImpl<T14ResultFlowEveningMapper, T14ResultFlowEvening> implements T14ResultFlowEveningService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T14ResultFlowEveningMapper mapper;
    @Override
    public T14EveningResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T14ResultFlowEvening> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());

        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T14ResultFlowEvening> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T14ResultFlowEvening> list1 = mapper.selectList(wrapper);
            list.addAll(list1);
        });
        T14EveningResultReturn t14EveningResultReturn = new T14EveningResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T14ResultFlowEvening> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14EveningResultReturn.setRedisKey(s);
                t14EveningResultReturn.setT14ResultFlowEvenings(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t14EveningResultReturn.setRedisKey(s);
                t14EveningResultReturn.setT14ResultFlowEvenings(list);
            }
        }
        return t14EveningResultReturn;
    }

    @Override
    public List<T14ResultFlowEvening> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T14ResultFlowEvening> t14ResultFlowEvenings  = jsonArray.toJavaList(T14ResultFlowEvening.class);
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
        for (int i = 0; i < t14ResultFlowEvenings.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T14ResultFlowEvening t14ResultFlowEvening = t14ResultFlowEvenings.get(i);
            dataMap.put("line_name", t14ResultFlowEvening.getLineName());
            dataMap.put("line_begin", t14ResultFlowEvening.getLineBegin());
            dataMap.put("line_end", t14ResultFlowEvening.getLineEnd());
            dataMap.put("station_orderid_a", t14ResultFlowEvening.getStationOrderidA());
            dataMap.put("station_orderid_b", t14ResultFlowEvening.getStationOrderidB());
            dataMap.put("flow_a", t14ResultFlowEvening.getFlowA());
            dataMap.put("flow_b", t14ResultFlowEvening.getFlowB());
            dataMap.put("run_date", t14ResultFlowEvening.getRunDate());
            dataMap.put("flow_ab", t14ResultFlowEvening.getFlowAb());
            dataMap.put("flow_ab_rate", t14ResultFlowEvening.getFlowAbRate());
            dataMap.put("direction", t14ResultFlowEvening.getDirection());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("t14ResultFlowEvening", column, data, request, response);
    }
    private List<T14ResultFlowEvening> ListSplit(List<T14ResultFlowEvening> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T14ResultFlowEvening> getT2ResultListSplit(List<T14ResultFlowEvening> list, QueryDTO queryDTO) {
        List<T14ResultFlowEvening> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
