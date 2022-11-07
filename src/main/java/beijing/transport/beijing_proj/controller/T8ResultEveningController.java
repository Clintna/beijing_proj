package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T8ResultEveningService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 指标8计算结果：晚高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为17:00~19:00） 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t8ResultEvening")
public class T8ResultEveningController {
    @Resource
    private T8ResultEveningService t8ResultEveningService;

    @PostMapping("/t8Evening")
    public GongjiaoResponse queryTt8Evening(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t8ResultEveningService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllTt8Evening() {
        return new GongjiaoResponse().data(t8ResultEveningService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t8ResultEveningService.exportExcel(redisKey, request, response);
    }
}
