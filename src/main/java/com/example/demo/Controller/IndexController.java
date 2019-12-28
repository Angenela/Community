package com.example.demo.Controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    //@GetMapping = @RequestMapping(method=RequestMethod.GET),@PostMapping = @RequestMapping(method=RequestMethod.POST)
    //"/"就是虚拟路径
    @GetMapping("/")
    //HttpServletRequest和HttpServletResponse接口会自动实例化
    public String hello(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    //将user放入session中，前端就可以判断是登录还是已登录
                }
                break;
            }
        }

        //返回值为templates内的html文件，即跳转到该html文件，虚拟路径为"/"
        return "index";
    }
}
