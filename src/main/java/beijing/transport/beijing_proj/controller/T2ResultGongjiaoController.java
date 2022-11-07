package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T2ResultGongjiaoService;
import beijing.transport.beijing_proj.utils.RedisUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 指标2计算结果：计算公交线路公里客运周转量 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/t2ResultGongjiao")
public class T2ResultGongjiaoController {

    @Resource
    private T2ResultGongjiaoService t2ResultGongjiaoService;

    @PostMapping("/t2Gongjiao")
    public GongjiaoResponse queryT2Gongjiao(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t2ResultGongjiaoService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT2Gongjiao() {
        return new GongjiaoResponse().data(t2ResultGongjiaoService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t2ResultGongjiaoService.exportExcel(redisKey, request, response);
    }
}
