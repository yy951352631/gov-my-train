package com.my.train.model;

import com.my.train.CommonConstants;
import com.my.train.util.ValidationGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wtq
 * @date 2019/10/23 - 13:53
 */
//@Entity
//@Table(name = CommonConstants.TABLE_NAME_PREFIX + "DRIVER")
public class Driver extends AbstractMainModel {

    @Column(name = "driveType")
    private String driveType;

    public String getDriveType() {
        return driveType;
    }

    public Driver() {
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driveType='" + driveType + '\'' +
                '}';
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public Driver(String driveType) {
        this.driveType = driveType;
    }

    public Driver(@NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) String code, @NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) String description, @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) Double salary, String driveType) {
        super(code, description, salary);
        this.driveType = driveType;
    }
}
