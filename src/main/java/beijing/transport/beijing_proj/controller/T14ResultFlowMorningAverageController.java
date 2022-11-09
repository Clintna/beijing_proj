package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T14ResultFlowMorningAverageService;
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
 * 指标14计算结果：计算每一条公交线路早高峰的平均满载率（晚高峰时间段为7:00~9:00），平均满载率=各AB路段满载率的平均值。 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t14ResultFlowMorningAverage")
public class T14ResultFlowMorningAverageController {
    @Resource
    private T14ResultFlowMorningAverageService t14ResultFlowMorningAverageService;

    @PostMapping("/t14ResultFlowMorningAverage")
    public GongjiaoResponse queryT14MA(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t14ResultFlowMorningAverageService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT14MA() {
        return new GongjiaoResponse().data(t14ResultFlowMorningAverageService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t14ResultFlowMorningAverageService.exportExcel(redisKey, request, response);
    }
}
