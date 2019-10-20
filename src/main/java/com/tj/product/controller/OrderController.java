package com.tj.product.controller;

import com.tj.product.HappysysApplicantInfo;
import com.tj.product.HappysysInsurance;
import com.tj.service.HappysysProductClientService;
import com.tj.user.HappysysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private HappysysProductClientService happysysProductClientService;


    @RequestMapping("/HappysysOrder/getAllOrderStatusNumByUserId")
    @ResponseBody
    public Map<String,Object> getAllOrderStatusNumByUserId(HttpSession session){
        System.out.println("OrderController      getAllOrderStatusNumByUserId");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();

        return happysysProductClientService.getAllOrderStatusNumByUserId(userId);
    }

    @RequestMapping("/HappysysOrder/getOrderByMap")
    @ResponseBody
    public List<Map<String,Object>> getOrderByMap(@RequestParam Map<String, Object> conditions, HttpSession session){
        System.out.println("OrderController      getOrderByUserId");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();

        conditions.put("user_id",userId);
        System.out.println("getOrderByUserId:"+conditions);
        List<Map<String,Object>> orderList = happysysProductClientService.getOrderByMap(conditions);

        return orderList;
    }


    @RequestMapping("/HappysysOrder/loadProductPayment2/{orderId}")
    public ModelAndView loadProductPayment2(@PathVariable("orderId") Integer orderId){
        ModelAndView mav = getOrderDetailsInfo(orderId);
        mav.setViewName("product_payment2");
        return mav;
    }

    @RequestMapping("/HappysysOrder/loadOrderDetailsByOrderId/{orderId}")
    public ModelAndView loadOrderDetailsByOrderId(@PathVariable("orderId") Integer orderId){
        ModelAndView mav = getOrderDetailsInfo(orderId);
        mav.setViewName("order_details");
        return mav;
    }


    public ModelAndView getOrderDetailsInfo(Integer orderId){
        ModelAndView mav = new ModelAndView();
        //获取订单
        Map<String,Object> orderDetails = happysysProductClientService.getOrderByOrderId(orderId);

        Integer applicantId = Integer.parseInt(orderDetails.get("order_applicant_id")+"");
        Integer recognizeeId = Integer.parseInt(orderDetails.get("order_recognizee_id")+"");

        //获取当前投保人和被保人
        List<HappysysApplicantInfo> applicantAndrecognizeeList =  happysysProductClientService.getApplicantByIds(Arrays.asList(applicantId,recognizeeId));

        HappysysApplicantInfo applicantInfo = new HappysysApplicantInfo();
        HappysysApplicantInfo recognizeeInfo = new HappysysApplicantInfo();

        if(applicantAndrecognizeeList != null){
            if(applicantAndrecognizeeList.size() == 1){//如果size等于1那就代表投保人和被保人是同一个人
                applicantInfo = applicantAndrecognizeeList.get(0);
                recognizeeInfo = applicantAndrecognizeeList.get(0);
            }else {
                for (HappysysApplicantInfo applicantAndrecognizee : applicantAndrecognizeeList) {
                    if(applicantId.equals(applicantAndrecognizee.getApplicantId()) ) {
                        applicantInfo = applicantAndrecognizee;
                    }else {
                        recognizeeInfo = applicantAndrecognizee;
                    }
                }
            }
        }
        //根据 orderid获取所有的投保项
        List<HappysysInsurance> insuranceList =  happysysProductClientService.getInsuranceByOrderId(orderId);

        mav.addObject("insuranceList",insuranceList);
        mav.addObject("orderDetails",orderDetails);
        mav.addObject("applicantInfo",applicantInfo);
        mav.addObject("recognizeeInfo",recognizeeInfo);
        return mav;
    }

    @RequestMapping("/HappysysOrder/getOrderCountByProductId/{productId}")
    @ResponseBody
    public Integer getOrderCountByProductId(@PathVariable("productId") Integer productId){
        System.out.println("OrderController      getOrderCountByProductId");

        return happysysProductClientService.getOrderCountByProductId(productId);
    }




}
