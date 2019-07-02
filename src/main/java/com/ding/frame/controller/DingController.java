package com.ding.frame.controller;

import com.ding.frame.annotation.DingAutowired;
import com.ding.frame.annotation.DingRequestMapping;
import com.ding.frame.annotation.DingRestController;
import com.ding.frame.annotation.RequestParam;
import com.ding.frame.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dingzhongfa
 * @email : 71964899@qq.com
 * @date 2019-07-01 下午9:34
 */
@DingRestController
@DingRequestMapping("ding/test")
public class DingController {

    @DingAutowired("testService")
    private TestService testService;

    @DingRequestMapping
    public void testMvc(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                        @RequestParam("id") String id) {
        testService.testMvc("id");

    }
}
