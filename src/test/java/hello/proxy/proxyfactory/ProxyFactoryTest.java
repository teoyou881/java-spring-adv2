package hello.proxy.proxyfactory;

import static org.assertj.core.api.Assertions.assertThat;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

// AopUtils는 Spring AOP 프록시 관련 정보를 확인할 수 있는 유틸리티 클래스이다.
// 주의: AopUtils는 Spring의 ProxyFactory로 생성된 프록시만 감지할 수 있으며,
// 직접 만든 JDK 동적 프록시나 CGLIB 프록시는 감지하지 못한다.
@Slf4j
public class ProxyFactoryTest {

  @Test
  @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
  void interfaceProxy() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvice(new TimeAdvice());
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());

    proxy.save();

    // AopUtils를 사용하면 편하게 체크할 수 있다.
    // 다만 proxyFactory를 사용해서 프록시를 만들었을때만 가능하다.
    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
    assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
  }

  @Test
  @DisplayName("구체 클래스만 있으면 CGLIB 사용")
  void concreteProxy() {
    // 구체 클래스인 ConcreteService 객체 생성 (인터페이스 구현 없음)
    ConcreteService target = new ConcreteService();
    
    // ProxyFactory는 대상 객체가 인터페이스를 구현하지 않았을 경우 CGLIB 프록시를 사용함
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvice(new TimeAdvice());
    
    // CGLIB로 생성된 프록시는 ConcreteService를 상속받아 만들어진 객체임
    // 클래스명은 ConcreteService$$EnhancerBySpringCGLIB$$... 형태로 생성됨
    ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
    
    // 프록시 객체의 메서드 호출 시, 부가 기능(TimeAdvice)이 수행된 후 실제 대상 객체 메서드 호출
    proxy.call();
    
    // AopUtils를 사용하여 프록시 타입 검증
    // 프록시가 생성되었고(isAopProxy), JDK 동적 프록시가 아니며(isJdkDynamicProxy), 
    // CGLIB 프록시임(isCglibProxy)을 확인
    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
  }

  @Test
  @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
  void proxyTargetClass() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true); //중요
    proxyFactory.addAdvice(new TimeAdvice());
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
    proxy.save();
    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
  }
}