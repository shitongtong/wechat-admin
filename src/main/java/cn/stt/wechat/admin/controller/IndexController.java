package cn.stt.wechat.admin.controller;

import cn.stt.wechat.admin.po.TextMeaasge;
import cn.stt.wechat.admin.util.CheckUtil;
import cn.stt.wechat.admin.util.MessageUtil;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/8/14.
 */
@RestController
@ResponseBody
@RequestMapping("/wechat")
public class IndexController {

    private String token = "stt";
    private String encodingAesKey = "uyYoch8ecoGUq2WO4VZcHvCKuzYnL75OhPoZUPVeMME";
    private String appId = "wxc2db313cf85c21bf";

    /**
     * 验证服务器地址的有效性
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/index")
    public void verifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();

        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);        // 校验通过，原样返回echostr参数内容
        }

        /*WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token,encodingAesKey,appId);
        String result = wxBizMsgCrypt.verifyUrl(signature, timestamp, nonce, echostr);  //解密之后的echostr
        out.print(result);// 校验通过，返回echostr参数内容*/
    }

    /**
     * 接收并处理微信客户端发送的请求
     * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
     *
     * @param request
     * @param response
     */
    @PostMapping("/index")
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if ("text".equals(msgType)) {                // 对文本消息进行处理
                TextMeaasge text = new TextMeaasge();
                text.setFromUserName(toUserName);         // 发送和回复是反向的
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(new Date().getTime());
                text.setContent("你发送的消息是：" + content);
                message = MessageUtil.textMessageToXML(text);
                System.out.println(message);
            }
            out.print(message);                            // 将回应发送给微信服务器
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
