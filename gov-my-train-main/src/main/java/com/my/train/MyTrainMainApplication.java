package com.my.train;

import com.my.train.repository.MyJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Wtq
 * @date 2019/10/23 - 14:17
 */
@EnableSwagger2
@SpringBootApplication
@EntityScan
@EnableJpaRepositories(repositoryFactoryBeanClass = MyJpaRepositoryFactoryBean.class)
public class MyTrainMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyTrainMainApplication.class,args);
    }
}
