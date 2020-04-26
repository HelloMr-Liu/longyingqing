package com.longyingqing.jianzhu.controller;

import com.longyingqing.jianzhu.entity.StudentMgn;
import com.longyingqing.jianzhu.mapper.StudentMgnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ================================================================
 * 说明：当前类说说明
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/26  18:25            创建
 * =================================================================
 **/

@Controller
public class Test {

    @Autowired
    private StudentMgnMapper studentMgnMapper;

    @RequestMapping("/getStudent")
    @ResponseBody
    public StudentMgn getStudent(){
        return studentMgnMapper.selectByPrimaryKey("201500000012");
    }
}
