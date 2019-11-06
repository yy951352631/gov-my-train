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
 * TODO 将用到的常用的方法抽取出来，定义自己的JpaRepository
 */

/**
 * @NoRepositoryBean注释：
 * 用于排除存储库接口，从而导致创建实例。
 * 当为所有存储库提供扩展的基础接口，并结合自定义存储库基类以实现在该中间接口中声明的方法时，
 * 通常会使用此方法。 在这种情况下，您通常会从中间接口派生出具体的存储库接口，但又不想为中间接口创建Spring bean。
 *  
 *    @作者Oliver Gierke
 */
@NoRepositoryBean //表明是一个中间接口，（标注这个注解则不会注入对应的实现bean，）被标注类主要用于拓展JpaRepository（或其他的repository），可以自定义一些官方未提供的方法
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

    Class<?> getDomainClass();

    int delete(Specification<T> spec);
}
