package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.SkipHttps.getUnsafeClient;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
//@component的作用是将一个java类加入到spring容器中管理，省去了<bean id="" class="">这句配置
// 取而代之的是<context component-scan base-package="包名" />，负责扫描改包下的所有包名。
public class GitHubProvider {

    //用于获取accessToken用与和GitHub交互获取用户信息
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client =  getUnsafeClient.getUnsafeOkHttpClient();           //原本为：new OkHttpClient();跳过https认证
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()             //发送请求
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {           //获取响应对象
            String string = response.body().string();       //对响应对象的响应信息的字符串进行拆分获取accessToken
            String accessToken = string.split("&")[0].split("=")[1];
            return accessToken;
        } catch (Exception e) {         //当检测到任何问题抛出异常,如accessToken的格式有误
            e.printStackTrace();
        }
        return null;
    }

    //获取用户信息
    public GitHubUser getGiHubUser(String accessToken) {
        OkHttpClient client = getUnsafeClient.getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);         //通过字节码文件将json转换为相应的对象属性
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
