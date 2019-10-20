package com.tj.product.controller;

import com.tj.product.HappysysFootprint;
import com.tj.service.HappysysProductClientService;
import com.tj.user.HappysysUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private HappysysProductClientService happysysProductClientService;

    @RequestMapping("/HappysysProduct/getById/{productId}")
    public String getById(@PathVariable("productId") Integer productId, Model model, HttpSession session){
        HappysysUser user = ((HappysysUser)session.getAttribute("user"));

        if(user != null){//如果用户已登陆，生产一个消息发送到队列等待消费（插入数据库到我的足迹）
            HappysysFootprint footPrint = new HappysysFootprint();
            footPrint.setFootprintUserId(user.getUserId());
            footPrint.setFootprintProductId(productId);
            rabbitTemplate.convertAndSend("direct.happy.insert_foot_print", "queue.insert_foot_print", footPrint);
        }

        model.addAttribute("product",this.happysysProductClientService.getProductById(productId));
        return "product_details";
    }

    @RequestMapping(value = "/HappysysProduct/getCaluelatePrice",method = RequestMethod.POST)
    @ResponseBody
    public Double caluelatePrice(@RequestBody Map<String,Object> map){
        return this.happysysProductClientService.calculatePrice(map);
    }





}
