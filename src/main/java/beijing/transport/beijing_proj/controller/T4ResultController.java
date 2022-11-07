package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T4ResultService;
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
 * 指标4计算结果：线路早晚高峰与全天运营速度系数比 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t4Result")
public class T4ResultController {
    @Resource
    private T4ResultService t4ResultService;

    @PostMapping("/t4")
    public GongjiaoResponse queryT4(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t4ResultService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT4() {
        return new GongjiaoResponse().data(t4ResultService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t4ResultService.exportExcel(redisKey, request, response);
    }
}
