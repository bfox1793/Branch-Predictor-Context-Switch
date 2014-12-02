
public class BranchInformation {
	
	private boolean myTaken;
	private int myInstructionNumber;
	public BranchInformation(boolean taken, int instructionNumber){
		myTaken = taken;
		myInstructionNumber = instructionNumber;
	}
	
	public boolean isTaken(){
		return myTaken;
	}
	
	public int getInstructionNumber(){
		return myInstructionNumber;
	}
}
