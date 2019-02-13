import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;

public class PositionWeightMatrix extends Consensus{

	// constants
	private final int PWMatrix = 1;
	private final int PPMatrix = 2;

	// variables
	private double[][] positionWeightMatrix;
	private double[][] positionProbabilityMatrix;
	private int dataLength;

	/**
	 * Constructor
	 *
	 * @param m Takes in the frequency matrix
	 * @param len Takes in the length of the sequence
	 * @param dataLen Takes in the total number of sequences
	 */
	public PositionWeightMatrix(int[][] m, int len, int dataLen) {
		super(len);
		this.matrix = m;
		this.dataLength = dataLen;
		this.positionWeightMatrix = new double[MAX_ROW][this.length];
		this.positionProbabilityMatrix = new double[MAX_ROW][this.length];
		this.buildMatrix(); // self-invoking
	}

	/*
	 * This method build the Position-Weighted and Position Probability matrix
	 */
	private void buildMatrix() {
		for(int row = 0; row < MAX_ROW; row++) {
			for(int i = 0; i < this.length; i++) {
				double odd = (double)this.matrix[row][i] / this.dataLength;
				this.positionWeightMatrix[row][i] =
						this.round(
								//odd
								this.log2(odd/0.25)
								,2);
				this.positionProbabilityMatrix[row][i] = odd;
			}
		}
	}

	/**
	 * Helper method to round double to n places
	 * @author: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	 *
	 * @param value Takes in a double number
	 * @param places Takes in an integer for calculating decimal places
	 * @return a rounded double number to the n decimal places
	 */
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	/**
	 * Helper method to calculate log2 of a number
	 * modified version from: https://www.linuxquestions.org/questions/programming-9/log-base-2-function-in-java-594619/
	 *
	 * @param num a decimal number
	 * @return the log2 value of the input number
	 */
	private double log2(double num){
		double result = (Math.log(num)/Math.log(2));
		if (result == Double.POSITIVE_INFINITY) return 99.0;
		else if(result == Double.NEGATIVE_INFINITY) return -99.0;
		return result;
	}

	/**
	 * This method calculate score of input seq based on PWM scores
	 *
	 * @param seq sequence from the user input
	 * @param type integer indicating the type of matrix, 1 for PWM, 2 for PPM
	 * @return the calculated score of the input sequence
	 */
	public double calcScore(String seq, int type) {
		seq = seq.toUpperCase();
		HashMap<Character,Integer> map = new HashMap<Character,Integer>();
		map.put('A', A_ROW);
		map.put('T', T_ROW);
		map.put('C', C_ROW);
		map.put('G', G_ROW);
		double score = 1.0;
		int currChar;
		
		if(type == this.PWMatrix) {
			for(int pos = 0; pos < seq.length(); pos++) {
				currChar = map.get(seq.charAt(pos));
				if(this.positionWeightMatrix[currChar][pos] == -99) return -99.99;
				score *= this.positionWeightMatrix[currChar][pos];
			}
		}
		else if(type == this.PPMatrix) {
			for(int pos = 0; pos < seq.length(); pos++) {
				currChar = map.get(seq.charAt(pos));
				if(this.positionProbabilityMatrix[currChar][pos] == 0.0) return 0.0;
				score *= this.positionProbabilityMatrix[currChar][pos];
			}
		}
		return score;
	}

	/**
	 * This method prints the matrix into the CLI
	 *
	 * @param type number indicating the type of matrix, 1 for Weighted, 2 for Probability
	 */
	public void printMatrix(int type) {
		if(type == this.PPMatrix) {
			System.out.println("Position-Probability Matrix(%) : ");
		}
		else if(type == this.PWMatrix) {
			System.out.println("Position-Weighted Matrix: ");
		}


		for (int i = 0; i < MAX_ROW; i++) { // max row = 4
			if (i == A_ROW){
				System.out.print("A| ");
			} else if (i == T_ROW){
				System.out.print("T| ");
			} else if (i == C_ROW) {
				System.out.print("C| ");
			} else if (i == G_ROW){
				System.out.print("G| ");
			}

			for (int y = 0; y < this.length; y++) { // length
				double val = 0.0;
				if(type == this.PPMatrix) {
					 val = positionProbabilityMatrix[i][y];
				}
				else if(type == this.PWMatrix) {
					 val = positionWeightMatrix[i][y];
				}

				System.out.print(val +" |");
			}
			System.out.println();
		}
	}

	/**
	 * This method return a string of the matrix
	 *
	 * @param type number indicating the type of matrix, 1 for weighted, 2 for probability
	 * @return a String contains the matrix
	 */
	public String getMatrix(int type) {
		String s ="";
		if(type == this.PPMatrix) {
			s += "Position-Probability Matrix: \n";
		}
		else if(type == this.PWMatrix) {
			s += "Position-Weighted Matrix: \n";
		}


		for (int i = 0; i < MAX_ROW; i++) { // max row = 4
			if (i == A_ROW){
				s += "A| ";
			} else if (i == T_ROW){
				s += "T| ";
			} else if (i == C_ROW) {
				s += "C| ";
			} else if (i == G_ROW){
				s += "G| ";
			}

			for (int y = 0; y < this.length; y++) { // length
				double val = 0.0;
				if(type == this.PPMatrix) {
					 val = positionProbabilityMatrix[i][y];
				}
				else if(type == this.PWMatrix) {
					 val = positionWeightMatrix[i][y];
				}

				s += val +" |";
			}
			s += "\n";
		}
		return s;
	}
}
