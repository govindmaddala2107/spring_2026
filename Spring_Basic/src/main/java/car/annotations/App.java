package car.annotations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotationConfig.xml");
        Car autoWireByNameCar = (Car) context.getBean("car");
        autoWireByNameCar.getDetails();

        // Specification{brand='CarBrand | byConstructor | specification', model='CarModel | byConstructor | specification'}
    }
}
