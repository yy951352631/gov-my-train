package com.my.train.repository;

import com.my.train.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Wtq
 * @date 2019/10/23 - 13:55
 */
public interface TeacherRepository extends MyJpaRepository<Teacher,String> {
}
