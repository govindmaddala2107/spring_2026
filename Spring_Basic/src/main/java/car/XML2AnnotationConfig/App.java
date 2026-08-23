package car.XML2AnnotationConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("annotationConfig.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Car autoWireByNameCar = context.getBean("car", Car.class);
        autoWireByNameCar.getDetails();

        // Specification{brand='CarBrand | byConstructor | specification', model='CarModel | byConstructor | specification'}
    }
}
