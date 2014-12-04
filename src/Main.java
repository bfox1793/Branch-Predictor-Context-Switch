import java.io.IOException;
import java.util.Queue;


public class Main {
	private static final int PREDICTOR_SIZE = 10;
	private static final int CONTEXT_SWITCH_AMOUNT = 1000;
	private static final String GO_SHORT = "GO_short.txt";
	private static final String ANAGRAM_SHORT = "anagram_short.txt";

	public static void main(String[] args){
		Parser parse = new Parser();
		
		Queue<BranchInformation> anagramInfo = parse.parseAnagram();
		Queue<BranchInformation> anagramShortInfo = null;
		Queue<BranchInformation> gccInfo = parse.parseGcc();
		Queue<BranchInformation> goInfo = null;
		
		try {
			goInfo = parse.parseFile(GO_SHORT);
			anagramShortInfo = parse.parseFile(ANAGRAM_SHORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		basicTest(anagramShortInfo, gccInfo, goInfo);
		anagramInfo = parse.parseAnagram();
		gccInfo = parse.parseGcc();
		Queue<BranchInformation> anagramShortInfo2 = null;
		try {
			goInfo = parse.parseFile(GO_SHORT);
			anagramShortInfo = parse.parseFile(ANAGRAM_SHORT);
			anagramShortInfo2 = parse.parseFile(ANAGRAM_SHORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		contextSwitchTest(anagramShortInfo, anagramShortInfo2);
		
		
	}

	private static void contextSwitchTest(Queue<BranchInformation> anagramInfo,
			Queue<BranchInformation> gccInfo) {
		
		BasicPredictor basicPred = new BasicPredictor(PREDICTOR_SIZE);
		TablePredictor predictor = new TablePredictor(PREDICTOR_SIZE);

		while (anagramInfo.size() > 0 || gccInfo.size() > 0){
			BranchInformation currInfo = getNextInfo(anagramInfo, gccInfo);		
			predictor.branch(currInfo);
			basicPred.branch(currInfo);
		}
		
		System.out.println("Basic Context Switch Result: " + basicPred.getAccuracy());
		System.out.println("Table Context Switch Result: " + predictor.getAccuracy());
	}

	private static void basicTest(
			Queue<BranchInformation> anagramInfo,
			Queue<BranchInformation> gccInfo, Queue<BranchInformation> goInfo) {
		BasicPredictor anagramBasicPredictor = new BasicPredictor(PREDICTOR_SIZE);
		TablePredictor anagramTablePredictor = new TablePredictor(PREDICTOR_SIZE);
		BasicPredictor gccBasicPredictor = new BasicPredictor(PREDICTOR_SIZE);
		TablePredictor gccTablePredictor = new TablePredictor(PREDICTOR_SIZE);
		BasicPredictor goBasicPredictor = new BasicPredictor(PREDICTOR_SIZE);
		TablePredictor goTablePredictor = new TablePredictor(PREDICTOR_SIZE);
		
		while (anagramInfo.size() >0) {
			BranchInformation currInfo = anagramInfo.poll();
			anagramBasicPredictor.branch(currInfo);
			anagramTablePredictor.branch(currInfo);
		}
		
		while(gccInfo.size() > 0){
			BranchInformation currInfo = gccInfo.poll();
			gccBasicPredictor.branch(currInfo);
			gccTablePredictor.branch(currInfo);
		}
		
		while (goInfo.size() > 0){
			BranchInformation currInfo = goInfo.poll();
			goBasicPredictor.branch(currInfo);
			goTablePredictor.branch(currInfo);
		}
		
		System.out.println("Anagram basic predictor accuracy: " + anagramBasicPredictor.getAccuracy());
		System.out.println("Anagram table predictor accuracy: " + anagramTablePredictor.getAccuracy());
		System.out.println("GCC basic predictor accuracy: " + gccBasicPredictor.getAccuracy());
		System.out.println("GCC table predictor accuracy: " + gccTablePredictor.getAccuracy());
		System.out.println("GO basic predictor accuracy: " + goBasicPredictor.getAccuracy());
		System.out.println("GO table predictor accuracy: " + goTablePredictor.getAccuracy());
	}
	
	private static BranchInformation getNextInfo(Queue<BranchInformation> first, Queue<BranchInformation> second){
		if (first.size() == 0){
			return second.poll();
		}
		else if (second.size()==0){
			return first.poll();
		}
		
//		if (first.size() == 0){
//			return null;
//		}
//		else if (second.size()==0){
//			return null;
//		}
		
		BranchInformation currInfo = null;
		BranchInformation firstNext = first.peek();
		BranchInformation secondNext = second.peek();
		int count=1;
		while (currInfo==null){
			if (firstNext.getInstructionNumber()/(count*CONTEXT_SWITCH_AMOUNT)==0){
				currInfo = first.poll();
			}
			else if (secondNext.getInstructionNumber()/(count*CONTEXT_SWITCH_AMOUNT)==0){
				currInfo = second.poll();
			}
			
			count++;
			
		}				
		
		return currInfo;
	}
}
