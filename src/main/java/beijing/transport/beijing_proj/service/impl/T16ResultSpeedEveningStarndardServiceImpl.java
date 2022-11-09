package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T16ResultSpeedEveningStarndard;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T16EveningStandardResultReturn;
import beijing.transport.beijing_proj.mapper.T16ResultSpeedEveningStarndardMapper;
import beijing.transport.beijing_proj.service.T16ResultSpeedEveningStarndardService;
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
 * 指标6计算结果：晚高峰公交站间速度可靠性 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T16ResultSpeedEveningStarndardServiceImpl extends ServiceImpl<T16ResultSpeedEveningStarndardMapper, T16ResultSpeedEveningStarndard> implements T16ResultSpeedEveningStarndardService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T16ResultSpeedEveningStarndardMapper mapper;
    @Override
    public T16EveningStandardResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T16ResultSpeedEveningStarndard> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T16ResultSpeedEveningStarndard> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T16ResultSpeedEveningStarndard> list1 = mapper.selectList(wrapper);
            list.addAll(list1);
        });
        T16EveningStandardResultReturn t16EveningStandardResultReturn = new T16EveningStandardResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T16ResultSpeedEveningStarndard> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16EveningStandardResultReturn.setRedisKey(s);
                t16EveningStandardResultReturn.setT16ResultSpeedEveningStarndards(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16EveningStandardResultReturn.setRedisKey(s);
                t16EveningStandardResultReturn.setT16ResultSpeedEveningStarndards(list);
            }
        }
        return t16EveningStandardResultReturn;
    }

    @Override
    public List<T16ResultSpeedEveningStarndard> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T16ResultSpeedEveningStarndard> t16ResultSpeedEveningStarndards = jsonArray.toJavaList(T16ResultSpeedEveningStarndard.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("standard");
        column.add("line_begin");
        column.add("line_end");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t16ResultSpeedEveningStarndards.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T16ResultSpeedEveningStarndard t16ResultSpeedEveningStarndard = t16ResultSpeedEveningStarndards.get(i);
            dataMap.put("line_name", t16ResultSpeedEveningStarndard.getLineName());
            dataMap.put("standard", t16ResultSpeedEveningStarndard.getStandard());
            dataMap.put("line_begin", t16ResultSpeedEveningStarndard.getLineBegin());
            dataMap.put("line_end", t16ResultSpeedEveningStarndard.getLineEnd());
            dataMap.put("run_date", t16ResultSpeedEveningStarndard.getRunDate());
            data.add(dataMap);
        }

        ExportExcel.exportExcel("t16ResultSpeedEveningStarndard", column, data, request, response);
    }
    private List<T16ResultSpeedEveningStarndard> ListSplit(List<T16ResultSpeedEveningStarndard> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T16ResultSpeedEveningStarndard> getT2ResultListSplit(List<T16ResultSpeedEveningStarndard> list, QueryDTO queryDTO) {
        List<T16ResultSpeedEveningStarndard> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
