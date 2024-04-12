/**
 * Description: A NOR gate object for project 1. Has 2 input strings that will specify another gate or variable, and a name.
 *
 * @programmer Manny Flores
 * @course COSC321, W'24
 */
public class NorGate {
    //Stores the string values of our two inputs (e. g. a or G00)
    private String name;
    private String inputOne;
    private String inputTwo;
    public NorGate(String name) {
        this.name = name;
    }

    public String getName(){
        return inputOne;
    }
    public String getInputOne(){
        return inputOne;
    }
    public String getInputTwo(){
        return inputTwo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInputOne(String inputOne) {
        this.inputOne = inputOne;
    }

    public void setInputTwo(String inputTwo) {
        this.inputTwo = inputTwo;
    }


    @Override
    public String toString() {
        return "NorGate{" +
                "name='" + name + '\'' +
                ", inputOne='" + inputOne + '\'' +
                ", inputTwo='" + inputTwo + '\'' +
                '}';
    }
}
