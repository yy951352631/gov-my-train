package com.my.train.model;

import com.my.train.CommonConstants;
import com.my.train.util.ValidationGroups;
import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;

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

    public Teacher(@Null(groups = ValidationGroups.Create.class) @NotBlank(groups = {ValidationGroups.Update.class, ValidationGroups.Association.class}) String id, @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String code, @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String description, Date createdDate, Date lastModifiedDate, String subject) {
        super(id, code, description, createdDate, lastModifiedDate);
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
