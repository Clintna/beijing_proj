package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T2ResultGuidaoService;
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
 * 指标2计算结果：计算轨道线路公里客运周转量 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t2ResultGuidao")
public class T2ResultGuidaoController {
    @Resource
    private T2ResultGuidaoService t2ResultGuidaoService;

    @PostMapping("/t2Guidao")
    public GongjiaoResponse queryT2Gongjiao(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t2ResultGuidaoService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT2Gongjiao() {
        return new GongjiaoResponse().data(t2ResultGuidaoService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t2ResultGuidaoService.exportExcel(redisKey, request, response);
    }
}
