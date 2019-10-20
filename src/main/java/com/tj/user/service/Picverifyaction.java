package com.tj.user.service;


import com.tj.user.util.RandomValidateCodeUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/login"})
public class Picverifyaction {

    private static final Logger logger = LoggerFactory.getLogger(Picverifyaction.class);

    public Picverifyaction() {
    }

    @RequestMapping({"/getVerify"})
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0L);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);
        } catch (Exception var4) {
            logger.error("获取验证码失败>>>>   ", var4);
        }

    }

    @RequestMapping(
            value = {"/checkVerify"},
            method = {RequestMethod.POST},
            headers = {"Accept=application/json"}
    )
    public boolean checkVerify(@RequestParam String verifyInput, HttpSession session) {
        try {
            String random = (String)session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            } else {
                return random.equals(verifyInput);
            }
        } catch (Exception var5) {
            logger.error("验证码校验失败", var5);
            return false;
        }
    }
}
