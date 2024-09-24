//Ciaran Gray
//Student number: 22427722
public class HealthPractice { //class to store the health practice's name and address together in 1 object
    private String hpName, hpAddress;

    public HealthPractice(String hpName, String hpAddress) {
        this.hpName = hpName;
        this.hpAddress = hpAddress;
    }
    public String getHealthPracticeAddress() {
        return hpAddress;
    }

    public String getHealthPracticeName() {
        return hpName;
    }

    public String toString() {
        return "Health practice name: " + hpName + "\n" + "Health practice address: " + hpAddress;
    }

}
