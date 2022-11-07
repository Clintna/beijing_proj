package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T2ResultGuidao;
import beijing.transport.beijing_proj.bean.T3ResultGuidao;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T3GuidaoResultReturn;
import beijing.transport.beijing_proj.mapper.T3ResultGuidaoMapper;
import beijing.transport.beijing_proj.service.T3ResultGuidaoService;
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
 * 指标3计算结果：线路客流不均衡系数（轨道） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T3ResultGuidaoServiceImpl extends ServiceImpl<T3ResultGuidaoMapper, T3ResultGuidao> implements T3ResultGuidaoService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T3ResultGuidaoMapper t3ResultGuidaoMapper;
    @Override
    public T3GuidaoResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T3ResultGuidao> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        List<T3ResultGuidao> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T3ResultGuidao> list1 = t3ResultGuidaoMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T3GuidaoResultReturn t2ResultReturn = new T3GuidaoResultReturn();
        if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

            List<T3ResultGuidao> newList = ListSplit(list, queryDTO);
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(newList));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t2ResultReturn.setRedisKey(s);
            t2ResultReturn.setT3ResultGuidaos(newList);
        } else {
            String s = TaskIdGenerator.nextId();
            redisUtil.set(s, JSON.toJSONString(list));
            redisUtil.expire(s, 2L, TimeUnit.HOURS);
            t2ResultReturn.setRedisKey(s);
            t2ResultReturn.setT3ResultGuidaos(list);
        }
        return t2ResultReturn;
    }

    @Override
    public List<T3ResultGuidao> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T3ResultGuidao> t3ResultGuidaos = jsonArray.toJavaList(T3ResultGuidao.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("run_date");
        column.add("standard");
        column.add("line_begin");
        column.add("line_end");


        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t3ResultGuidaos.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T3ResultGuidao guidao = t3ResultGuidaos.get(i);
            dataMap.put("line_name", guidao.getLineName());
            dataMap.put("run_date", guidao.getRunDate());
            dataMap.put("line_begin", guidao.getLineBegin());
            dataMap.put("line_end", guidao.getLineEnd());
            dataMap.put("standard", guidao.getStandard());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("T3ResultGuidao", column, data, request, response);
    }


    private List<T3ResultGuidao> ListSplit(List<T3ResultGuidao> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T3ResultGuidao> getT2ResultListSplit(List<T3ResultGuidao> list, QueryDTO queryDTO) {
        List<T3ResultGuidao> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }

}
