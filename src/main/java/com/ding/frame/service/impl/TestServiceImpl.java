package com.ding.frame.service.impl;

import com.ding.frame.annotation.Service;
import com.ding.frame.service.TestService;

/**
 * @author dingzhongfa
 * @email : 71964899@qq.com
 * @date 2019-07-01 下午9:45
 */
@Service("testService")
public class TestServiceImpl implements TestService {


    public void testMvc(String id) {
        System.out.println("id " + id + "进入service");
    }
}

