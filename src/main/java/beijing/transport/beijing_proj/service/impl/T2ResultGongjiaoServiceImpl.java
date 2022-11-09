package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T2ResultGongjiao;
import beijing.transport.beijing_proj.bean.T2ResultGuidao;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T2GongjiaoResultReturn;
import beijing.transport.beijing_proj.mapper.T2ResultGongjiaoMapper;
import beijing.transport.beijing_proj.service.T2ResultGongjiaoService;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 指标2计算结果：计算公交线路公里客运周转量 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T2ResultGongjiaoServiceImpl extends ServiceImpl<T2ResultGongjiaoMapper, T2ResultGongjiao> implements T2ResultGongjiaoService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T2ResultGongjiaoMapper t2ResultGongjiaoMapper;

    @Override
    public T2GongjiaoResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T2ResultGongjiao> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T2ResultGongjiao> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T2ResultGongjiao> list1 = t2ResultGongjiaoMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T2GongjiaoResultReturn t2ResultReturn = new T2GongjiaoResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T2ResultGongjiao> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t2ResultReturn.setRedisKey(s);
                t2ResultReturn.setT2ResultGongjiaos(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t2ResultReturn.setRedisKey(s);
                t2ResultReturn.setT2ResultGongjiaos(list);
            }
        }
        return t2ResultReturn;
    }

    @Override
    public List<T2ResultGongjiao> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T2ResultGongjiao> t2ResultGongjiaos = jsonArray.toJavaList(T2ResultGongjiao.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("run_date");
        column.add("exchange_distance");
        column.add("line_begin");
        column.add("line_end");
        column.add("ic_distance_sum");
        column.add("ic_card_num");
        column.add("direction");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t2ResultGongjiaos.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T2ResultGongjiao gongjiao = t2ResultGongjiaos.get(i);
            dataMap.put("line_name", gongjiao.getLineName());
            dataMap.put("run_date", gongjiao.getRunDate());
            dataMap.put("exchange_distance", gongjiao.getExchangeDistance());
            dataMap.put("line_begin", gongjiao.getLineBegin());
            dataMap.put("line_end", gongjiao.getLineEnd());
            dataMap.put("ic_distance_sum", gongjiao.getIcDistanceSum());
            dataMap.put("ic_card_num", gongjiao.getIcCardNum());
            dataMap.put("direction", gongjiao.getDirection());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("T2ResultGongjiao", column, data, request, response);

    }


    private List<T2ResultGongjiao> ListSplit(List<T2ResultGongjiao> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T2ResultGongjiao> getT2ResultListSplit(List<T2ResultGongjiao> list, QueryDTO queryDTO) {
        List<T2ResultGongjiao> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }

}
