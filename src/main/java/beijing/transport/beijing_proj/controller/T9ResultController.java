package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T9ResultService;
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
 * 指标9计算结果：公交和轨道共线区域满载率的比值 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t9Result")
public class T9ResultController {
    @Resource
    private T9ResultService t9ResultService;

    @PostMapping("/t9")
    public GongjiaoResponse queryT9(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t9ResultService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT9() {
        return new GongjiaoResponse().data(t9ResultService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t9ResultService.exportExcel(redisKey, request, response);
    }
}
