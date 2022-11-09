package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T11ResultBanciTimeFirstMatchService;
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
 * 接驳线路运营时间匹配指数：最早班车的匹配度 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t11ResultBanciTimeFirstMatch")
public class T11ResultBanciTimeFirstMatchController {
    @Resource
    private T11ResultBanciTimeFirstMatchService t11ResultBanciTimeFirstMatchService;

    @PostMapping("/t11ResultBanciTimeFirstMatch")
    public GongjiaoResponse queryT111(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t11ResultBanciTimeFirstMatchService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT111() {
        return new GongjiaoResponse().data(t11ResultBanciTimeFirstMatchService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t11ResultBanciTimeFirstMatchService.exportExcel(redisKey, request, response);
    }
}
