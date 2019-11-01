package com.my.train.service;

import com.my.train.model.AbstractMainModel;
import com.my.train.repository.MyJpaRepository;
import com.my.train.util.PersistentModelEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Wtq
 * @date 2019/10/23 - 17:13
 *
 * service层需要做事件发布
 */


@Service
public abstract class AbstractMainModelService<T extends AbstractMainModel> implements ApplicationEventPublisherAware {

    //通过泛型的方式预留model位置
    protected abstract MyJpaRepository<T ,String> getRep();

    private ApplicationEventPublisher publisher;

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

//    @Transactional(rollbackOn = Throwable.class)
//    public T create(T model, boolean publishEvent) {
//        if (publishEvent) {
//            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.PRE_CREATE, model));
//        }
//        model = getRep().save(model);
//        if (publishEvent) {
//            publishEvent(new PersistentModelEvent(PersistentModelEvent.Type.POST_CREATE, model));
//        }
//        return model;
//    }


}
