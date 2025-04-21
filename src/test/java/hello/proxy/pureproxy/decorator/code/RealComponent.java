package hello.proxy.pureproxy.decorator.code;

import lombok.Lombok;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealComponent implements Component {

  @Override
  public String operation() {
    log.info("RealComponent.operation (Line: 9)");
    return "data";

  }
}
