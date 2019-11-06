package com.my.train.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;

/**
 * @author Wtq
 * @date 2019/11/1 - 17:11
 */

/**
 * 设置自己的Repository管理工厂，针对不同的实体类返回不同的repositoryImpl实例（在这里我们暂且先只返回我们定义的唯一的实例repository实现类MyJpaRepositoryImpl）
 *
 * @param <T> ： 具体的实体类对应的Repository   如TeacherRepository extends MyJpaRepository<Teacher, String>
 * @param <S>  ：
 * @param <ID> ： Entity的主键
 */
public class MyJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID> {
    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public MyJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
        //将自定义的JpaRepositoryFactory注入到ioc容器作为Repository的实例化工厂
        return new MyJpaRepositoryFactory(entityManager);
    }

    public static class MyJpaRepositoryFactory extends JpaRepositoryFactory {

        public MyJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            Class<?> repositoryInterface = metadata.getRepositoryInterface();
            if (MyJpaRepository.class.isAssignableFrom(repositoryInterface)) {
                //继承与MyJpaRepository的repository，则我们使用自己的dao层实现类
                return MyJpaRepositoryImpl.class;
            } else {
                //返回JpaRepository接口的默认的实现类（SimpleJpaRepository）
                return super.getRepositoryBaseClass(metadata);
            }
        }
    }
}
