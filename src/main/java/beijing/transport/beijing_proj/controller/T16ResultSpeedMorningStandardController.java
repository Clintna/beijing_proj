package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T16ResultSpeedMorningStarndardService;
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
 * 指标6计算结果：早高峰公交站间速度可靠性 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t16ResultSpeedMorningStandard")
public class T16ResultSpeedMorningStandardController {
    @Resource
    private T16ResultSpeedMorningStarndardService t16ResultSpeedMorningStarndardService;

    @PostMapping("/t16ResultSpeedMorningStandard")
    public GongjiaoResponse queryT16MS(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t16ResultSpeedMorningStarndardService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT16MS() {
        return new GongjiaoResponse().data(t16ResultSpeedMorningStarndardService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t16ResultSpeedMorningStarndardService.exportExcel(redisKey, request, response);
    }
}
