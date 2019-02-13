import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
	
	// constants 
	private static final int ITEM_NAME = 0;
	private static final int ITEM_SEQ = 1;
	private static final int ERROR_CODE_OK = 0;
	private static final int ERROR_CODE_INVALID_SEQ = 100;
	private static final int ERROR_CODE_INVALID_INPUT = 101;
	private static final int ERROR_CODE_PARSING = 102;
	
	// variables
	private String str;
	private boolean isValid;
	private Scanner scan;
	private int errorCode;
	private int errorPos;
	/**
	 * Constructor
	 * 
	 * @param s Take in a string sequence
	 */
	public InputValidator(String s) {
		this.errorCode = 0;
		this.str = s;
		this.isValid = false;
	}
	
	/**
	 * check if the input format is correct and the sequence length is equal
	 * 
	 * @return the error code number and 0 if there's none
	 */
	public int checkInput() {
		String[] words;
		this.scan = new Scanner(this.str);
		
		//check if the sequence contains only A, T, C, G
		final Pattern sequencePattern = Pattern.compile("^[-ATCGatcg]++$");
		
		try {
			
			//get lenth of the first sequence to compare
			int len = scan.nextLine().split(" ")[ITEM_SEQ].length();
			//item counter
			int counter = 1;
			
			//loop to check all the seuqence length and valid sequence characters
			while(scan.hasNextLine()) {
				words = scan.nextLine().split(" ");
				counter++;
				
				//validate sequence characters
				if(!sequencePattern.matcher(words[ITEM_SEQ]).matches()) {
					//System.out.println("Invalid sequence");
					System.out.println("At item number: "+counter);
					this.errorCode = ERROR_CODE_INVALID_SEQ;
					this.errorPos = counter;
					return this.errorCode;
				}
				
				//validate sequence length
				if(words[ITEM_SEQ].length() == len) this.isValid = true;
				
				else {
					//System.out.println("Invalid length");
					System.out.println("At item number: "+counter);
					this.isValid = false;
					this.errorCode = ERROR_CODE_INVALID_INPUT;
					this.errorPos = counter;
					return this.errorCode;
				}
			}
		}catch(Exception e) {
			//System.out.println("Error parsing");
			this.errorCode = ERROR_CODE_PARSING;
			return this.errorCode;
		}
		
		if(this.isValid) {
			System.out.println("Start building data..");
			this.errorCode = ERROR_CODE_OK;
			return this.errorCode;
		}
		//for single entry
		//this.errorCode = ERROR_CODE_INVALID_INPUT;
		return this.errorCode;
	}
	
	/**
	 * This method returns the definition of the error code 
	 * 
	 * @return a string contains the definition of the error code
	 */
	public String getErrorDef() {
		String definition = "";
		
		switch(this.errorCode) 
		{
			case ERROR_CODE_INVALID_SEQ:
				definition = "Invalid character in the sequence!";
				break;
			case ERROR_CODE_INVALID_INPUT:
				definition = "Invalid sequence length!";
				break;
			case ERROR_CODE_PARSING:
				definition = "Parsing error!";
				break;
			default:
				break;
		}
		return definition + "\nAt sequence #"+this.errorPos;
	}
	
	/**
	 * This method returns the current string in this object
	 * 
	 * @return a string contains the sequence
	 */
	public String getString() {return this.str;}
	
	/**
	 * This method returns if the string is valid or not
	 * 
	 * @return true if the string is suitable
	 */
	public boolean checkValid() {return this.isValid;}
}
