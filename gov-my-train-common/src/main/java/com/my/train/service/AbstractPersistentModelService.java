package com.my.train.service;

import com.my.train.exception.MyErrorCode;
import com.my.train.model.AbstractPersistentModel;
import com.my.train.repository.MyJpaRepository;
import com.my.train.util.PersistentModelEvent;
import com.my.train.util.PersistentModelUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Wtq
 * @date 2019/11/1 - 17:59
 */

/**
 * 这个类用于实现在自定义repository中自定义的方法
 * @param <T>
 */
public abstract class AbstractPersistentModelService<T extends AbstractPersistentModel> implements ApplicationEventPublisherAware {

    //具体的实现方法由子类根据自身需要返回具体的Repository  （抽象层的统一写法）
    protected abstract MyJpaRepository<T, String> getRepo();
    //我们的事件发布者
    private ApplicationEventPublisher publisher;

    //继承了ApplicationEventPublisherAware后，此方法会自动注入事件发布者
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }


    @Transactional(rollbackOn = Throwable.class)
    public T create(T model) {
        return create(model, true);
    }

    /**
     *
     * @param model  ： 操作对象
     * @param publishEvent ： 是否对事件进行发布
     * @return ： 实例化对象
     */
    @Transactional(rollbackOn = Throwable.class)
    public T create(T model, boolean publishEvent) {
        if (publishEvent) {
            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.PRE_CREATE, model));
        }
        model = getRepo().save(model);
        if (publishEvent) {
            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.POST_CREATE, model));
        }
        return model;
    }

    public void publishEvent(PersistentModelEvent event) {
        publisher.publishEvent(event);
    }


    @Transactional(rollbackOn = Throwable.class)
    public T update(T model) {
        return update(model, true);
    }

    @Transactional(rollbackOn = Throwable.class)
    public T update(T model, boolean publishEvent) {
        return update(model, true, publishEvent);
    }

    @Transactional(rollbackOn = Throwable.class)
    public T update(T model, boolean dynamicUpdate, boolean publishEvent) {
        T existedModel = null;
        if (publishEvent) {
            // 不clone的话, 经过save, existedModel的值会变成新值  （这里是为了保留日志，需要保留更新前后的model，所以进行clone操作）
            existedModel = (T) findById(model.getId()).clone();
            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.PRE_UPDATE, existedModel, model));
        }
        if (dynamicUpdate) {
            T modelToSave = findById(model.getId());/* 会直接使用Hibernate Session缓存的model, 不会再查数据库 */
            PersistentModelUtil.copyNotNullProperties(model, modelToSave);
            model = getRepo().save(modelToSave);
        } else {
            model = getRepo().save(model);
        }
        if (publishEvent) {
            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.POST_UPDATE, existedModel, model));
        }
        return model;
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteById(String id) {
        deleteById(id, true);
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteById(String id, boolean publishEvent) {
        T existedModel = (T) findById(id, false);
        if (existedModel == null) {
            return;
        }
        if (publishEvent) {
            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.PRE_DELETE, existedModel));
        }
        getRepo().deleteById(id);
        if (publishEvent) {
            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.POST_DELETE, existedModel));
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteByIds(String... ids) {
        if (ids == null) {
            return;
        }
        Arrays.asList(ids).stream().forEach(id -> deleteById(id));
    }

    public T findById(String id) {
        return findById(id, true);
    }

    public T findById(String id, boolean errorIfNotFound) {
        Optional<T> optional = getRepo().findById(id);
        return optional.orElse(null);
        //errorIfNotFound ? optional.orElseThrow(() -> NOT_FOUND_MODEL_BY_ID.runtimeException(getRepo().getDomainClass().getName(), id)) : optional.orElse(null);
    }

    public void errorIfNotFoundById(String id) {
        if (!getRepo().existsById(id)) {
            throw MyErrorCode.NOT_FOUND_MODEL_BY_ID.runtimeException(getRepo().getDomainClass().getName(), id);
        }
    }
}