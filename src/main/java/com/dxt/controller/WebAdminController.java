package com.dxt.controller;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;
import com.dxt.service.CacheManager;
import com.dxt.service.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
@RequestMapping(value = "/", name = "webAdminController")
public class WebAdminController {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SessionManager sessionManager;

    private static final Logger logger = LoggerFactory.getLogger(WebAdminController.class);

    @RequestMapping(value = "/admin")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }
        return "admin/index.html";
    }



}
