package car.example.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationBeanContext.xml");
        MyBean myBean = (MyBean) context.getBean("myBean");
        System.out.println("Actual bean value----");
        System.out.println(myBean);
        System.out.println("Bean after message is changed from App----");
        myBean.setMessage("MyBean set from context");
        System.out.println(myBean);

        /*
        ==================================================
        Console output is:
        Actual bean value----
        MyBean{message='Value set from xml'}
        Bean after message is changed from App----
        MyBean{message='MyBean set from context'}
        ==================================================
        */
    }
}

