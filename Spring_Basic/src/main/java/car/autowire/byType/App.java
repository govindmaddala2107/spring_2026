package car.autowire.byType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("autowiringByType.xml");
        Car autoWireByNameCar = (Car) context.getBean("myCar");
        autoWireByNameCar.getDetails();

        // Specification{brand='CarBrand | byType | specification', model='CarModel | byType | specification'}
    }
}
