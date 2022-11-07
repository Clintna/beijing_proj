package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T10ResultService;
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
 * 指标10计算结果：公交下轨道上接驳统计数据 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t10Result")
public class T10ResultController {
    @Resource
    private T10ResultService t10ResultService;

    @PostMapping("/t10")
    public GongjiaoResponse queryT10(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t10ResultService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT10() {
        return new GongjiaoResponse().data(t10ResultService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t10ResultService.exportExcel(redisKey, request, response);
    }
}
