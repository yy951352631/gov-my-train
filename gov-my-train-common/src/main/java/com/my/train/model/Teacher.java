package com.my.train.model;

import com.my.train.CommonConstants;
import com.my.train.util.ValidationGroup;
import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wtq
 * @date 2019/10/22 - 16:36
 */
@ApiModel("教师实体类")
@Entity
@Table(name = CommonConstants.TABLE_NAME_PREFIX + "TEACHER")
public class Teacher extends AbstractMainModel {

    @Column(name = "subject")
    private String subject; //教授课程


    public Teacher(String subject) {
        this.subject = subject;
    }

    public Teacher(@NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) String code, @NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) String description, @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) Double salary, String subject) {
        super(code, description, salary);
        this.subject = subject;
    }

    public Teacher() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "subject='" + subject + '\'' +
                '}';
    }


}
