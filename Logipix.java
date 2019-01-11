import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Logipix{

	FileReader input;

	public static void main(String[] args) {
		Logipix logipx = new Logipix();
		logipx.read("InputFiles/LogiX.txt");
		
	}

	public void read(String name){
		try {
	        input = new FileReader(name);
	        BufferedReader readInput = new BufferedReader(input);
	        String a;
	 	    while((a=readInput.readLine())!=null) System.out.println(a);
	 	    input.close();

   		}catch (IOException e) {
        	System.err.println("Error while opening file");
            e.getMessage();
        }
    }

}