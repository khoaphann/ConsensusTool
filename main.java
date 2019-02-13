
public class main {

	public static void main(String[] args) {
		/*
		SequenceData data = new SequenceData(); // build data storage
		
		data.insert("human", "agtctgtcgtct");
		data.insert("dog", 	 "agctacgtagcc");
		data.insert("cat",   "agccggtaggaa");
		data.insert("rat",   "agcccgtcgggt");
		data.insert("pig",   "agtctgtcgcgt");

		data.printData();
		
		// creating matrix from data
		ConsensusMatrix m = new ConsensusMatrix(data, data.getSequenceSize());
		System.out.println("Length: "+m.getLength());

		m.printMatrix();

		// build consensus sequence from matrix
		ConsensusSeq seq = new ConsensusSeq(m.getMatrix(), m.getLength());
		seq.printSequence();
		// build residue sequence from matrix
		//ResidueSeq r = new ResidueSeq(m.getMatrix(),m.length,data);
		//r.printResidueSequence();
				
		PositionWeightMatrix pwm = new PositionWeightMatrix(m.getMatrix(),m.getLength(),data.getNumberOfSequences());
		pwm.printMatrix(1);
		pwm.printMatrix(2);
		
		System.out.println(pwm.calcScore("tgtctgtcgcgt"));
		*/
		runProgram();
		
	}
	
	/**
	 * This method invoke the GUI application
	 */
	private static void runProgram() {
		new ApplicationGUI();
	}

}