package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    log.info("TimeProxy 실행");
    long startTime = System.currentTimeMillis();

    // 이전 방식(JDK Dynamic Proxy, CGLIB)에서는 타겟을 직접 파라미터로 넘겨줘야 했다.
    // ProxyFactory는 MethodInvocation 파라미터에 프록시, 타겟, 메서드 정보 등을 모두 포함하여 제공한다.
    // invocation.proceed()가 타겟의 메서드를 실행한다.
    Object result = invocation.proceed();
    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;

    log.info("TimeProxy 종료 resultTime={}ms", resultTime);
    return result;
  }
}