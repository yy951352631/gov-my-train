package com.my.train.model;

import com.my.train.CommonConstants;
import com.my.train.util.ValidationGroups;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author Wtq
 * @date 2019/10/23 - 13:53
 */
@Entity
@Table(name = CommonConstants.TABLE_NAME_PREFIX + "DRIVER")
public class Driver extends AbstractMainModel {

    @Column(name = "driveType")
    private String driveType;//驾驶的车型


    public Driver(@Null(groups = ValidationGroups.Create.class) @NotBlank(groups = {ValidationGroups.Update.class, ValidationGroups.Association.class}) String id, @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String code, @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String description, Date createdDate, Date lastModifiedDate, String driveType) {
        super(id, code, description, createdDate, lastModifiedDate);
        this.driveType = driveType;
    }

    public Driver() {
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driveType='" + driveType + '\'' +
                '}';
    }
}
