package com.my.train.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wtq
 * @date 2019/10/23 - 14:30
 */
@Api(value = "控制层")
@RestController
@RequestMapping(value = "/test")
public class TestConteoller {


    @GetMapping(value = "/01")
    public String test01(){
//        Teacher teacher = new Teacher();
//        teacher.setCode("tc");
//        teacher.setId("10086");
//        teacher.setSubject("语文");
//        teacher.setDescription("教授课程");
//        teacher.setSalary(5000.0);
//        return teacherService.save(teacher).toString();
        return "";
    }
}
