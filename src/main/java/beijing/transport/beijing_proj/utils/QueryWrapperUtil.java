package beijing.transport.beijing_proj.utils;

import beijing.transport.beijing_proj.bean.T3ResultGongjiao;
import beijing.transport.beijing_proj.entity.QueryWrapperConsHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Jinglin
 * @Date: 2022/09/28
 * @Description:
 */

public class QueryWrapperUtil {
    public static QueryWrapper<T3ResultGongjiao> constructT3GongjiaoWrapper(QueryWrapperConsHelper helper) {

        List<String> dates = splitDate(helper.getDates());

        QueryWrapper<T3ResultGongjiao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("line_name", helper.getLineName());
        if (helper.getDirection() != null) {
            queryWrapper.eq("direction", helper.getDirection());
        }
        for (int i = 0; i < dates.size(); i++) {
            queryWrapper.eq("run_date", dates.get(i));
        }
        return queryWrapper;
    }

    static List<String> splitDate(String s) {
        String[] split = s.split(",");
        return Arrays.asList(split);
    }

}
