package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.Component;
import hello.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.decorator.code.MessageDecorator;
import hello.proxy.pureproxy.decorator.code.RealComponent;
import hello.proxy.pureproxy.decorator.code.TimeDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatterTest {

  @Test
  void noDecorator(){
    Component realComponent = new RealComponent();
    new DecoratorPatternClient(realComponent).execute();
  }

  @Test
  void decorator1(){
    Component realComponent = new RealComponent();
    Component decorator = new MessageDecorator(realComponent);
    new DecoratorPatternClient(decorator).execute();
  }

  @Test
  void decorator2() {
    Component realComponent = new RealComponent();
    Component messageDecorator = new MessageDecorator(realComponent);
    Component timeDecorator = new TimeDecorator(messageDecorator);
    DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
    client.execute();
  }
}
