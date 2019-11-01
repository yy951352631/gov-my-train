package com.my.train.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * @author Wtq
 * @date 2019/10/23 - 10:53
 * TODO 将用到的常用的方法抽取出来，定义自己的JJpaRepository
 */
@NoRepositoryBean
public interface MyJpaRepository<T,ID> extends JpaRepository<T,ID> {

    Optional<T> findOne(Specification<T> spec);

    List<T> findAll(Specification<T> spec);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> findAll(Specification<T> spec, Sort sort);

    long count(Specification<T> spec);

    boolean existsById(ID id);

    Optional<T> findById(ID id);

    T getOne(ID id);

    <S extends T> S save(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void deleteById(ID id);

    void deleteAll(Iterable<? extends T> entities);

    int delete(Specification<T> spec);

}
