package bside.weo.plant.application.controller.in;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "테스트 API")
public class testAPIController {

    @GetMapping("/api/v1/hello1")
    public String hello1() {
        return "hello";
    }

    @GetMapping("/api/v1/hello2")
    public String hello2(@RequestParam String param) {
        return param;
    }
}
