package com.my.train.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.my.train.util.ValidationGroup;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author Wtq
 * @date 2019/10/22 - 17:23
 */
// 不能用@Data, 因为@Data会覆写hashcode方法, hashcode方法里会对关联实体的属性也做hashcode,
// 对于这种实体在做lambda的map时会调用hashcode方法, 造成主动加载关联属性实体
@MappedSuperclass
//指定json的序列化顺序
@JsonPropertyOrder({"id", "code", "description", "salary"})
public abstract class AbstractMainModel implements Serializable {

    private static final long serialVersionUID = -7122439246051524423L;

    @Id
    // 因为现在校验是在Controller层做的,
    // 所以可以使用@Null在新增时不让前端传入id
    // 在Service层还是可以手工set id
    @Null(groups = ValidationGroup.Create.class)
    //在更新或与其他表关联的时候id不能为空
    @NotBlank(groups = {ValidationGroup.Update.class, ValidationGroup.Association.class})
    private String id;


    @NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    private String code;    //编码

    @NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    private String description;    //描述

    @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    private Double salary;    //薪资

    //将我们查询所需的条件封装在Criteria内部类中
    public static class Criteria implements Serializable {
        private static final long serialVersionUID = 737663162167601627L;
        private String code;
        private String description;
        private Double salary;
        private boolean usingLikeQuery = true;

        public static long getSerialVersionUID() {
            return serialVersionUID;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double salary) {
            this.salary = salary;
        }

        public boolean isUsingLikeQuery() {
            return usingLikeQuery;
        }

        public void setUsingLikeQuery(boolean usingLikeQuery) {
            this.usingLikeQuery = usingLikeQuery;
        }
    }

    public AbstractMainModel() {
    }

    public AbstractMainModel(@NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) String code, @NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) String description, @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}) Double salary) {
        this.code = code;
        this.description = description;
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "AbstractMainModel{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", salary=" + salary +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
