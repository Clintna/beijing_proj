package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T5ResultService;
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
 * 指标5计算结果：线路运行时间不均衡系数 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t5Result")
public class T5ResultController {
    @Resource
    private T5ResultService t5ResultService;

    @PostMapping("/t5")
    public GongjiaoResponse queryT5(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t5ResultService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT5() {
        return new GongjiaoResponse().data(t5ResultService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t5ResultService.exportExcel(redisKey, request, response);
    }
}
