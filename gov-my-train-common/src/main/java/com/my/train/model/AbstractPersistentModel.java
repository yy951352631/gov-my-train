package com.my.train.model;

import com.my.train.util.ValidationGroups;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author Wtq
 * @date 2019/11/1 - 15:12
 */

/**
 * 是所有需要存储到数据库中的实体类的抽象
 */
@MappedSuperclass
public abstract class AbstractPersistentModel implements Serializable {

    private static final long serialVersionUID = 3846050117316030756L;
    @Id
    // 因为现在校验是在Controller层做的,
    // 所以可以使用@Null在新增时不让前端传入id
    // 在Service层还是可以手工set id
    @Null(groups = ValidationGroups.Create.class)
    //在更新或与其他表关联的时候id不能为空
    @NotBlank(groups = {ValidationGroups.Update.class, ValidationGroups.Association.class})
    private String id;

    public AbstractPersistentModel(@Null(groups = ValidationGroups.Create.class) @NotBlank(groups = {ValidationGroups.Update.class, ValidationGroups.Association.class}) String id) {
        this.id = id;
    }

    public AbstractPersistentModel clone() {
        try {
            return (AbstractPersistentModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw null;
            //MODEL_CLONE_NOT_SUPPORTED.runtimeException(getClass().getName());
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AbstractPersistentModel() {
    }
}
