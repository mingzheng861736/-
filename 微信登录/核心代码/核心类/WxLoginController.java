package cn.itrip.auth.controller;

import cn.itrip.common.UrlUtils;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by shang-pc on 2017/8/25.
 */
@RequestMapping("/wx/login")
@Controller
public class WxLoginController {

    Logger logger = Logger.getLogger(WxLoginController.class);

    @RequestMapping("/callBack")
    public void callBack(String code, String state, HttpServletResponse response) throws IOException {
        String accessUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9168f76f000a0d4c&secret=8ba69d5639242c3bd3a69dffe84336c1&code=" + code + "&grant_type=authorization_code";
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo";
        //请求地址获取 access_token
        String jsonStr = UrlUtils.loadURL(accessUrl);
        Map<String, String> accessMap = JSON.parseObject(jsonStr, Map.class);
        String accessToken = accessMap.get("access_token");
        String openId = accessMap.get("openid");
        logger.info("accessToken:" + accessToken + ";openid:" + openId);
        //获取用户信息
        userInfoUrl = userInfoUrl + "?access_token=" + accessToken + "&openid=" + openId;
        String userInfoStr = UrlUtils.loadURL(userInfoUrl);
        Map<String, String> userInfoMap = JSON.parseObject(userInfoStr, Map.class);
        String city=userInfoMap.get("city");
        String nickname=userInfoMap.get("nickname");
        logger.info("city:" + city + ";nickname:" + nickname);
        //TODO 自身网站的业务逻辑
        response.sendRedirect("http://www.baidu.com");
    }
}
