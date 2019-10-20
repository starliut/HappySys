package com.tj.product.controller;

import com.tj.product.HappysysApplicantInfo;
import com.tj.service.HappysysProductClientService;
import com.tj.user.HappysysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ApplicantInfoController {

    @Autowired
    private HappysysProductClientService happysysProductClientService;

    @RequestMapping("/HappysysApplicantInfo/getCurrentUserApplicantAndInsured")
    @ResponseBody
    public List<HappysysApplicantInfo> getCurrentUserApplicantAndInsured(HttpSession session){
        System.out.println("ApplicantInfoController      getCurrentUserApplicantAndInsured");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();

        //查询被保人
        return happysysProductClientService.getApplicantByUserId(userId);
    }

    @RequestMapping("/HappysysApplicantInfo/delApplicantById")
    @ResponseBody
    public Boolean delApplicantById(Integer applicantId){
        System.out.println("ApplicantInfoController      delApplicantById");
        try {
            return happysysProductClientService.delApplicantById(applicantId);
        }catch (Throwable throwable){       //如果删除出错证明此此Applicant被外键引用
            return false;
        }
    }

}
