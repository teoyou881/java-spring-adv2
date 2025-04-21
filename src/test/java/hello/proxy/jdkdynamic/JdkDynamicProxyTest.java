package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.AImpl;
import hello.proxy.jdkdynamic.code.AInterface;
import hello.proxy.jdkdynamic.code.BImpl;
import hello.proxy.jdkdynamic.code.BInterface;
import hello.proxy.jdkdynamic.code.TimeInvocationHandler;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class JdkDynamicProxyTest {

  @Test
  void dynamicA() {
    AInterface target = new AImpl();
    TimeInvocationHandler handler = new TimeInvocationHandler(target);

    // Proxy.newProxyInstance(classLoader, interfaces, h)
    // 1. 클래스 로더 정보
    // 2. 인터페이스 - 어떤 인터페이스 기반으로 프록시를 만들지 선택
    // 3. 프록시가 호출할 로직이 담긴 InvocationHandler 구현체
    AInterface proxy = (AInterface) Proxy.newProxyInstance(
        AInterface.class.getClassLoader(),
        new Class[]{AInterface.class}, handler);
    proxy.call();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
  }

  @Test
  void dynamicB() {
    BInterface target = new BImpl();
    TimeInvocationHandler handler = new TimeInvocationHandler(target);
    // 1. 클래스 로더 정보
    // 2. 인터페이스 - 어떤 인터페이스 기반으로 프록시를 만들지 선택
    // 3. 프록시가 호출할 로직이 담긴 InvocationHandler 구현체
    BInterface proxy = (BInterface) Proxy.newProxyInstance(
        BInterface.class.getClassLoader(),
        new Class[]{BInterface.class}, handler);
    proxy.call();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
  }
}