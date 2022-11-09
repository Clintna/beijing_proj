package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T14ResultFlowEveningService;
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
 * 指标14计算结果：计算每一条公交线路AB段在晚高峰的断面流量（晚高峰时间段为17:00~19:00） 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t14ResultFlowEvening")
public class T14ResultFlowEveningController {
    @Resource
    private T14ResultFlowEveningService t14ResultFlowEveningService;

    @PostMapping("/t14ResultFlowEvening")
    public GongjiaoResponse queryT14E(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t14ResultFlowEveningService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT14E() {
        return new GongjiaoResponse().data(t14ResultFlowEveningService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t14ResultFlowEveningService.exportExcel(redisKey, request, response);
    }
}
