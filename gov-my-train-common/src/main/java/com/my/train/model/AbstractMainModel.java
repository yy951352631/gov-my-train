package com.my.train.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.my.train.util.ValidationGroups;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wtq
 * @date 2019/10/22 - 17:23
 * description : 这里类用来抽取部分实体的公共属性
 */
// 不能用@Data, 因为@Data会覆写hashcode方法, hashcode方法里会对关联实体的属性也做hashcode,
// 对于这种实体在做lambda的map时会调用hashcode方法, 造成主动加载关联属性实体
@MappedSuperclass
//指定json的序列化顺序
@JsonPropertyOrder({"id", "code", "description", "createdDate", "lastModifiedDate"})
public abstract class AbstractMainModel extends AbstractPersistentModel implements Serializable {

    private static final long serialVersionUID = -35694461622941211L;

    @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String code;    //编码

    @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String description;    //描述

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @org.hibernate.annotations.CreationTimestamp //save时自动保存时间戳
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;   //创建时间


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @org.hibernate.annotations.UpdateTimestamp //update时自动保存时间戳
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate; //最后更新时间

    //将我们查询所需的条件封装在Criteria内部类中
    public static class Criteria implements Serializable {
        private static final long serialVersionUID = 737663162167601627L;
        private String code;
        private String description;
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

        public boolean isUsingLikeQuery() {
            return usingLikeQuery;
        }

        public void setUsingLikeQuery(boolean usingLikeQuery) {
            this.usingLikeQuery = usingLikeQuery;
        }
    }


    public AbstractMainModel(@Null(groups = ValidationGroups.Create.class)
                             @NotBlank(groups = {ValidationGroups.Update.class, ValidationGroups.Association.class}) String id,
                             @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String code,
                             @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String description,
                             Date createdDate, Date lastModifiedDate) {
        super(id);
        this.code = code;
        this.description = description;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public AbstractMainModel(@NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String code,
                             @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}) String description,
                             Date createdDate, Date lastModifiedDate) {
        this.code = code;
        this.description = description;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public AbstractMainModel() {
    }

    @Override
    public String toString() {
        return "AbstractMainModel{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
