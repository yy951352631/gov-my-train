package com.my.train.service;

import com.my.train.model.Teacher;
import com.my.train.repository.TeacherRepository;
import com.my.train.util.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wtq
 * @date 2019/10/23 - 14:06
 */
@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    public Teacher save(Teacher teacher) {
        if(StringUtils.isBlank(teacher.getId())){
            teacher.setId(IdUtil.generateId());
        }
        return teacherRepository.save(teacher);
    }
}
