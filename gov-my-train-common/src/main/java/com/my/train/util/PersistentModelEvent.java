/**
 * 
 */
package com.my.train.util;

import com.my.train.model.AbstractPersistentModel;
import org.springframework.context.ApplicationEvent;

import java.lang.reflect.Array;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */
public class PersistentModelEvent extends ApplicationEvent {
    private static final long serialVersionUID = 5542443911974434071L;

    private Type type;
    private Class<?> modelClass;

    public static enum Type {
        PRE_CREATE, POST_CREATE, PRE_UPDATE, POST_UPDATE, PRE_DELETE, POST_DELETE;
    }

    public PersistentModelEvent(Type type, AbstractPersistentModel... models/*对于POST_UPDATE, 会有2个Model*/) {
        // TODO 是否要做一个长度校验??
        super(Array.newInstance(models[0].getClass(), models.length));
        System.arraycopy(models, 0, getSource(), 0, models.length);
        this.type = type;
        this.modelClass = models[0].getClass();
    }

    public AbstractPersistentModel getModel() {
        return (AbstractPersistentModel) getModels()[0];
    }

    public AbstractPersistentModel[] getModels() {
        return (AbstractPersistentModel[]) getSource();
    }

    public Type getType() {
        return type;
    }

    public Class<?> getModelClass() {
        return modelClass;
    }

}
