package com.my.train.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Wtq
 * @date 2019/11/1 - 15:34
 */
public class MyJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements MyJpaRepository<T, ID> {

    protected JpaEntityInformation<T, ?> entityInformation;
    protected EntityManager em;

    public MyJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public Class<T> getDomainClass(){
        return super.getDomainClass();
    }

    /**
     * 按条件删除
     * @param spec 删除的条件
     * @return
     */
    @Override
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
