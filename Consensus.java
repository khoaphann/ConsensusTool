
public class Consensus {

	// constants
	public static final int MAX_ROW = 4; // respresenting 4 motifs 
	public static final int A_ROW = 0;
	public static final int T_ROW = 1;
	public static final int C_ROW = 2;
	public static final int G_ROW = 3;

	// variables
	protected int[][] matrix; // This array store the frequency matrix
	protected int length;	  // This int store the length the sequence
	protected ResidueColumn[] residueCols;

	/**
	 * Constructor
	 *
	 * @param len takes in the length of the dna string
	 */
	public Consensus(int len) {
		this.length = len;
	}
}