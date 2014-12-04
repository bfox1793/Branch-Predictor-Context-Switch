import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class TablePredictor extends BranchPredictor{
	
	boolean[] pastValues = null;
	
	Map<Integer, Boolean> mappedValues = new HashMap<Integer, Boolean>();
	
	public TablePredictor (int bitsRemembered){
		super();
		pastValues = new boolean[bitsRemembered];
				
		initialize(bitsRemembered);

		
	}
	
	private void initialize(int bitsRemembered){
		
		for (int i=0; i < bitsRemembered; i++){
			pastValues[i] = false;
		}
		
		int rememberedSizes = (int)Math.pow(2, bitsRemembered);
		
		for (int i=0; i < rememberedSizes; i++){
			mappedValues.put(i, false);
		}
	}

	@Override
	public void branch(BranchInformation currInfo) {
		
		if (currInfo==null) return;
		
		int previousValueIndex = 0;
		
		
		for (int i=0; i < pastValues.length;i++){
			if (pastValues[i]){
				previousValueIndex+=(int)Math.pow(2, i);
			}
		}
		
		
		
		boolean prediction = mappedValues.get(previousValueIndex);
		
		if (prediction==currInfo.isTaken()){
			hits++;
		}
		else {
			misses++;
		}
		
		mappedValues.put(previousValueIndex, currInfo.isTaken());
		
		for (int i=pastValues.length-1; i > 0 ; i--){
			pastValues[i] = pastValues[i-1];
		}
		
		pastValues[0] = currInfo.isTaken();
		
		
	}

}


