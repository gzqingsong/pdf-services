package com.whj.controller;

import com.whj.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date 2022/1/12 13:57
 * @Description: 实现签字上传和签字功能
 */
@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private SignService signService;

    @PostMapping(value = "/uploadSign")
    @ResponseBody
    public String uploadSign(String img) {
        return signService.uploadSign(img);
    }


    @PostMapping(value = "/sign")
    @ResponseBody
    public String sign() {
        return signService.sign();
    }
}
