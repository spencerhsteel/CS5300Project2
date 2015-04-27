import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class EdgeSelector {
	private static double rejectMin, rejectLimit;
	private int totalNodes = 685230;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// compute filter parameters for netid shs257
		double fromNetID = 0.752; // 752 is 257 reversed
		rejectMin = 0.9 * fromNetID;
		rejectLimit = rejectMin + 0.01;
		
		//System.out.println("RejectMin: " + rejectMin);
		//System.out.println("RejectLimit: " + rejectLimit);
		
		//ArrayList<String> edges = processEdges();
		//writeToFile("data/ouredges.txt",edges);
		
		ArrayList<String> blocks = processBlocks();
		writeToFile("data/nodeToBlocks.txt",blocks);
		
		
	}
	
	// assume 0.0 <= rejectMin < rejectLimit <= 1.0
	private static boolean selectInputLine(double x) {
		return ( ((x >= rejectMin) && (x < rejectLimit)) ? false : true );
	}
	
	// read each line and determine if it's going to be used
	private static ArrayList<String> processEdges() throws FileNotFoundException, IOException {
		ArrayList<String> list = new ArrayList<String>();
		int nodeCounter = 0;
		BufferedReader br = new BufferedReader(new FileReader("data/edges.txt"));
	    String line;
	    while ((line = br.readLine()) != null) {
	       String[] parts = line.split("\\s+");
	       boolean selectLine = selectInputLine(Double.parseDouble(parts[1]));
	       if(selectLine) {
	    	   //fileWriter.println(parts[2] + " "+ parts[3]);
	    	   list.add(parts[2] + " "+ parts[3]);
	    	   if(Double.parseDouble(parts[2]) == nodeCounter) {
	    		   // Do nothing... we are seeing another edge for current node
	    	   } else if (Double.parseDouble(parts[2]) == nodeCounter + 1) {
	    		   // We have moved to the next
	    		   nodeCounter++;
	    	   } else {
	    		   // not equal to nodeCounter or +1, only thing that could have happened is we skipped a node
	    		   //System.out.println("MISSED A NODE: Node ID: " + parts[2] + " Counter: " + nodeCounter);
	    	   }
	       }
	    }
	    br.close();
	    System.out.println("Total Nodes: " + nodeCounter);
	    return list;
		
		
	}
	
	private static ArrayList<String> processBlocks() throws IOException {
		ArrayList<String> nodeBlocks = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("data/blocks.txt"));
	    String line;
	    int currentNode = 0;
	    int currentBlock = 0;
	    while ((line = br.readLine()) != null) {
	    	int blockSize = Integer.parseInt(line.trim());
	    	int i;
	    	for(i = 0; i<blockSize; i++) {
	    		nodeBlocks.add(currentNode + " " + currentBlock);
	    		currentNode++;
	    	}
	    	currentBlock++;
	    }
	    br.close();
		return nodeBlocks;
	}
	
	private static void writeToFile(String fileName, ArrayList<String> list) throws FileNotFoundException {
		PrintWriter fileWriter = new PrintWriter(fileName);
		for(String s: list) {
			fileWriter.println(s);
		}
		fileWriter.close();
		
	}
	
	

}
