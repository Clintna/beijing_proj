package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T16ResultSpeedEveningStarndardService;
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
 * 指标6计算结果：晚高峰公交站间速度可靠性 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t16ResultSpeedEveningStandard")
public class T16ResultSpeedEveningStandardController {
    @Resource
    private T16ResultSpeedEveningStarndardService t16ResultSpeedEveningStarndardService;

    @PostMapping("/t16ResultEveningStandard")
    public GongjiaoResponse queryT16ES(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t16ResultSpeedEveningStarndardService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT16ES() {
        return new GongjiaoResponse().data(t16ResultSpeedEveningStarndardService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t16ResultSpeedEveningStarndardService.exportExcel(redisKey, request, response);
    }
}
