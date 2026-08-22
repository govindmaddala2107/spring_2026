package car.autowire.byName;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("autowiringByName.xml");
        Car autoWireByNameCar = (Car) context.getBean("autoWireByNameCar");
        autoWireByNameCar.getDetails();
    }
}
