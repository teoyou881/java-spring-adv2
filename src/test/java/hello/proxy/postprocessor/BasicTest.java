package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicTest {

  @Test
  void basicConfig(){
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

    //A는 빈으로 등록된다.
    A a = applicationContext.getBean("beanA", A.class);
    a.helloA();

    //B는 빈으로 등록되지 않는다.
    org.assertj.core.api.Assertions.assertThatThrownBy(() -> applicationContext.getBean(B.class))
                                   .isInstanceOf(NoSuchBeanDefinitionException.class);

    org.assertj.core.api.Assertions.assertThat(applicationContext.getBean(A.class))
                                   .isSameAs(a);

  }

  @Slf4j
  @Configuration
  static class BasicConfig{
    @Bean(name = "beanA")
    public A a(){
      return new A();
    }
  }


  @Slf4j
  static class A{
    public void helloA(){
      log.info("helloA");
    }
  }
  @Slf4j
  static class B{
    public void helloB(){
      log.info("helloB");
    }
  }

}
