package encoder;
//size of file/number of symbols it is (total number of characters in the uncompressed file)
//bits/symbol
public class InternalHuffmanNode implements HuffmanNode {
	int count;
	HuffmanNode left;
	HuffmanNode right;
	
	public boolean isFull() {
		if(hasLeft() && hasRight()) {
			if(left.isFull() && right.isFull()) {
				return true;
			}
		} 
		return false;
	}
	
	public boolean hasLeft() {
		return left != null;
	}
	
	public boolean hasRight() {
		return right != null;
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public int symbol()  {
		return -1;
	}

	@Override
	public int height() {
	    int leftH = (left == null) ? 0 : left.height();
	    int rightH = (right == null) ? 0 : right.height();
	    int height = 1+Math.max(leftH, rightH);
	    return height ;
	}
	// insertSymbol() attempts to insert leaf node for given
		// symbol under this node that is length distance
		// away and follows rules for canonical tree construction:
		//  * First try to go left
		//  * If not possible or fails, then try to go right
		//  * If not possible or fails, return false to indicate failure.
		//
		// Returns true if new leaf node was successfully inserted, 
		// false otherwise. Should be able to implement this recursively,
		// reducing length by 1 for each recursion until length is 1 at which
		// point new leaf node with symbol should be created and attached
		// if possible. 
		//
		// This method is only applicable for internal nodes. Calling on
		// a leaf node should be an exception.
	@Override
	public boolean insertSymbol(int lengthRemaining, int symbol) {
		
		if(left == null) {
			if(lengthRemaining==1) {
				left = new LeafHuffmanNode(symbol);
				return true;
			} else {
				InternalHuffmanNode i = new InternalHuffmanNode();
				left = i;
				return left.insertSymbol(lengthRemaining-1, symbol);
			}
		} else if(left != null && !isLeaf()){
			return left.insertSymbol(lengthRemaining-1, symbol);
		} 
		if(right == null) {
			if(lengthRemaining==1) {
				right = new LeafHuffmanNode(symbol);
				return true;
			} else {
				InternalHuffmanNode i = new InternalHuffmanNode();
				right = i;
				return right.insertSymbol(lengthRemaining-1, symbol);
			}
		} else if(right != null && !isLeaf()){
			return right.insertSymbol(lengthRemaining-1, symbol);
		} 
		return false;
		
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return right;
	}
	

}
