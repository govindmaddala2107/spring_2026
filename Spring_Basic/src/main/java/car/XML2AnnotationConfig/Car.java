package car.XML2AnnotationConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

//    @Autowired
    private Specification specification;

    @Autowired
    public Car(Specification specification) {
        this.specification = specification;
    }

    public void getDetails(){
        System.out.println(specification.toString());
    }
}
