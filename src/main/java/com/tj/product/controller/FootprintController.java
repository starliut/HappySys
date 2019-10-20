package com.tj.product.controller;

import com.tj.product.HappysysFootprint;
import com.tj.product.HappysysProduct;
import com.tj.service.HappysysProductClientService;
import com.tj.user.HappysysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FootprintController {

    @Autowired
    private HappysysProductClientService happysysProductClientService;


    @RequestMapping("/HappysysFootprint/getFootprintProductByUserId")
    @ResponseBody
    public List<HappysysProduct> getFootprintProductByUserId(HttpSession session){
        System.out.println("FootprintController      getFootprintProductByUserId");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();

        return happysysProductClientService.getFootprintProductByUserId(userId);
    }


}
