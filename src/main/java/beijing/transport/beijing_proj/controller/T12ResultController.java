package beijing.transport.beijing_proj.controller;

import beijing.transport.beijing_proj.entity.GongjiaoResponse;
import beijing.transport.beijing_proj.entity.QueryDTO;
import beijing.transport.beijing_proj.service.T12ResultService;
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
 * 指标12计算结果：公交-轨道共线重复距离（只计算共线线路）	计算方法：读取t_station_gis_nearby_gongxian，计算公交ab站点的距离 前端控制器
 * </p>
 *
 * @author Jinglin
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/t12Result")
public class T12ResultController {
    @Resource
    private T12ResultService t12ResultService;

    @PostMapping("/t12")
    public GongjiaoResponse queryT12(@Validated @RequestBody QueryDTO queryDTO) {
        return new GongjiaoResponse().data(t12ResultService.query(queryDTO));
    }

    @PostMapping("/listAll")
    public GongjiaoResponse queryAllT12() {
        return new GongjiaoResponse().data(t12ResultService.queryAll());
    }

    @PostMapping("/export")
    public void exportExcel(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        t12ResultService.exportExcel(redisKey, request, response);
    }
}
