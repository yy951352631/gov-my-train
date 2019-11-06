package com.my.train.service;

import com.my.train.exception.MyErrorCode;
import com.my.train.model.AbstractMainModel;
import com.my.train.model.AbstractMainModel_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;

/**
 * @author Wtq
 * @date 2019/11/1 - 17:58
 */

public abstract class AbstractMainModelService<T extends AbstractMainModel> extends AbstractPersistentModelService<T> {

    @Transactional(rollbackOn = Throwable.class)
    public T create(T model, boolean publishEvent) {
        preCreate(model);
        return super.create(model, publishEvent);
    }

    @Transactional(rollbackOn = Throwable.class)
    public T update(T model, boolean dynamicUpdate, boolean publishEvent) {
        preUpdate(model);
        if (!dynamicUpdate) {
            // 非动态更新的情况下, 需要把数据库已存在记录的createdDate写入到需要更新的实体上
            model.setCreatedDate(findById(model.getId()).getCreatedDate());
        }
        return super.update(model, dynamicUpdate, publishEvent);
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteById(String id, boolean publishEvent) {
        preDelete(id);
        super.deleteById(id, publishEvent);
    }

    public void findAllAndDo(ModelRunnable<T> runnable) {
        int pageNum = 0;
        int pageSize = 50;
        int totalPageNum = Integer.MAX_VALUE;
        while (pageNum < totalPageNum) {
            Pageable pageable = PageRequest.of(pageNum, pageSize);
//            Page<T> page = getRepo().findAll(null, pageable);
            Page<T> page = getRepo().findAll(pageable);
            for (T model : page.getContent()) {
                runnable.run(model);
            }
            pageNum += 1;
            totalPageNum = page.getTotalPages();
        }
    }

    public static interface ModelRunnable<T> {
        void run(T model);
    }

    public void checkCode(String code) {
        System.out.println("*******************!!!!!!!!!!!!!!!!!!!");
        if(StringUtils.isBlank(code)){ //code字段不能为空
            System.out.println("*******************????????????????");
            throw MyErrorCode.NOT_ALLOWED_MODEL_CODE_NULL.runtimeException(getRepo().getDomainClass().getSimpleName(), code);
        }
        if (getRepo().count((root, query, builder) -> {//
            return builder.equal(builder.upper(root.get(AbstractMainModel_.code)), code.toUpperCase());
        }) == 1L) {
            throw MyErrorCode.FOUND_DUPLICATED_MODEL_WITH_SAME_CODE.runtimeException(getRepo().getDomainClass().getSimpleName(), code);
        }
    }

    // 注意: 用于校验数据, 不要做其他事情, 子类也要注意
    protected void preCreate(T model) {
        // 现在新增时在code是必填字段
        checkCode(model.getCode());
    }

    // 注意: 用于校验数据, 不要做其他事情, 子类也要注意
    protected void preUpdate(final T model) {
        // 支持部分更新某几个字段,所以code可以不传
        if (!StringUtils.isBlank(model.getCode())) {
            if (getRepo().count((root, query, builder) -> {
                return builder.and(builder.equal(root.get(AbstractMainModel_.code), model.getCode()), builder.equal(root.get(AbstractMainModel_.id), model.getId()));
            }) == 0L) {
                throw MyErrorCode.NOT_ALLOWED_CHANGE_MODEL_CODE.runtimeException(getRepo().getDomainClass().getName(), model.getId());
//                throw new RuntimeException("不允许改变code");
            }
        }
    }

    // 注意: 用于校验数据, 不要做其他事情, 子类也要注意
    protected void preDelete(String id) {
        if(!getRepo().existsById(id)){
            throw MyErrorCode.NOT_FOUND_MODEL_BY_ID.runtimeException(getRepo().getDomainClass());
        }
    }
}
