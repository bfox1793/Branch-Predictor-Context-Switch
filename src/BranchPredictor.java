
public abstract class BranchPredictor {
	
	
	protected int misses;
	protected int hits;
	
	public BranchPredictor(){
		misses = 0;
		hits = 0;
	}

	public double getAccuracy(){
		return (double)hits / ((double)hits+(double)misses);
	}

	public abstract void branch(BranchInformation currInfo);
	
}
