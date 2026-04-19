package com.ruoyi.framework.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端路由转发
 */
@Controller
public class RouteForwardConfig {

    /**
     * 转发 station 所有路由到 index.html
     */
    @RequestMapping("/station/**")
    public String forwardStation() {
        return "forward:/index.html";
    }
}