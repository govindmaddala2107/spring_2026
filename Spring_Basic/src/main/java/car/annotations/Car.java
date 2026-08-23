package car.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Car {

    @Autowired
    @Qualifier("specification")
    private Specification specification;

//    @Autowired
//    public Car(Specification specification) {
//        this.specification = specification;
//    }

    public void getDetails(){
        System.out.println(specification.toString());
    }
}
