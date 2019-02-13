package encoder;

/* SymbolWithCodeLength
 * 
 * Class that encapsulates a symbol value along with the length of the code
 * associated with that symbol. Used to build the canonical huffman tree.
 * Implements Comparable in order to sort first by code length and then by symbol value.
 */

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {
	
	// Instance fields should be declared here.
	private int code_length;
	int value;
	
	// Constructor
	public SymbolWithCodeLength(int value, int code_length) {
		this.code_length = code_length;
		this.value = value;
	}

	// codeLength() should return the code length associated with this symbol
	public int codeLength() {
		// Needs implementation
		return code_length;
	}

	// value() returns the symbol value of the symbol
	public int value() {
		// Needs implementation
		return value;
	}
	
	// compareTo implements the Comparable interface
	// First compare by code length and then by symbol value.
	public int compareTo(SymbolWithCodeLength other) {
		// Needs implementation
		if(code_length != other.code_length) {
			return code_length - other.code_length;
		} else {
			return this.value - other.value;
		}
	}
}
