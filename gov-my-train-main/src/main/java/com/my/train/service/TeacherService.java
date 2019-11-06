package com.my.train.service;

import com.my.train.model.Teacher;
import com.my.train.repository.MyJpaRepository;
import com.my.train.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wtq
        * @date 2019/11/5 - 11:02
        */
@Service
public class TeacherService extends AbstractMainModelService<Teacher>{

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    protected MyJpaRepository getRepo() {
        //为父类赋值具体的repository
        return teacherRepository;
    }

    @Override
    public Teacher create(Teacher model, boolean publishEvent) {
        return super.create(model, publishEvent);
    }

    public int countById(String id){
        return 0;
    }
}
