import java.util.ArrayList;
import java.util.List;

public class ResidueSeq extends Consensus {
	
	private String residueSequence;
	private int dataSize;

	/**
	 * Constructor
	 * 
	 * @param m Takes in the frequency matrix
	 * @param len Takes in the length of the sequence
	 * @param d Takes in the data 
	 */
	public ResidueSeq(int[][] m, int len, SequenceData d) {
		super(len);
		this.matrix = m;
		this.residueSequence = "";
		this.dataSize = d.getNumberOfSequences();
		this.buildSequence(); // self-invoking
	}
	
	/**
	 * This method invoke the class to build the residue sequence
	 */
	private void buildSequence() {
		List<Integer> noneZeroList;
		for(int i = 0; i < this.length;i++) {
			
			// check if a motif is always found at current position
			// when three motifs are 0
			if(this.matrix[A_ROW][i] == this.dataSize) this.residueSequence += "A ";
			else if(this.matrix[T_ROW][i] == this.dataSize) this.residueSequence += "T ";
			else if(this.matrix[C_ROW][i] == this.dataSize) this.residueSequence += "C ";
			else if(this.matrix[G_ROW][i] == this.dataSize) this.residueSequence += "G ";
			
			noneZeroList = new ArrayList<Integer>();
			for(int y = 0; y < MAX_ROW; y++) {
				if(this.matrix[y][i] != 0) noneZeroList.add(y);
			}
			
			// when two motifs are 0
			if(noneZeroList.size() == 2) {
				this.residueSequence += "[";
				for(Integer item : noneZeroList) {
					if(item == A_ROW) this.residueSequence += "A";
					if(item == T_ROW) this.residueSequence += "T";
					if(item == C_ROW) this.residueSequence += "C";
					if(item == G_ROW) this.residueSequence += "G";	
				}
				this.residueSequence += "] ";
			}
			
			//when only one motif is 0
			else if(noneZeroList.size() == 3) {
				this.residueSequence += "{";
				if(!noneZeroList.contains(A_ROW)) this.residueSequence += "A";
				else if(!noneZeroList.contains(T_ROW)) this.residueSequence += "T";
				else if(!noneZeroList.contains(C_ROW)) this.residueSequence += "C";
				else if(!noneZeroList.contains(G_ROW)) this.residueSequence += "G";
				this.residueSequence += "} ";
			}
			
			else if(noneZeroList.size() == 4) {
				this.residueSequence += "N ";
			}
		}
	}
	
	/**
	 * This method output the residue sequence to the CLI
	 */
	public void printResidueSequence() {
		System.out.println("Residue Sequence:");
		System.out.println(this.residueSequence);
		System.out.println("Notations: \n"
				+ "A means A always found in that position \n"
				+ "[AT] means either A or T \n"
				+ "{A} any base except A \n"
				+ "N for any bases");
	}
	
	/**
	 * This method return the residue sequence and the notations
	 * 
	 * @return a String contains the residue analysis
	 */
	public String getSequence() {
		String s = "";
		s += "Residue Sequence: \n";
		s += this.residueSequence;
		s += "\n\nNotations: \n"
				+ "A means A always found in that position \n"
				+ "[AT] means either A or T \n"
				+ "{A} any base except A \n"
				+ "N for any bases";
		
		return s;
	}
}
