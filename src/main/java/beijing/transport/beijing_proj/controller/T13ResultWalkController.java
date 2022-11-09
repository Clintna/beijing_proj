package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T13ResultWalkService;
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
 * 指标13：接驳站点之间的步行距离 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t13ResultWalk")
public class T13ResultWalkController {
    @Resource
    private T13ResultWalkService t13ResultWalkService;

    @PostMapping("/t13ResultWalk")
    public GongjiaoResponse queryT13(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t13ResultWalkService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT13() {
        return new GongjiaoResponse().data(t13ResultWalkService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t13ResultWalkService.exportExcel(redisKey, request, response);
    }
}
