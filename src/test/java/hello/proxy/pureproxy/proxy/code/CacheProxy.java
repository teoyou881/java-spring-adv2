package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

  private Subject target;
  private String cacheValue;

  public CacheProxy(RealSubject realSubject) {
    this.target = realSubject;
  }

  @Override
  public String operation() {
    log.info("CacheProxy.operation (Line: 13)");
    if (cacheValue == null) {
      cacheValue = target.operation();
    }
    return cacheValue;
  }
}
