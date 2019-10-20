package com.tj.product.controller;

import com.tj.product.HappysysApplicantInfoModel;
import com.tj.product.HappysysOrder;
import com.tj.product.HappysysOrderDetails;
import com.tj.service.HappysysProductClientService;
import com.tj.service.HappysysUserClientService;
import com.tj.user.HappysysUser;
import com.tj.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @program: HappySys
 * @Date: 2019/8/1 11:01
 * @Author: Mr.Deng
 * @Description:
 */
@Controller
public class PaymentController {

 @Autowired
 private HappysysProductClientService happysysProductClientService;
 @Autowired
 private HappysysUserClientService happysysUserClientService;

 @RequestMapping(value = "/HappysysPayment/payment1",method = RequestMethod.POST)
 public String paymentOne(@RequestParam Map<String,Object> map, @RequestParam("insuranceList") List<Integer> insuranceList, Model model, HttpSession session){

  model.addAttribute("map",map);
  model.addAttribute("insuranceList",insuranceList);

  HappysysUser user = (HappysysUser)session.getAttribute("user");
  System.out.println("用户："+user);

  if(user==null){
   return "redirect:/login.html";
  }else{
   if(user.getUserApplicantInfoId()!=null&&user.getUserApplicantInfoId()!=0){
    model.addAttribute("applicant",happysysProductClientService.getApplicantByApplicatnId(user.getUserApplicantInfoId()));
    model.addAttribute("recognizee",happysysProductClientService.getApplicantByUserId(user.getUserId()));
   }else{
    model.addAttribute("applicant",null);
    model.addAttribute("recognizee",null);
   }
  }
  return "product_payment1";
 }



 @RequestMapping(value = "/HappysysPayment/payment2",method = RequestMethod.POST)
 public String paymentTwo(HappysysOrder order, @ModelAttribute("insuranceList") List<Integer> insuranceList,HappysysApplicantInfoModel list,HttpSession session) {

  HappysysUser happysysUser = (HappysysUser)session.getAttribute("user");
  System.out.println("用户："+happysysUser);

  //生成订单号
  order.setOrderNumber(UUIDUtil.getOrderIdByTime());

  //被保人操作
  list.getList().forEach(i->{
   i.setApplicantUserId(happysysUser.getUserId());
  });
  Integer[] ids = happysysProductClientService.operationApplicationInfo(list);
  order.setOrderApplicantId(ids[0]);
  order.setOrderRecognizeeId(ids[1]);

  //订单详情
  Integer orderId = happysysProductClientService.insertOrder(order);
  Collection<HappysysOrderDetails> orderDetailsList = new ArrayList<>();
  for (Integer insuranceId : insuranceList) {
   HappysysOrderDetails happysysOrderDetails = new HappysysOrderDetails();
   happysysOrderDetails.setOrderDetailsOrderId(orderId);
   happysysOrderDetails.setOrderDetailsInsuranceId(insuranceId);
   orderDetailsList.add(happysysOrderDetails);
  }
  happysysProductClientService.insertOrderDetails(orderDetailsList);

  //订单ID
  order.setOrderId(orderId);

  //修改用户表被保人
  if(happysysUser!=null){
   happysysUser.setUserApplicantInfoId(ids[0]);
   happysysUserClientService.updateUserById(happysysUser);
  }

  System.out.print("====Order:"+order);
  session.setAttribute("order",order);

  return "forward:/HappysysOrder/loadProductPayment2/"+orderId;
 }

 @RequestMapping(value = "/HappysysPayment/payment3")
 public String paymentTwo(HttpSession session) {

  return "product_payment3";
 }



}
