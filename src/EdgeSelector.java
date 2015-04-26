import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class EdgeSelector {
	private static double rejectMin, rejectLimit;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// compute filter parameters for netid shs257
		double fromNetID = 0.752; // 752 is 257 reversed
		rejectMin = 0.9 * fromNetID;
		rejectLimit = rejectMin + 0.01;
		
		//System.out.println("RejectMin: " + rejectMin);
		//System.out.println("RejectLimit: " + rejectLimit);
		
		processEdges("data/ouredges.txt");
		
	}
	
	// assume 0.0 <= rejectMin < rejectLimit <= 1.0
	private static boolean selectInputLine(double x) {
		return ( ((x >= rejectMin) && (x < rejectLimit)) ? false : true );
	}
	
	// read each line and determine if it's going to be used
	private static void processEdges(String fileName) throws FileNotFoundException, IOException {
		PrintWriter fileWriter = new PrintWriter(fileName);
		try (BufferedReader br = new BufferedReader(new FileReader("data/edges.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] parts = line.split("\\s+");
		       boolean selectLine = selectInputLine(Double.parseDouble(parts[1]));
		       if(selectLine) {
		    	   fileWriter.println(parts[2] + " "+ parts[3]);
		       }
		    }
		    fileWriter.close();
		}
		
	}
	

}
