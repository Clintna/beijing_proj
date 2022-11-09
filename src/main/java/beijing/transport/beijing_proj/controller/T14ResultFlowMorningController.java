package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T14ResultFlowMorningService;
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
 * 指标14计算结果：计算每一条公交线路AB段在早高峰的断面流量（晚高峰时间段为7:00~9:00） 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t14ResultFlowMorning")
public class T14ResultFlowMorningController {
    @Resource
    private T14ResultFlowMorningService t14ResultFlowMorningService;

    @PostMapping("/t14ResultFlowMorning")
    public GongjiaoResponse queryT14M(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t14ResultFlowMorningService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT14M() {
        return new GongjiaoResponse().data(t14ResultFlowMorningService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t14ResultFlowMorningService.exportExcel(redisKey, request, response);
    }
}
