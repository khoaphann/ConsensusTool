public class ConsensusMatrix extends Consensus {

	private SequenceData myData;

	/**
	 * Constructor
	 *
	 * @param d   takes in a HashMap of dna strings
	 * @param len takes in the length of the dna string
	 */
	public ConsensusMatrix(SequenceData d, int len) {
		super(len);
		this.myData = d;
		this.matrix = new int[MAX_ROW][this.length];
		this.buildMatrix();
	}

	/**
	 * 0 1 2 3 4 5 6 .... A T C G
	 *
	 * Method to build frequency matrix
	 */
	private void buildMatrix() {
		for (String item : myData.getKeys()) {

			String s = myData.getSequence(item);
			//System.out.println(item + " " + s);

			for (int i = 0; i < this.length; i++) {
				char letter = s.charAt(i);
				switch (letter) {
				case 'A':
					matrix[A_ROW][i] += 1;
					break;
				case 'T':
					matrix[T_ROW][i] += 1;
					break;
				case 'C':
					matrix[C_ROW][i] += 1;
					break;
				case 'G':
					matrix[G_ROW][i] += 1;
					break;
				case '-':
					break;
				default:
					System.out.println("Error in buildingMaxtrix");
				}
			}
		}
	}

	/**
	 * Method to print the matrix on CLI
	 * 
	 */
	public void printMatrix() {
		System.out.println("Frequency Matrix: ");
		for (int i = 0; i < MAX_ROW; i++) { // max row = 4
			if (i == 0){
				System.out.print("A| ");
			} else if (i == 1){
				System.out.print("T| ");
			} else if (i == 2) {
				System.out.print("C| ");
			} else {
				System.out.print("G| ");
			}

			for (int y = 0; y < this.length; y++) { // length

				int val = matrix[i][y];
				System.out.print(val);
			}
			System.out.println();
		}
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "# of bases per sequence: "+ this.length+"\n";
		s += "Frequency Matrix: "+ System.lineSeparator();
		for (int i = 0; i < MAX_ROW; i++) { // max row = 4
			if (i == 0){
				s += "A| ";
			} else if (i == 1){
				s += "T| ";
			} else if (i == 2) {
				s += "C| ";
			} else {
				s += "G| ";
			}

			for (int y = 0; y < this.length; y++) { // length

				int val = matrix[i][y];
				s += val;
			}
			s += System.lineSeparator();
		}
		return s;
	}

	/**
	 * This method return the length of the sequence
	 */
	public int getLength() {
		return this.length;
	}
	
	/**
	 * This method return the frequency matrix used in this object
	 */
	public int[][] getMatrix() {
		return this.matrix;
	}
}