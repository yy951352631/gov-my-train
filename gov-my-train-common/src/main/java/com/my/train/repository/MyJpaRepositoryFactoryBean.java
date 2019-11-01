package com.my.train.repository;

import com.my.train.model.AbstractMainModel;
import com.my.train.model.AbstractMainModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

/**
 * @author Wtq
 * @date 2019/10/23 - 10:56
 */
public class MyJpaRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID> {


    public MyJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
        return new MyJpaRepositoryFactory(entityManager);
    }

    public static class MyJpaRepositoryFactory extends JpaRepositoryFactory {
        public MyJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            if (AbstractMainModel.class.isAssignableFrom(metadata.getDomainType())) {
                return PersistentModelJpaRepositoryImpl.class;
            }
            Class<?> repositoryInterface = metadata.getRepositoryInterface();
            if (MyJpaRepository.class.isAssignableFrom(repositoryInterface)) {
                return MyJpaRepositoryImpl.class;
            }
            return super.getRepositoryBaseClass(metadata);
        }

        public static class PersistentModelJpaRepositoryImpl<T extends AbstractMainModel> extends MyJpaRepositoryImpl<T, String> {

            public PersistentModelJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
                super(entityInformation, entityManager);
            }

            @Override
            public <S extends T> S save(S entity) {
                preAdd(entity.getId());
                return super.save(entity);
            }

            public void preAdd(String id){//在添加一个实体前做的前置处理
                if(existsById(id))
                {
                    //如果存在
                    throw new RuntimeException("该用户已存在！");
                }
                System.out.println(" preAdd()");
            }

            public boolean existsById(String id) {
                return count((root, query, builder) -> {
                    return builder.equal(root.get(AbstractMainModel_.id), id);
                }) == 1L;
            }

            public T getOne(String id) {
                return findById(id).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundException"));
            }
        }
    }
}
