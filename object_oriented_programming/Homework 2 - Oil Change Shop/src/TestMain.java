
public class TestMain
{
    public static void main(String[] args) {
        CarList test = new CarList();
        
        Car c1 = new Car(Make.Jeep, "Willy");
        Car c2 = new Car(Make.Chevy, "Soccer Mom");
        Car c3 = new Car(Make.Ford, "Jill");
        Car c4 = new Car(Make.Chevy, "Levy");
        Car c5 = new Car(Make.Jeep, "GI Joe");
        Car c6 = new Car(Make.Ford, "Henry");
        
        
        try {
        test.appendToTail(c1);
        test.appendToTail(c2);
        test.appendToTail(c3);
        test.appendToTail(c4);
        test.appendToTail(c5);
        test.appendToTail(c6);
        } catch (Exception e) {
        }
        
        System.out.println(test.toString());
        test.sortList();
        System.out.println(test.toString());

    }
}
