
public class BasicPredictor extends BranchPredictor{

	private int upperBound;
	
	private int currentCount;
	
	public BasicPredictor(int numBits){
		super();
		upperBound = (int)Math.pow(2,numBits)-1;
		
		currentCount = upperBound/2;
	}
	@Override
	public void branch(BranchInformation currInfo) {
		boolean prediction = false;
		if (currentCount > upperBound/2){
			prediction = true;
		}
		
		if (prediction==currInfo.isTaken()){
			hits++;
		}
		else {
			misses++;
		}
		
		if (currInfo.isTaken()){
			if (currentCount<upperBound) currentCount++;
		}
		
		else {
			if (currentCount>0) currentCount--;
		}
	}

}
