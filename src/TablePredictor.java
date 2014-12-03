import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class TablePredictor extends BranchPredictor{
	
	Node pastValues = null;
	
	Map<Integer, Boolean> mappedValues = new HashMap<Integer, Boolean>();
	
	public TablePredictor (int bitsRemembered){
		super();
				
		initialize(bitsRemembered);

		
	}
	
	private void initialize(int bitsRemembered){
		
		for (int i=0; i < bitsRemembered; i++){
			if (pastValues==null){
				pastValues = new Node(false);
			}
			else {
				pastValues.add(false);
			}
		}
		
		int rememberedSizes = (int)Math.pow(2, bitsRemembered);
		
		for (int i=0; i < rememberedSizes; i++){
			mappedValues.put(i, false);
		}
	}

	@Override
	public void branch(BranchInformation currInfo) {
		int previousValueIndex = 0;
		
		
		/*for (int i=pastValues.size()-1; i >=0;i--){
			if (pastValues[i]){
				previousValueIndex+=(int)Math.pow(2, i);
			}
		}*/
		
		
		
		boolean prediction = mappedValues.get(previousValueIndex);
		
		if (prediction==currInfo.isTaken()){
			hits++;
		}
		else {
			misses++;
		}
		
		mappedValues.put(previousValueIndex, currInfo.isTaken());
		
		
	}
	
	public class Node{
		private boolean taken;
		private Node next;
		public Node(boolean isTaken){
			taken = isTaken;
			next = null;
		}
		
		public boolean isTaken(){
			return taken;
		}
		
		public void add(boolean taken){
			Node nextNode = new Node(taken);
			this.next = nextNode;
		}
		
	}

}


