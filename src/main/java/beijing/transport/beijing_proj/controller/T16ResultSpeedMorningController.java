package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T16ResultSpeedMorningService;
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
 * 指标16计算结果：计算每一条公交线路AB段在早高峰的速度（晚高峰时间段为7:00~9:00） 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t16ResultSpeedMorning")
public class T16ResultSpeedMorningController {
    @Resource
    private T16ResultSpeedMorningService t16ResultSpeedMorningService;

    @PostMapping("/t16ResultSpeedMorning")
    public GongjiaoResponse queryT16M(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t16ResultSpeedMorningService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT16M() {
        return new GongjiaoResponse().data(t16ResultSpeedMorningService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t16ResultSpeedMorningService.exportExcel(redisKey, request, response);
    }
}
