package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator extends AbstractDecorator {

  public MessageDecorator(Component component) {
    super(component);
  }

  @Override
  protected String doOperation(String result) {
    String decoResult = "*****" + result + "*****";
    log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", result, decoResult);
    return decoResult;
  }

  @Override
  protected String getDecoratorName() {
    return "MessageDecorator";
  }
}