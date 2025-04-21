package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDecorator implements Decorator {
    
    protected final Component component;
    
    public AbstractDecorator(Component component) {
        this.component = component;
    }
    
    @Override
    public String operation() {
        log.info(getDecoratorName() + " 실행");
        String result = doOperation(component.operation());
        log.info(getDecoratorName() + " 종료");
        return result;
    }
    
    // 각 구체 데코레이터가 구현해야 하는 메서드
    protected abstract String doOperation(String result);
    
    // 데코레이터 이름 반환 (로깅용)
    protected abstract String getDecoratorName();
}