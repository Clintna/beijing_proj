package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T6ResultService;
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
 * 指标6计算结果：线路发车间隔不均衡系数 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t6Result")
public class T6ResultController {
    @Resource
    private T6ResultService t6ResultService;

    @PostMapping("/t6")
    public GongjiaoResponse queryT6(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t6ResultService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT6() {
        return new GongjiaoResponse().data(t6ResultService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t6ResultService.exportExcel(redisKey, request, response);
    }
}
