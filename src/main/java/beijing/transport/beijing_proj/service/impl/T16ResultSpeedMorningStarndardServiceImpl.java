package beijing.transport.beijing_proj.service.impl;

import beijing.transport.beijing_proj.bean.T16ResultSpeedMorningStarndard;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.entity.T16MorningStandardResultReturn;
import beijing.transport.beijing_proj.mapper.T16ResultSpeedMorningStarndardMapper;
import beijing.transport.beijing_proj.service.T16ResultSpeedMorningStarndardService;
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
 * 指标6计算结果：早高峰公交站间速度可靠性 服务实现类
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@Service
public class T16ResultSpeedMorningStarndardServiceImpl extends ServiceImpl<T16ResultSpeedMorningStarndardMapper, T16ResultSpeedMorningStarndard> implements T16ResultSpeedMorningStarndardService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private T16ResultSpeedMorningStarndardMapper t16ResultSpeedMorningStarndardMapper;
    @Override
    public T16MorningStandardResultReturn query(QueryDTO queryDTO) {
        QueryWrapper<T16ResultSpeedMorningStarndard> wrapper = new QueryWrapper<>();
        wrapper.eq("line_name", queryDTO.getLineName());
        if (null != queryDTO.getLineBegin() && null != queryDTO.getLineEnd()) {
            wrapper.eq("line_begin", queryDTO.getLineBegin());
            wrapper.eq("line_end", queryDTO.getLineEnd());
        }
        List<T16ResultSpeedMorningStarndard> list = new ArrayList<>();
        String[] split = queryDTO.getDates().split(",");
        List<String> dates = Arrays.asList(split);
        dates.forEach(d -> {
            wrapper.eq("run_date", d);
            final List<T16ResultSpeedMorningStarndard> list1 = t16ResultSpeedMorningStarndardMapper.selectList(wrapper);
            list.addAll(list1);
        });
        T16MorningStandardResultReturn t16MorningStandardResultReturn = new T16MorningStandardResultReturn();
        if (!CollectionUtils.isEmpty(list)) {
            if (queryDTO.getPage() != 0 && queryDTO.getLimit() != 0) {

                List<T16ResultSpeedMorningStarndard> newList = ListSplit(list, queryDTO);
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(newList));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16MorningStandardResultReturn.setRedisKey(s);
                t16MorningStandardResultReturn.setT16MorningStandardResultReturns(newList);
            } else {
                String s = TaskIdGenerator.nextId();
                redisUtil.set(s, JSON.toJSONString(list));
                redisUtil.expire(s, 2L, TimeUnit.HOURS);
                t16MorningStandardResultReturn.setRedisKey(s);
                t16MorningStandardResultReturn.setT16MorningStandardResultReturns(list);
            }
        }
        return t16MorningStandardResultReturn;
    }

    @Override
    public List<T16ResultSpeedMorningStarndard> queryAll() {
        return list();
    }

    @Override
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.parseArray(redisUtil.get(redisKey));
        List<T16ResultSpeedMorningStarndard> t16ResultSpeedMorningStarndards = jsonArray.toJavaList(T16ResultSpeedMorningStarndard.class);
        List<String> column = new ArrayList<>();
        column.add("line_name");
        column.add("standard");
        column.add("line_begin");
        column.add("line_end");
        column.add("run_date");

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < t16ResultSpeedMorningStarndards.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            T16ResultSpeedMorningStarndard t16ResultSpeedMorningStarndard = t16ResultSpeedMorningStarndards.get(i);
            dataMap.put("line_name", t16ResultSpeedMorningStarndard.getLineName());
            dataMap.put("standard", t16ResultSpeedMorningStarndard.getStandard());
            dataMap.put("line_begin", t16ResultSpeedMorningStarndard.getLineBegin());
            dataMap.put("line_end", t16ResultSpeedMorningStarndard.getLineEnd());
            dataMap.put("run_date", t16ResultSpeedMorningStarndard.getRunDate());
            data.add(dataMap);
        }

        ExportExcel.exportExcel("t16ResultSpeedMorningStarndard", column, data, request, response);
    }
    private List<T16ResultSpeedMorningStarndard> ListSplit(List<T16ResultSpeedMorningStarndard> list, QueryDTO queryDTO) {
        return getT2ResultListSplit(list, queryDTO);
    }

    static List<T16ResultSpeedMorningStarndard> getT2ResultListSplit(List<T16ResultSpeedMorningStarndard> list, QueryDTO queryDTO) {
        List<T16ResultSpeedMorningStarndard> newList;
        int rows = queryDTO.getLimit();
        int page = queryDTO.getPage();
        int size = list.size();
        newList = list.subList(rows * (page - 1), (Math.min((rows * page), size)));
        return newList;
    }
}
