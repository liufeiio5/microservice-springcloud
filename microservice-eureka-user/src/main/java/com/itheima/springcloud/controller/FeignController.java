package com.itheima.springcloud.controller;

import com.itheima.springcloud.api.FeignApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("feign")
public class FeignController {
    @Autowired
    private FeignApi feignApi;

    @RequestMapping("/test")
    @ResponseBody
    public String testFeign(String id){
        return feignApi.findOrderById(id);
    }
}
