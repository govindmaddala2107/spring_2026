package car.injection.setter;

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
