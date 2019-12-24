package com.example.demo.Controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GitHubUser;
import com.example.demo.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//注入服务
public class AuthorizeController {

    @Autowired
    //@Autowired:它可以对类成员变量、方法及构造函数进行标注,完成自动装配的工作。通过@Autowired的使用来消除set,get方法。
    private GitHubProvider gitHubProvider;

    //@Value的作用和@Autowired相似，即读取application.properties中key为${写key的地方}的值并赋值到下面的变量
    @Value("${Client.id}")
    private String Client_id;
    @Value("${Client.secret}")
    private String Client_secret;
    @Value("${Redirect.uri}")
    private String Redirect_uri;

    @GetMapping("/callback")
    //@RequestParam:这个注解的作用是从前端返回的参数中获取name为"xxx"的值并赋值给其后的变量
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
    ) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(Redirect_uri);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getGiHubUser(accessToken);

        if (user != null) {
            //写cookie和session

        } else {
            //重新登陆

        }

        return "index";
    }
}
