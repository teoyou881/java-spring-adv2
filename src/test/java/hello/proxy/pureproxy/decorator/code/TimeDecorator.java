package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator extends AbstractDecorator {

  public TimeDecorator(Component component) {
    super(component);
  }

  @Override
  protected String doOperation(String result) {
    long startTime = System.currentTimeMillis();
    String operationResult = result;
    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("TimeDecorator 종료 resultTime={}ms", resultTime);
    return operationResult;
  }

  @Override
  protected String getDecoratorName() {
    return "TimeDecorator";
  }
}