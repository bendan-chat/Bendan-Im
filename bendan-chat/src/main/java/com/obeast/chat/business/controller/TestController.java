package com.obeast.chat.business.controller;

import com.obeast.chat.business.service.feign.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2023/1/9 19:24
 * @version 1.0
 * Description:
 */
@RestController
@RequestMapping("/t")
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/u")
    public void  test () {
        System.out.println(testService.listAll());
    }
}
