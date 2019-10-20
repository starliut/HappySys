package com.tj.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tj.product.HappysysComment;
import com.tj.product.HappysysCommonProblem;
import com.tj.service.HappysysProductClientService;
import com.tj.user.HappysysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private HappysysProductClientService happysysProductClientService;


    @RequestMapping(value = "/HappysysComment/addComment",consumes ="application/json")
    @ResponseBody
    public boolean addComment(@RequestBody HappysysComment comment, HttpSession session){
        System.out.println("CommentController      addComment");
        Integer userId = ((HappysysUser)session.getAttribute("user")).getUserId();
        comment.setCommentUserId(userId);

        return happysysProductClientService.addComment(comment);
    }



    @RequestMapping("/HappysysComment/getCommentAndUserByProductId/{productId}")
    @ResponseBody
    public Map<String,Object> getCommentAndUserByProductId(@PathVariable("productId") Integer productId){
        System.out.println("CommentController      getCommentAndUserByProductId  responseBody");

        Page<Map<String,Object>> commentAndUserPage = happysysProductClientService.getCommentAndUserByProductId(productId,1,99999);
        Integer commentSatisfaction = 100;

        //所有的评论所评价的user
        List<Map<String,Object>> commentAndUser = commentAndUserPage.getRecords();

        //循环计算评价满意度
        if(commentAndUser != null && commentAndUser.size() > 0){
            Integer totalGrade = 0;
            for(Map<String,Object>  map: commentAndUser){
                totalGrade += (Integer)map.get("comment_grade");
            }
            //数量÷总数×100=百分比
            Double result = (totalGrade / (commentAndUser.size() * 5.0) * 100);
            commentSatisfaction = result.intValue();
        }

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("commentAndUser",commentAndUser);
        resultMap.put("commentSatisfaction",commentSatisfaction);

        return resultMap;
    }


}
