package com.closer.ws.controller.sso;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.closer.ws.common.BaseController;
import com.closer.ws.common.ResData;
import com.closer.ws.entity.SysUser;
import com.closer.ws.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class SsoController extends BaseController {

    /**
     * 跳转登陆
     */
    @RequestMapping( "/toLogin" )
    public String toLogin( String requestUrl, Model model ) {
        model.addAttribute("requestUrl", requestUrl);
        return "login";
    }


    /**
     * 登陆判断， 生成令牌， 携带授权码返回
     */
    @RequestMapping( "/login" )
    public String login( String name, String pwd, String callbackUrl, Model model ) {
        if ( name != null ) {
            // 登陆成功
            session.setAttribute("isLogin", true);
            SysUser sysUser = new SysUser();
            sysUser.setUserName(name);
            // 存储用户信息
            session.setAttribute("loginUser", sysUser);
            // 存储 authCode
            String authCode = UUIDUtils.getUUID();
            ValueOperations<Serializable, Object> opsForValue = redisTemplate.opsForValue();
            opsForValue.set(userConfigure.getAuthCodePrefix() + callbackUrl, authCode, userConfigure.getAuthCodeTimeout(), TimeUnit.MINUTES);
            return "redirect:" + callbackUrl + "?authCode=" + authCode;
        } else {
            return "redirect:/toLogin?requestUrl=" + callbackUrl;
        }
    }


    /**
     * 获取授权码
     */
    @RequestMapping( "/getAuthCode" )
    public String getAuthCode( String callbackUrl ) throws IOException {
        callbackUrl = URLDecoder.decode(callbackUrl, "UTF-8");
        String requestUrl = request.getRequestURL().toString();
        boolean isLogin = ( boolean ) session.getAttribute("isLogin");
        // 没有登陆，跳转到登陆( 会生成并分发授权码 )
        if ( !isLogin ) {
            return "forward:/toLogin?requestUrl=" + callbackUrl;
        }

        // 已登陆，从缓存中取 authCode 没有时生成并保存
        session.setMaxInactiveInterval(( int ) ( userConfigure.getTokenTimeout() * 60 * 60 ));
        ValueOperations<Serializable, Object> opsForValue = redisTemplate.opsForValue();
        String authCode = ( String ) opsForValue.get(userConfigure.getAuthCodePrefix() + callbackUrl);
        if ( authCode == null ) {
            authCode = UUIDUtils.getUUID();
            opsForValue.set(userConfigure.getAuthCodePrefix() + requestUrl, authCode, userConfigure.getAuthCodeTimeout(), TimeUnit.MINUTES);
        }
        return "redirect:" + callbackUrl + "?authCode=" + authCode;
    }


    /**
     * 获取令牌
     */
    public ResData<Map> getToken( String authCode ) {

        String requestUrl = request.getRequestURL().toString();
        ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
        if ( authCode == null || !authCode.equals(ops.get(userConfigure.getAuthCodePrefix() + requestUrl)) ) {
            return ResData.ofFail(500, "authCode 失效", null);
        }
        Map<String, Object> ret = new HashMap<>();
        String token = UUIDUtils.getUUID();
        ops.set(userConfigure.getTokenPrefix() + requestUrl, token, userConfigure.getTokenTimeout(), TimeUnit.HOURS);
        ret.put("token", token);
        SysUser loginUser = ( SysUser ) session.getAttribute("loginUser");
        ret.put("openId", loginUser.getId());
        return ResData.ofSuccess(ret);
    }


    /**
     * 检查令牌是否有效
     */
    @RequestMapping( "/checkToken" )
    @ResponseBody
    public boolean checkToken( String token ) {
        /*
         *  && redisTemplate.<String>hasKey(token)
         *  && setOperations.isMember(userConfigure.getTokenPrefix() + token, requestURL);
         */
        String requestUrl = request.getRequestURL().toString();
        ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
        return token != null && token.equals(ops.get(userConfigure.getTokenPrefix() + requestUrl));
    }


    /**
     * 退出登陆
     */
    @RequestMapping( "/logout" )
    public boolean logout() throws InterruptedException {
        List<String> servers = ( List<String> ) session.getAttribute(userConfigure.getTokenServersName());
        if ( servers != null ) {
            CountDownLatch countDownLatch = new CountDownLatch(servers.size());
            for ( String server : servers ) {
                new Thread(() -> {
                    CloseableHttpClient client = HttpClients.createMinimal();
                    HttpPost httpPost = new HttpPost(server + "/logout");
                    try {
                        boolean reTry = true;
                        int maxReTry = 3;
                        // 失败重试
                        for ( int i = 0; reTry && i < maxReTry; i++ ) {
                            CloseableHttpResponse execute = client.execute(httpPost);
                            HttpEntity entity = execute.getEntity();
                            String json = EntityUtils.toString(entity);
                            JSONObject jsonObject = JSON.parseObject(json);
                            Boolean success = jsonObject.getBoolean("success");
                            reTry = success == null || !success;
                        }
                    } catch ( IOException e ) {
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
    @RequestMapping( "/getUserInfo" )
    @ResponseBody
    public ResData<SysUser> getUserInfo( String token ) {
        boolean b = checkToken(token);
        if ( b ) {
            SysUser loginUser = ( SysUser ) session.getAttribute("loginUser");
            return ResData.ofSuccess(loginUser);
        }
        return ResData.ofFail(500, "token not effective!");
    }
}
