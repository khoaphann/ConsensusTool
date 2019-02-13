// to print as desired (support to print [ ] and {}, and N)
class ResidueColumn extends Consensus {
	
	private String cResidueCols;
	private int numberOfSeq; // number of sequences for alignment
	private SequenceData d;
	
	private Boolean isConserved;
	private char conservedLetter;
	private String missedLetters;
	private String differences;

	public ResidueColumn(int len, SequenceData d){
		super(len);
		this.isConserved = false;
		this.conservedLetter = ' ';
		this.missedLetters = "";
		this.differences = "";
		this.numberOfSeq = d.getNumberOfSequences();
		this.residueCols = new ResidueColumn[d.getSequenceSize()];
		this.d = d;
		this.residueCols = residueCols;
	}
	
	private void buildCol() {
		this.cResidueCols = "";
		char residueLetter;
		for (int i = 0; i < MAX_ROW; i++) { // max row = 4
			if (i == 0){
				residueLetter = 'A';
			} else if (i == 1){
				residueLetter = 'T';
			} else if (i == 2) {
				residueLetter = 'C';
			} else {
				residueLetter = 'G';
			}
			for (int y = 0; y < this.length; y++) { // length
				if(residueCols[y] == null) residueCols[y] = new ResidueColumn(this.length,this.d);

				int val = matrix[i][y];
				if(val == numberOfSeq) {
					residueCols[y].setIsConserved();
					residueCols[y].setConservedLetter(residueLetter);
				}
				else if(val > 0) residueCols[y].setDifferences(residueLetter);
				else residueCols[y].setMissedLetters(residueLetter);

				System.out.print(val);
				//-----build cResidueCols String
				ResidueColumn temp = residueCols[i];
				if(temp.isConserved()) cResidueCols += temp.getConservedLetter();
				else if (temp.canBeAnything()) cResidueCols += 'N';
				else if (temp.canBeCurlyBraced()) cResidueCols += ("{" + temp.getMissedLetters() + "}");
				else cResidueCols += ("[" + temp.getDifferences() + "]");
			}
			System.out.println();
			
		}
	}
	
	public void printRCSequence() { System.out.println("RCS:" + cResidueCols); }

	
	public ResidueColumn[] getResidueCols() { return this.residueCols; }

	public void setIsConserved() { isConserved = true; }
	Boolean isConserved() { return isConserved; }

	public void setConservedLetter(char conservedLetter) { this.conservedLetter = conservedLetter; }
	public char getConservedLetter(){ return conservedLetter; }

	 // if there is a different nucleic acid at a position, record here
	public void setDifferences(char diff) { differences += diff; }
	public String getDifferences() { return differences; }

	public Boolean canBeAnything() { return differences.length() == Consensus.MAX_ROW; }
	public Boolean canBeCurlyBraced(){ return differences.length() == Consensus.MAX_ROW - 1; }

	public void setMissedLetters(char missed){ missedLetters += missed; }
	public String getMissedLetters() { return missedLetters; }
}