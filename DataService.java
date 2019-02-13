import java.util.Scanner;

public class DataService {
	
	// object classes
	InputValidator validator; // Validation object
	SequenceData data;		  // Data object
	ConsensusMatrix m;		  // Frequency matrix object
	ConsensusSeq consenSeq;   // Consensus object
	ResidueSeq resiSeq;		  // Residue object
	PositionWeightMatrix pwm; // PWM + PPM object
	Scanner scan;			  // Scanner object to read the data
	
	/**
	 * Constructor for the service object
	 */
	public DataService() {
		data = new SequenceData();
	}
	
	/**
	 * This method check if the current sequence is valid or not
	 * 
	 * @param s Take in the sequence to check
	 * @return true if the sequence is ready to be insert into data structure
	 */
	public boolean isValidData(String s) {
		validator = new InputValidator(s);
		if(validator.checkInput() == 0) return true; //return true if the data is validated
		return false;
	}
	
	/**
	 * This method check what's the input's error definition
	 * 
	 * @return the error's message
	 */
	public String getValidateError() {
		return validator.getErrorDef();
	}
	
	/**
	 * This method insert a string sequence into the data structure
	 * 
	 * @param s Take in the string to be inserted
	 */
	public void insertData(String s) {
	    final int ITEM_NAME = 0;
		final int ITEM_SEQ = 1;
		String[] words;
		scan = new Scanner(s);
		
		while(scan.hasNextLine()) {
			words = scan.nextLine().split(" ");
			data.insert(words[ITEM_NAME], words[ITEM_SEQ]);
		}
	}
	
	/**
	 * This method invoke all the object classes to gather the required result
	 */
	public void generateData() {
		this.m = new ConsensusMatrix(this.data, this.data.getSequenceSize());
		consenSeq = new ConsensusSeq(m.getMatrix(), m.getLength());
		resiSeq = new ResidueSeq(m.getMatrix(),m.length,data);
		pwm = new PositionWeightMatrix(m.getMatrix(),m.getLength(),data.getNumberOfSequences());
	}
	
	/**
	 * This method returns a data structure
	 * 
	 * @return a SequenceData object
	 */
	public SequenceData getData() { return this.data; }
	
	/**
	 * This method returns the Consensus
	 * 
	 * @return a String contains the Consensus Sequence
	 */
	public String getConsesusSeq() { return this.consenSeq.getSequence(); }
	
	/**
	 * This method returns the frequency matrix
	 * 
	 * @return a String contains the Consensus Matrix
	 */
	public String getFreqMatrix() { return this.m.toString(); }
	
	/**
	 * This method returns the residue sequence
	 * 
	 * @return a String contains the Residue Sequence
	 */
	public String getResidueSeq() { return this.resiSeq.getSequence(); }
	
	/**
	 * This method returns the position weighted matrix from the frequency matrix
	 * 
	 * @return a String contains the Position-Weighted Matrix
	 */
	public String getPWMatrix() { return this.pwm.getMatrix(1); }
	
	/**
	 * This method returns the position probability matrix from the frequency matrix
	 * 
	 * @return a String contains the Position-Probability Matrix
	 */
	public String getPPMatrix() { return this.pwm.getMatrix(2); }
	
	/**
	 * This method compute the score of the input sequence from PWMatrix
	 * 
	 * @param s Take in a string sequence to compute
	 * @return a String contains the score of the computed score
	 */
	public String getScorePWMatrix(String s) { return Double.toString(this.pwm.calcScore(s, 1));}
	
	/**
	 * This method compute the score of the input sequence from PPMatrix
	 * 
	 * @param s Take in a string sequence to compute
	 * @return a String contains the score of the computed score
	 */
	public String getScorePPMatrix(String s) { return Double.toString(this.pwm.calcScore(s, 2));}
}
