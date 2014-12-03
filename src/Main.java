import java.util.Queue;


public class Main {
	private static final int CONTEXT_SWITCH_AMOUNT = 250;
	private static boolean isOnFirst = true;
	private static int instCount=0;
	public static void main(String[] args){
		Parser parse = new Parser();
		
		Queue<BranchInformation> anagramInfo = parse.parseAnagram();
		Queue<BranchInformation> gccInfo = parse.parseGcc();
		
		basicTest(anagramInfo, gccInfo);
		anagramInfo = parse.parseAnagram();
		gccInfo = parse.parseGcc();
		contextSwitchTest(anagramInfo, gccInfo);
		
		
		
		
	}

	private static void contextSwitchTest(Queue<BranchInformation> anagramInfo,
			Queue<BranchInformation> gccInfo) {
		
		BasicPredictor predictor = new BasicPredictor(2);

		while (anagramInfo.size() > 0 || gccInfo.size() > 0){
			BranchInformation currInfo = getNextInfo(instCount,anagramInfo, gccInfo);
			instCount++;
			
			predictor.branch(currInfo);
		}
		
		System.out.println("Context Switch Result: " + predictor.getAccuracy());
	}

	private static void basicTest(
			Queue<BranchInformation> anagramInfo,
			Queue<BranchInformation> gccInfo) {
		BasicPredictor anagramPredictor = new BasicPredictor(2);
		BasicPredictor gccPredictor = new BasicPredictor(2);
		
		while (anagramInfo.size() >0) {
			BranchInformation currInfo = anagramInfo.poll();
			anagramPredictor.branch(currInfo);
		}
		
		while(gccInfo.size() > 0){
			BranchInformation currInfo = gccInfo.poll();
			gccPredictor.branch(currInfo);
		}
		
		System.out.println("Anagram predictor accuracy: " + anagramPredictor.getAccuracy());
		System.out.println("GCC predictor accuracy: " + gccPredictor.getAccuracy());
	}
	
	private static BranchInformation getNextInfo(int instCount,Queue<BranchInformation> anagram, Queue<BranchInformation> gcc){
		if (anagram.size() == 0){
			return gcc.poll();
		}
		else if (gcc.size()==0){
			return anagram.poll();
		}
		
		if (instCount%CONTEXT_SWITCH_AMOUNT == 0){
			isOnFirst = !isOnFirst;
		}
		
		BranchInformation currInfo = null;
		
		if (isOnFirst) currInfo = anagram.poll();
		else currInfo = gcc.poll();
		
		
		instCount+=currInfo.getInstructionNumber();		
		
		
		
	}
}
