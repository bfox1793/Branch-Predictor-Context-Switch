import java.util.Queue;


public class Main {
	private static final int CONTEXT_SWITCH_AMOUNT = 1;

	public static void main(String[] args){
		Parser parse = new Parser();
		
		Queue<BranchInformation> anagramInfo = parse.parseAnagram();
		Queue<BranchInformation> gccInfo = parse.parseGcc();
		
		basicTest(anagramInfo, gccInfo);
		/*anagramInfo = parse.parseGcc();
		gccInfo = parse.parseGcc();
		contextSwitchTest(anagramInfo, gccInfo);*/
		
		
		
		
	}

	private static void contextSwitchTest(Queue<BranchInformation> anagramInfo,
			Queue<BranchInformation> gccInfo) {
		
		BasicPredictor predictor = new BasicPredictor(8);

		while (anagramInfo.size() > 0 || gccInfo.size() > 0){
			BranchInformation currInfo = getNextInfo(anagramInfo, gccInfo);			
			predictor.branch(currInfo);
		}
		
		System.out.println("Context Switch Result: " + predictor.getAccuracy());
	}

	private static void basicTest(
			Queue<BranchInformation> anagramInfo,
			Queue<BranchInformation> gccInfo) {
		BasicPredictor anagramBasicPredictor = new BasicPredictor(2);
		TablePredictor anagramTablePredictor = new TablePredictor(2);
		BasicPredictor gccBasicPredictor = new BasicPredictor(2);
		TablePredictor gccTablePredictor = new TablePredictor(2);
		
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
		
		System.out.println("Anagram basic predictor accuracy: " + anagramBasicPredictor.getAccuracy());
		System.out.println("Anagram table predictor accuracy: " + anagramTablePredictor.getAccuracy());
		System.out.println("GCC basic predictor accuracy: " + gccBasicPredictor.getAccuracy());
		System.out.println("GCC table predictor accuracy: " + gccTablePredictor.getAccuracy());
	}
	
	private static BranchInformation getNextInfo(Queue<BranchInformation> first, Queue<BranchInformation> second){
		if (first.size() == 0){
			return second.poll();
		}
		else if (second.size()==0){
			return first.poll();
		}
		
		BranchInformation currInfo = null;
		BranchInformation anagramNext = first.peek();
		BranchInformation gccNext = second.peek();
		int count=1;
		while (currInfo==null){
			if (anagramNext.getInstructionNumber()/(count*CONTEXT_SWITCH_AMOUNT)==0){
				currInfo = first.poll();
			}
			else if (gccNext.getInstructionNumber()/(count*CONTEXT_SWITCH_AMOUNT)==0){
				currInfo = second.poll();
			}
			
			count++;
			
		}				
		
		return currInfo;
	}
}
