package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.bean.T3ResultGuidao;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.QueryWrapperConsHelper;
import beijing.transport.beijing_proj.entity.T3GongjiaoResultReturn;
import beijing.transport.beijing_proj.entity.T3GuidaoResultReturn;
import beijing.transport.beijing_proj.mapper.T3ResultGongjiaoMapper;
import beijing.transport.beijing_proj.service.T3ResultGongjiaoService;
import beijing.transport.beijing_proj.utils.ExportExcel;
import beijing.transport.beijing_proj.utils.QueryWrapperUtil;
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
 * 指标3计算结果：线路客流不均衡系数（公交） 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@Service
public class T3ResultGongjiaoServiceImpl extends ServiceImpl<T3ResultGongjiaoMapper, T3ResultGongjiao> implements T3ResultGongjiaoService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T3ResultGongjiaoMapper t3ResultGongjiaoMapper;

    @Override
    public T3GongjiaoResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T3ResultGongjiao> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T3ResultGongjiao> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T3ResultGongjiao> list1 = t3ResultGongjiaoMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T3GongjiaoResultReturn t2ResultReturn = new T3GongjiaoResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T3ResultGongjiao> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t2ResultReturn.setRedisKey(s);
                t2ResultReturn.setT3ResultGongjiaos(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t2ResultReturn.setRedisKey(s);
                t2ResultReturn.setT3ResultGongjiaos(list);
            }
        }
        return t2ResultReturn;
    }

    @Override
    public List<T3ResultGongjiao> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T3ResultGongjiao> t3ResultGongjiaos = jsonArray.toJavaList(T3ResultGongjiao.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("run_date");
        column.add("standard");
        column.add("line_begin");
        column.add("line_end");


        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t3ResultGongjiaos.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T3ResultGongjiao guidao = t3ResultGongjiaos.get(i);
            dataMap.put("line_name", guidao.getLineName());
            dataMap.put("run_date", guidao.getRunDate());
            dataMap.put("line_begin", guidao.getLineBegin());
            dataMap.put("line_end", guidao.getLineEnd());
            dataMap.put("standard", guidao.getStandard());

            data.add(dataMap);
        }

        ExportExcel.exportExcel("T3ResultGongjiao", column, data, request, response);

    }

    private List<T3ResultGongjiao> ListSplit(List<T3ResultGongjiao> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T3ResultGongjiao> getT2ResultListSplit(List<T3ResultGongjiao> list, QueryDTO queryDTO) {
        List<T3ResultGongjiao> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }


}
