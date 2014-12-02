import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class Parser {
	
	public static final String ANAGRAM = "anagram.txt";
	public int count = 0;
	public static void main(String[] args){
		Parser currParser = new Parser();
		Queue<BranchInformation> anagram = null;
		try {
			anagram = currParser.parseFile(ANAGRAM);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String hello = "";
		hello = "hi";
	}
	
	public Queue<BranchInformation> parseAnagram(){
		try {
			return parseFile(ANAGRAM);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Queue<BranchInformation> parseFile(String fileName) throws IOException{
		Queue<BranchInformation> branchInfo = new LinkedList<BranchInformation>();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line = reader.readLine();
		
		while (line!=null){
			String[] information = line.split(":");
			count++;
			boolean isTaken = false;
			if (information[0].equals("TAKEN")){
				isTaken = true;
			}
			
			int instNumber = Integer.parseInt(information[1]);
			
			BranchInformation currInfo = new BranchInformation(isTaken, instNumber);
			
			branchInfo.add(currInfo);
			
			line = reader.readLine();
		}
		
		
		return branchInfo;
		
	}
}
