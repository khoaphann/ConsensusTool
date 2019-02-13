import java.util.Set;

public interface SeqDataInterface {

	/**
	 * This method insert name and sequence into HashMap.
	 *
	 * @param n   takes in name of where this dna string from
	 * @param seq takes in dna sequence
	 *
	 */
	public void insert(String n, String seq);

	/**
	 * This method delete the key and value of given key. In this case is the name
	 *
	 * @param n takes in the key's name
	 *
	 */
	public void delete(String n);

	/**
	 * This method find a sequence with given name.
	 *
	 * @param n takes in key's name
	 *
	 */
	public String getSequence(String n);

	/**
	 * This method returns all the names in the data.
	 *
	 * @return all keys in the HashMap
	 */
	public Set<String> getKeys();

	/**
	 * This method output the data into CLI
	 */
	public void printData();
	
	/**
	 * This method return the size of dna's length by getting the first dna sequence
	 * and check its length.
	 *
	 * @return size of the dna
	 */
	public int getSequenceSize();
	
	/**
	 * This method return the total sequence in the data structure
	 * 
	 * @return number of total sequences
	 */
	public int getNumberOfSequences();

}