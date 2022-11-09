package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T11ResultBanciTimeService;
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
 * 接驳线路运营时间匹配指数：公交站点与轨道站点为接驳关系，记录公交站点和轨道站点最早班次时间、最晚班次时间 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t11ResultBanciTime")
public class T11ResultBanciTimeController {
    @Resource
    private T11ResultBanciTimeService t11ResultBanciTimeService;

    @PostMapping("/t11ResultBanciTime")
    public GongjiaoResponse queryT11(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t11ResultBanciTimeService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT11() {
        return new GongjiaoResponse().data(t11ResultBanciTimeService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t11ResultBanciTimeService.exportExcel(redisKey, request, response);
    }
}
