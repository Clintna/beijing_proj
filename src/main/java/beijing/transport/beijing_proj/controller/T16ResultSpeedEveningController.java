package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T16ResultSpeedEveningService;
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
 * 指标16计算结果：计算每一条公交线路AB段在晚高峰的速度（晚高峰时间段为17:00~19:00） 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t16ResultSpeedEvening")
public class T16ResultSpeedEveningController {
    @Resource
    private T16ResultSpeedEveningService t16ResultSpeedEveningService;

    @PostMapping("/t16ResultEvening")
    public GongjiaoResponse queryT16E(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t16ResultSpeedEveningService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT16E() {
        return new GongjiaoResponse().data(t16ResultSpeedEveningService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t16ResultSpeedEveningService.exportExcel(redisKey, request, response);
    }
}
