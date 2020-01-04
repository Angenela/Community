package com.example.demo.Controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GitHubUser;
import com.example.demo.provider.GitHubProvider;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
//注入服务

public class AuthorizeController {

    @Autowired
    //@Autowired:完成自动装配的工作,即自动实例化好对象装进spring容器中。通过@Autowired的使用来消除set,get方法。
    private GitHubProvider gitHubProvider;

    //@Value的作用和@Autowired相似，即读取application.properties中key为${写key的地方}的值并赋值到下面的变量
    @Value("${Client.id}")
    private String Client_id;
    @Value("${Client.secret}")
    private String Client_secret;
    @Value("${Redirect.uri}")
    private String Redirect_uri;

    @Autowired          //此报错为idea的问题,在UserMapper加上@Repository即可去掉
    //实例化该接口的的继承类的对象，会默认要求对象存在，所以接口会报错
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    //@RequestParam:这个注解的作用是从前端返回的参数中获取name为"xxx"的值并赋值给其后的变量
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response
    ) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(Redirect_uri);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getGiHubUser(accessToken);

        if (gitHubUser != null && gitHubUser.getId() != null) {
            User user = new User();
            //UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。它保证对在同一时空中的所有机器都是唯一的
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(gitHubUser.getId().toString());
            user.setName(gitHubUser.getName());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            //写cookie和session,session在indexController中
            response.addCookie(new Cookie("token", token));

            return "redirect:/";

        } else {
            //重新登陆
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){

        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie cookie1 = new Cookie("JSESSIONID",null);

        return "redirect:/";
    }
}
