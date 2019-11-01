package com.my.train.service;

import com.my.train.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wtq
 * @date 2019/10/22 - 16:38
 */
@RunWith(SpringRunner.class)
@Transactional(transactionManager = "transactionManager")
@Rollback(value = false) //事务不回滚
@SpringBootTest
public class MyTest {

    @Autowired
    private TeacherService teacherService;

    @Test
    public void test01(){
        Teacher teacher = new Teacher();
        teacher.setCode("tc");
        teacher.setSubject("语文");
        teacher.setDescription("教授课程");
        teacher.setSalary(5000.0);
        teacherService.save(teacher);
    }
}
