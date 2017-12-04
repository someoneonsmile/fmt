package com.closer.ws.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.closer.ws.common.BaseController;
import com.closer.ws.common.ResData;
import com.closer.ws.common.Single;
import com.closer.ws.entity.SysUser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Controller
public class IndexController extends BaseController {

    @RequestMapping("/index")
    public String index() {
        String sessionId = session.getId();
        int maxInactiveInterval = session.getMaxInactiveInterval();
        System.out.println(maxInactiveInterval);
        System.out.println(sessionId);
        Single.Inner success = Single.success();
        Single.Inner s = Single.success();
        System.out.println(success == s);
        return "index";
    }

    /**
     * 跳转登陆
     */
    @RequestMapping("/toLogin")
    public String toLogin(String requestUrl, Model model) {
        model.addAttribute("requestUrl", requestUrl);
        return "login";
    }

    /**
     * 登陆判断， 生成令牌， 携带令牌返回
     */
    @RequestMapping("/login")
    public String login(String name, String pwd, String requestUrl, Model model) {
        if (name != null) {
            // 登陆成功
            session.setAttribute("isLogin", true);
            SysUser sysUser = new SysUser();
            sysUser.setUserName(name);
            // 存储用户信息
            session.setAttribute("loginUser", sysUser);
            // 存储token
            String token = UUID.randomUUID().toString();
            session.setAttribute("token", userConfigure.getTokenPrefix() + token);
            // 存储serverList
            List<String> serverList = new ArrayList<>();
            serverList.add(requestUrl);
            session.setAttribute(userConfigure.getTokenServersName(), serverList);
            return "redirect:" + requestUrl + "?token=" + token;
        } else {
            return "redirect:/toLogin?requestUrl=" + requestUrl;
        }
    }

    /**
     * 获取令牌
     */
    @RequestMapping("/getToken")
    @ResponseBody
    public String getToken() throws IOException {
        String s = request.getRequestURL().toString();
        boolean isLogin = (boolean) session.getAttribute("isLogin");
        // 没有登陆，跳转到登陆( 会生成并分发令牌 )
        if (!isLogin) {
            response.sendRedirect("/toLogin?requestUrl=" + s);
            return null;
        }
        // 没有注册时注册
        List<String> servers = (List<String>) session.getAttribute(userConfigure.getTokenServersName());
        if (!servers.contains(s)) {
            servers.add(s);
        }
        return (String) session.getAttribute(userConfigure.getTokenName());
    }

    /**
     * 检查令牌是否有效
     */
    @RequestMapping("/checkToken")
    public boolean checkToken(String token) {
        SetOperations<Serializable, Object> setOperations = redisTemplate.opsForSet();
        String requestURL = request.getRequestURL().toString();
        Object isLogin = session.getAttribute("isLogin");
        return (isLogin != null && (boolean) isLogin)
                && redisTemplate.<String>hasKey(token)
                && setOperations.isMember(userConfigure.getTokenPrefix() + token, requestURL);
    }

    /**
     * 退出登陆
     */
    @RequestMapping("/logout")
    public boolean logout() throws InterruptedException {
        List<String> servers = (List<String>) session.getAttribute(userConfigure.getTokenServersName());
        if (servers != null) {
            CountDownLatch countDownLatch = new CountDownLatch(servers.size());
            for (String server : servers) {
                new Thread(() -> {
                    CloseableHttpClient client = HttpClients.createMinimal();
                    HttpPost httpPost = new HttpPost(server + "/logout");
                    try {
                        boolean reTry = true;
                        int maxReTry = 3;
                        // 失败重试
                        for (int i = 0; reTry && i < maxReTry; i++) {
                            CloseableHttpResponse execute = client.execute(httpPost);
                            HttpEntity entity = execute.getEntity();
                            String json = EntityUtils.toString(entity);
                            JSONObject jsonObject = JSON.parseObject(json);
                            Boolean success = jsonObject.getBoolean("success");
                            reTry = success == null || !success;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            // 等待线程执行完成, 超时5S
            countDownLatch.await(5, TimeUnit.SECONDS);
            // 销毁session
            session.invalidate();
        }
        return true;
    }

    /**
     * 获取用户信息
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResData<SysUser> getUserInfo(String token){
        boolean b = checkToken(token);
        if(b){
            SysUser loginUser = (SysUser) session.getAttribute("loginUser");
            ResData<SysUser> success = ResData.success();
            success.setData(loginUser);
            return success;
        }
        return new ResData<>(false, 500, "token no effic!", null);
    }
}
