package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T14ResultFlowEveningAverageService;
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
 * 指标14计算结果：计算每一条公交线路晚高峰的平均满载率（晚高峰时间段为17:00~19:00），平均满载率=各AB路段满载率的平均值。 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t14ResultFlowEveningAverage")
public class T14ResultFlowEveningAverageController {
    @Resource
    private T14ResultFlowEveningAverageService t14ResultFlowEveningAverageService;

    @PostMapping("/t14ResultFlowEveningAverage")
    public GongjiaoResponse queryT14EA(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t14ResultFlowEveningAverageService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT14EA() {
        return new GongjiaoResponse().data(t14ResultFlowEveningAverageService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t14ResultFlowEveningAverageService.exportExcel(redisKey, request, response);
    }
}
