package car.autowire.byConstructor;

public class Car {

    private final Specification specification;

    public Car(Specification specification) {
        this.specification = specification;
    }

    public void getDetails(){
        System.out.println(specification.toString());
    }
}
