package com.tj.product.controller;

import com.tj.product.HappysysCommonProblem;
import com.tj.service.HappysysProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CommonProblemController {

    @Autowired
    private HappysysProductClientService happysysProductClientService;

    @RequestMapping("/HappysysCommonProblem/getCommonProblemByProductId/{productId}")
    @ResponseBody
    public List<HappysysCommonProblem> getCommonProblemByProductId(@PathVariable("productId") Integer productId){
        return happysysProductClientService.getCommonProblemByProductId(productId);
    }


}
