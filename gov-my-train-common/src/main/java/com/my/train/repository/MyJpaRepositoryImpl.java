package com.my.train.repository;

import com.my.train.model.AbstractMainModel;
import com.my.train.model.AbstractMainModel_;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;

/**
 * @author Wtq
 * @date 2019/10/23 - 17:20
 */
public class MyJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyJpaRepository<T, ID> {


    public MyJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.asc(AbstractMainModel_.code.getName()));

    protected JpaEntityInformation<T, ?> entityInformation;
    protected EntityManager em;

    public MyJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityInformation = entityInformation;
        this.em = em;
    }

    public Class<T> getDomainClass() {
        return super.getDomainClass();
    }

    protected <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {
        CrudMethodMetadata metadata = getRepositoryMethodMetadata();
        TypedQuery<S> typedQuery = super.getQuery(customizedSpec(spec, metadata), domainClass, customizedSort(sort, metadata));
        return typedQuery;
    }

    protected <S extends T> Specification<S> customizedSpec(Specification<S> spec, CrudMethodMetadata metadata) {
        return spec;
    }

    protected Sort customizedSort(Sort sort, CrudMethodMetadata metadata) {
        if (sort == null || sort == Sort.unsorted()) {
            if (AbstractMainModel.class.isAssignableFrom(getDomainClass())) {
                return DEFAULT_SORT;
            }
        }
        return sort;
    }

    public int delete(Specification<T> spec) {
        return getDeleteQuery(spec, getDomainClass()).executeUpdate();
    }

    private <S extends T> Query getDeleteQuery(@Nullable Specification<S> spec, Class<S> domainClass) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaDelete<S> delete = builder.createCriteriaDelete(domainClass);
        Root<S> root = delete.from(domainClass);
        if (spec != null) {
            // 这里是个特殊处理
            Predicate predicate = spec.toPredicate(root, builder.createQuery(Long.class)/*和countQuery的返回值用一样的, 各个服务实现的Specification对于这种返回值不能fetch其他实体*/, builder);
            if (predicate != null) {
                delete.where(predicate);
            }
        }
        return em.createQuery(delete);
    }
}
