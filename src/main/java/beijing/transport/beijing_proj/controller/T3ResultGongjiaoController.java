package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T3ResultGongjiaoService;
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
 * 指标3计算结果：线路客流不均衡系数（公交） 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t3ResultGongjiao")
public class T3ResultGongjiaoController {
    @Resource
    private T3ResultGongjiaoService t3ResultGongjiaoService;

    @PostMapping("/t3Gongjiao")
    public GongjiaoResponse queryT3Gongjiao(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t3ResultGongjiaoService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT3Gongjiao() {
        return new GongjiaoResponse().data(t3ResultGongjiaoService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t3ResultGongjiaoService.exportExcel(redisKey, request, response);
    }
}
