package beijing.transport.beijing_proj.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Jinglin
 * @Date: 2022/11/08
 * @Description:
 */
@RestController
@RequestMapping("/unitSearch")
public class UnitSearchController {
    @PostMapping("testPush")
    void testPush(){
        System.out.println("123");
    }
}
