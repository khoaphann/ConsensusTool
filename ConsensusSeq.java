public class ConsensusSeq extends Consensus {

	private String consensusSeq;

	/**
	 * Constructor
	 * @param m takes in a matrix array
	 * @param len takes in the length of the dna string
	 */
	public ConsensusSeq(int[][] m, int len){
		super(len);
		this.matrix = m;
		this.buildConsesus(); //invoke the object to build the data
	}

	/**
	 * This method build the Consensus sequence
	 */
	private void buildConsesus(){
		consensusSeq = "";
		boolean equal, equalGroup1, equalGroup2;
		for(int i = 0; i < this.length; i++){
			equal = false; 
			equalGroup1 = false; 
			equalGroup2 = false;
			
			//find the largest value each column
			int max1 = Math.max(this.matrix[A_ROW][i], this.matrix[T_ROW][i]);
			int max2 = Math.max(this.matrix[C_ROW][i], this.matrix[G_ROW][i]);
			int largest = Math.max(max1, max2);
			
			if(this.matrix[A_ROW][i] == this.matrix[T_ROW][i]) equalGroup1 = true;
			if(this.matrix[C_ROW][i] == this.matrix[G_ROW][i]) equalGroup2 = true;
			
			if((equalGroup1 && largest == max1) 
					|| (equalGroup2 && largest == max2)
					|| (max1 == max2) ) equal =true;
			

			//build the string
			if(equal) this.consensusSeq += "?";
			else if(matrix[A_ROW][i] == largest) this.consensusSeq += "A";
			else if(matrix[T_ROW][i] == largest) this.consensusSeq += "T";
			else if(matrix[C_ROW][i] == largest) this.consensusSeq += "C";
			else this.consensusSeq += "G";
			
			//insert space every 10 characters
			if((i+1) % 10 == 0) this.consensusSeq += " ";
;		}
	}
	
	/**
	 * This method returns a String of analyzed consensus sequence
	 * 
	 * @return
	 */
	public String getSequence() { return "Consensus Sequence :\n"+ this.consensusSeq.toLowerCase(); }
	
	/**
	 * This method output the analyzed consensus sequence to CLI
	 */
	public void printSequence() { System.out.println("Consensus Sequence :\n"+ this.consensusSeq); };
}