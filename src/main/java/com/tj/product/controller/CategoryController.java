package com.tj.product.controller;

import com.tj.service.HappysysProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: happysys
 * @Date: 2019/7/11 11:04
 * @Author: Mr.Deng
 * @Description:
 */
@Controller
public class CategoryController {

 @Autowired
 private HappysysProductClientService happysysProductClientService;

 @RequestMapping("category/list")
 public String category(Model model){

  model.addAttribute("categoryList",this.happysysProductClientService.getCategoryAll());

  System.out.println("成功进入首页：Index");
  return "index";
 }


/* @RequestMapping("category/list")
 @ResponseBody
 public List<HappysysCategory> category(){
  return happysysProductClientService.list();
 }*/

}
