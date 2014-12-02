import java.util.Queue;


public class Main {
	public static void main(String[] args){
		Parser parse = new Parser();
		
		Queue<BranchInformation> anagramInfo = parse.parseAnagram();
		
		BasicPredictor basicPredictor = new BasicPredictor(4);
		
		while (anagramInfo.size() >0) {
			BranchInformation currInfo = anagramInfo.poll();
			basicPredictor.branch(currInfo);
		}
		
		System.out.println(basicPredictor.getAccuracy());
	}
}
