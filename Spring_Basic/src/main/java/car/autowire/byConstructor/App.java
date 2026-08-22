package car.autowire.byConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("autowiringByConstructor.xml");
        Car autoWireByNameCar = (Car) context.getBean("myCar");
        autoWireByNameCar.getDetails();

        // Specification{brand='CarBrand | byConstructor | specification', model='CarModel | byConstructor | specification'}
    }
}
