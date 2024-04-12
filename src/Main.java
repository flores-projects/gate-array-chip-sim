import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Description: This program will simulate an m x n array of two input NOR gates to process j inputs into k outputs.
 * m, n, j and k are variables set at compile time.
 *
 * @programmer Manny Flores
 * @course COSC321, W'24
 * @project 1
 * @due (3-17-24)
 */

public class Main {
    static final int WIDTH = 4;
    static final int HEIGHT = 4;

    static final int INPUT_NUM = 3;
    static final int OUTPUT_NUM = 1;

    private static NorGate[][] circuit;
    private static String[] output;
    private static boolean[] inputBools;

    private static Scanner keyboard;

    private static File input;

    public static void main(String[] args) {
        keyboard = new Scanner(System.in);
        circuit = new NorGate[WIDTH][HEIGHT];
        output = new String[OUTPUT_NUM];
        inputBools = new boolean[INPUT_NUM];

        populateGates();
        //will loop until createFile() AND processInput() returns true, meaning a valid file with a valid structure has been entered.
        while(true){
            if(createFile()){
                if(processInput()){
                    break;
                }
                System.out.println("Something went wrong! File syntax may be incorrect.");
            }
        }
        getOutputValues();

    }

    /**
     *  Populate the matrix of gates with gates that are named accordingly to their position.
     *
     *  @return none
     */
    private static void populateGates(){
        for(int i = 0; i < circuit.length; i++){
            for(int j = 0; j < circuit.length; j++){
                circuit[i][j] = new NorGate("G" + i + j);
            }
        }
    }

    /**
     *  Create the file object from the user inputting the name. Returns true if successful.
     *
     *  @return boolean
     */
    private static boolean createFile(){
        System.out.print("Enter an input file name: ");
        String inputFile = keyboard.next();
        input = new File(inputFile);
        if(input.exists()){
            return true;
        }else{
            System.out.println("File does not exist!");
        }
        return false;
    }

    /**
     *  Reads the input and updates all the NOR gates in the matrix to match with the specified connections.
     *  Returns true if successful.
     *
     *  @return boolean
     */
    private static boolean processInput(){
        try {
            Scanner inputScan = new Scanner(input);
            //Loop through every line of the input, each line is its own connection which must be read.
            while (inputScan.hasNextLine()) {
                String line = inputScan.nextLine();
                //Split up the LHS and RHS to evaluate both.
                String[] bothSides = line.split(">");
                //If the destination is a Gate instead of an Output, we set the appropriate gate input to the source.
                if(bothSides[1].charAt(0) == 'G'){
                    //Get the destination gate number and specified input separately.
                    String[] gateAndInput = bothSides[1].split("I");
                    switch(gateAndInput[1]) {
                        case "0":
                                getGate(gateAndInput[0]).setInputOne(bothSides[0]);
                            break;
                        case "1":
                                getGate(gateAndInput[0]).setInputTwo(bothSides[0]);
                            break;
                        default:
                            //This means the user specified an input that was not 0 or 1.
                            return false;
                    }
                }else if(bothSides[1].charAt(0) == 'M'){
                    //if the destination is an output, we just want to name the output after the source gate so
                    //we know where to start when trying to figure out the boolean value of output
                    output[Character.getNumericValue(bothSides[1].charAt(1) - 1)] = bothSides[0];
                }else{
                    //if the destination doesnt start with M or G, thats a huge problem for the syntax so we return false.
                    return false;
                }


            }
            inputScan.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     *  Takes in the name of a gate and returns the nor gate in the matrix by reading the row and column values.
     *
     *  @param gateName The name of the gate
     *  @return NorGate
     */
    private static NorGate getGate(String gateName){
        //remove the G at the start so it is a 2 character string with the first character being the row and second being column.
        gateName = gateName.substring(1);
        return circuit[Character.getNumericValue(gateName.charAt(0))][Character.getNumericValue(gateName.charAt(1))];
    }

    /**
     *  Loops over every possible combination of inputs and finds the output value for each. Prints all the information
     *  in a table for the user.
     *
     * @return none
     */
    private static void getOutputValues(){
        //we need to loop 2 to the power of our input length times.
        for(int i = 0; i < (Math.pow(2, INPUT_NUM)); i++){
            //Loop over every input each time to find what value it should be for this specific loop.
            for(int j = 0; j < INPUT_NUM; j++){
                //For the first input from the right, we want the 1s and 0s to alternate each row.
                //For the second one, we want them to alternate every second, then every fourth, then eighth, etc.
                double column = Math.pow(2,(INPUT_NUM - j - 1));
                if(Math.floor(i/column) % 2 == 0){
                    System.out.print("0 ");
                    inputBools[j] = false;
                }else{
                    System.out.print("1 ");
                    inputBools[j] = true;
                }
            }
            System.out.print("| ");
            //Now to get the output values.
            for(int k = 0; k < output.length; k++){
                if(getGateValue(output[k])){
                    System.out.print("1 ");
                }else{
                    System.out.print("0 ");
                }
            }

            System.out.println("");
        }
    }

    /**
     *  A recursive function which will get the value of a specified gate by checking its two inputs with the same function.
     *  Whenever it runs into a variable, it will return the current value of that variable.
     *
     * @param gateName The name of the gate
     * @return boolean
     */
    private static boolean getGateValue(String gateName){
        //make sure that any inputs that go unused (have no connection) are LOGICAL 1.
        if(gateName == null){
            return true;
        }

        if(gateName.charAt(0) == 'G'){
            NorGate gate = getGate(gateName);
            //return the NOR operation of the outputs of the two inputs of the gate.
            return !(getGateValue(gate.getInputOne()) || getGateValue(gate.getInputTwo()));
        }else{
            //If the format was correct, the only time an input doesn't start with G, it will be a variable. We will
            //now find the truth value of that variable and return it
            return inputBools[gateName.charAt(0) - 97];
        }
    }

    //These two are not used by the program, leftover from debugging activities. Figured that it would be helpful to
    //leave them in if I ever decide to revisit this code.
    private static void printCircuit(){
        for(int i = 0; i < circuit.length; i++){
            for(int j = 0; j < circuit.length; j++){
                System.out.print(circuit[i][j]);
            }
            System.out.println();
        }
    }

    private static void printOutput(){
        for(int i = 0; i < output.length; i++){

            System.out.print(output[i] + " ");
        }
    }
}