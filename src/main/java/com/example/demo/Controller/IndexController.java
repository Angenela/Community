package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    //@GetMapping = @RequestMapping(method=RequestMethod.GET),@PostMapping = @RequestMapping(method=RequestMethod.POST)
    //"/"就是虚拟路径
    @GetMapping("/")
    public String hello(){

        //返回值为templates内的html文件，即跳转到该html文件，虚拟路径为"/"
        return  "index";
    }
}
