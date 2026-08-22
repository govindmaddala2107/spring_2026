package car.injection.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationBeanContext.xml");
        Car mahindraCar = (Car) context.getBean("mahindraCar");
        mahindraCar.getDetails();
    }
}
