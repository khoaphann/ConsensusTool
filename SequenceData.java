
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SequenceData implements SeqDataInterface {

	private HashMap<String, String> map;;

	/**
	 * Constructor
	 */
	public SequenceData() {
		this.map = new HashMap<>();
	}

	/**
	 * This method insert name and sequence into HashMap.
	 *
	 * @param n   takes in name of where this dna string from
	 * @param seq takes in dna sequence
	 *
	 */
	public void insert(String n, String seq) {
		seq = seq.toUpperCase();
		this.map.put(n, seq);
	}

	/**
	 * This method delete the key and value of given key. In this case is the name
	 *
	 * @param n takes in the key's name
	 *
	 */
	public void delete(String n) {
		if (this.map.containsKey(n))
			this.map.remove(n);
		else
			System.out.println("ERROR DELETE");
	}

	/**
	 * This method find a sequence with given name.
	 *
	 * @param n takes in key's name
	 *
	 */
	public String getSequence(String n) {
		if (this.map.containsKey(n))
			return this.map.get(n);
		else
			return "ERROR GETSEQ";
	}

	/**
	 * This method returns all the names in the data.
	 *
	 * @return all keys in the HashMap
	 */
	public Set<String> getKeys() {
		return this.map.keySet();
	}

	/**
	 * This method output the data into CLI
	 */
	public void printData() {
		if (this.map.isEmpty())
			System.out.println("Data is empty");
		else {
			System.out.println("Data Set:");
			for(String item: this.map.keySet()){
				String s = this.map.get(item);
			    System.out.println(item + "\t " + s);
			}
		}
	}

	/**
	 * This method return the size of dna's length by getting the first dna sequence
	 * and check its length.
	 *
	 * @return size of the dna
	 */
	public int getSequenceSize() {
		Map.Entry<String, String> entry = this.map.entrySet().iterator().next(); // get first entry
		return entry.getValue().length();
	}

	/**
	 * This method return the total sequence in the data structure
	 * 
	 * @return number of total sequences
	 */
	public int getNumberOfSequences(){
		return this.map.size();
	}
}