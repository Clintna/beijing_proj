package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T8ResultMorningService;
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
 * 指标8计算结果：早高峰时段公交-轨道在共线区域范围内的运营速度比（早高峰时间段为7:00~9:00） 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t8ResultMorning")
public class T8ResultMorningController {
    @Resource
    private T8ResultMorningService t8ResultMorningService;

    @PostMapping("/t8Morning")
    public GongjiaoResponse queryT8Morning(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t8ResultMorningService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT8Morning() {
        return new GongjiaoResponse().data(t8ResultMorningService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t8ResultMorningService.exportExcel(redisKey, request, response);
    }
}
