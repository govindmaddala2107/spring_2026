# Spring & Springboot
## Spring
### Configuration
- Dependencies needed are:
    - spring-core
    - spring-context
- In resources package: create any xml with below snippet:
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

            ------------ <bean tags>
    </beans>
    ```
### Bean Creation:
- Create any class of your own.
    ```java
    package car.example.bean;

    public class MyBean {
        private String message;

        public void setMessage(String message) {
            this.message = message;
        }

        public void showMessage(){
            System.out.println("Message " + message);
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
    ```
- Now in resources/beanConfig.xml, we will configure the bean:
    ```xml
    <!-- Simple Bean -->
    <bean id="myBean" class="car.example.bean.MyBean">
        <property name="message" value="Value set from xml" />
    </bean>
    <!-- Simple Bean -->
    ```
    - id: object name
    - class: path of that class
    - property: object's properties [here it is message]
- Now in main method, we will create spring context.
    ```java
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
    ```
    - So here, value to message is set from xml file and same id name has to give to getBean, else it will throw an error.

### Dependency Injection:
#### Constructor Injection:
- Now let's say there is Specification class is needed for Car class and there are as follows:
- Specification.java:
    ```java
    package car.injection.constructor;

    public class Specification {
        private String brand;
        private String model;

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return "Specification{" +
                    "brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }
    }
    ```

- Car.java
    ```java
    package car.injection.constructor;

    public class Car {

        private Specification specification;

        public Car(Specification specification) {
            this.specification = specification;
        }

        public void getDetails(){
            System.out.println(specification.toString());
        }
    }
    ```
- Now in Spring, Inverse of Control [IoC], takes care of object creation and maintain them. So for that we need xml configuration as follows:
    ```xml
    <!--    Constructor Injection-->
    <bean id="toyatoSpecification" class="car.injection.constructor.Specification">
        <property name="brand" value="Toyato by Constructor" />
        <property name="model" value="Glanza by Constructor" />
    </bean>

    <bean id="toyatoCar" class="car.injection.constructor.Car" >
        <constructor-arg ref="toyatoSpecification" />
    </bean>
    <!--    Constructor Injection-->
    ```
    - Specification is just like some normal bean creation but for injection of that bean into another bean, we need 
    **constructor-arg** tag and ref value should be as same as id of bean which we need to inject. 
        - So specification bean name is **toyatoSpecification** and in **toyatoCar** car bean, we want toyatoSpecification bean so we used **toyatoSpecification**.
- Now in main method, we can refer toyatoCar bean and their console outputs are as follows:
    ```java
    package car.injection.constructor;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationBeanContext.xml");
            Car toyato = (Car) context.getBean("toyatoCar");
            toyato.getDetails();
            /* 
            
            Specification{brand='Toyato by Constructor', model='Glanza by Constructor'}

            */
        }
    }
    ```
- Important step in constructor injection is:
    - In Car.java, we inject Specification in constructor as follows:
    ```java
    public Car(Specification specification) {
            this.specification = specification;
    }
    ```
    - In Xml:
    ```xml
     <constructor-arg ref="toyatoSpecification" />
    ```

#### Setter Injection:
- Now let's say there is Specification class is needed for Car class and there are as follows:
- Specification.java: [remains same]
    ```java
    package car.injection.constructor;

    public class Specification {
        private String brand;
        private String model;

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return "Specification{" +
                    "brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }
    }
    ```

- Car.java [instead of constructor, getter and setter are added]
    ```java
    package car.injection.constructor;

    public class Car {

        private Specification specification;

        public Specification getSpecification() {
            return specification;
        }

        public void setSpecification(Specification specification) {
            this.specification = specification;
        }

        public void getDetails(){
            System.out.println(specification.toString());
        }
    }
    ```
- Now in Spring, Inverse of Control [IoC], takes care of object creation and maintain them. So for that we need xml configuration as follows:
    - specification bean is same but for understanding I made it to Mahindra and Thar
    ```xml
    <!--    Setter Injection-->
    <bean id="mahindraSpecification" class="car.injection.setter.Specification">
        <property name="brand" value="Mahindra by Setter" />
        <property name="model" value="Thar by Setter" />
    </bean>

    <bean id="mahindraCar" class="car.injection.setter.Car" >
        <property name="specification" ref="mahindraSpecification" />
    </bean>
    <!--    Setter Injection-->
    ```
    - Specification is just like some normal bean creation but for injection of that bean into another bean in setter way, we need normal **property** but since it is injection, instead of name and value, here we use **ref** attribute.
- Now in main method, we can refer toyatoCar bean and their console outputs are as follows:
    ```java
    package car.injection.constructor;
    package car.injection.setter;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationBeanContext.xml");
            Car mahindraCar = (Car) context.getBean("mahindraCar");
            mahindraCar.getDetails();

            /*
            
            Specification{brand='Mahindra by Setter', model='Thar by Setter'}
            
            */
        }
    }
    ```

- Important step in setter injection is:
    - In Car.java, we inject Specification in setter as follows:
    ```java
    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }
    ```
    - In Xml:
    ```xml
     <property name="specification" ref="mahindraSpecification" />
    ```











