package com.tj.product.controller;

import com.tj.product.HappysysProduct;
import com.tj.product.HappysysShoppingCart;
import com.tj.service.HappysysProductClientService;
import com.tj.user.HappysysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShoppingCartController {

    @Autowired
    private HappysysProductClientService happysysProductClientService;

    @RequestMapping("/HappysysShoppingCart/delShoppingCartByShoppingCartId/{shoppingCartId}")
    @ResponseBody
    public boolean delShoppingCartByShoppingCartId(@PathVariable("shoppingCartId")Integer shoppingCartId){
        System.out.println("ShoppingCartController      delShoppingCartByShoppingCartId");

        return happysysProductClientService.delShoppingCartByShoppingCartId(shoppingCartId);
    }

    @RequestMapping("/HappysysShoppingCart/getShoppingCartByProductIdAndUserId/{productId}")
    @ResponseBody
    public HappysysShoppingCart getShoppingCartByProductIdAndUserId(@PathVariable("productId")Integer productId, HttpSession session){
        System.out.println("ShoppingCartController      getShoppingCartByProductIdAndUserId");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();
        return happysysProductClientService.getShoppingCartByProductIdAndUserId(productId,userId);
    }

    @RequestMapping("/HappysysShoppingCart/addShoppingCart")
    @ResponseBody
    public boolean addShoppingCart(Integer productId, HttpSession session){
        System.out.println("ShoppingCartController      addShoppingCart");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();

        HappysysShoppingCart shoppingCart = new HappysysShoppingCart();
        shoppingCart.setShoppingCartProductId(productId);
        shoppingCart.setShoppingCartUserId(userId);
        return happysysProductClientService.addShoppingCart(shoppingCart);
    }

    @RequestMapping(value = "/HappysysShoppingCart/delShoppingCartByProductIdAndUserId/{productId}")
    @ResponseBody
    public boolean delShoppingCartByProductIdAndUserId(@PathVariable("productId") Integer productId, HttpSession session){
        System.out.println("ShoppingCartController      delShoppingCartByProductIdAndUserId");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();

        return happysysProductClientService.delShoppingCartByProductIdAndUserId(productId,userId);
    }


    @RequestMapping(value = "/HappysysShoppingCart/getCurrentUserShoppingCartProduct")
    @ResponseBody
    public List<HappysysProduct> getCurrentUserShoppingCartProduct(HttpSession session){
        System.out.println("ShoppingCartController      loadShoppingCart：");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();

        Map<String,Object> condtions = new HashMap<String,Object>();
        condtions.put("shopping_cart_user_id",userId);
        condtions.put("size",999);

        return happysysProductClientService.getProductByMap(condtions).getRecords();
    }




}
