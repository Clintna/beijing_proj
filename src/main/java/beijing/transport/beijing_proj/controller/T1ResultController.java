package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T1ResultService;
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
 * 指标1：线路中非重复的OD客运量占全部客运量的比 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-08
 */
@RestController
@RequestMapping("/t1Result")
public class T1ResultController {
    @Resource
    private T1ResultService t1ResultService;

    @PostMapping("/t1")
    public GongjiaoResponse queryT1(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t1ResultService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT1() {
        return new GongjiaoResponse().data(t1ResultService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t1ResultService.exportExcel(redisKey, request, response);
    }
}
