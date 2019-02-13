package encoder;

public class LeafHuffmanNode implements HuffmanNode {
	
	int symbol;
	int count;
	
	public LeafHuffmanNode(int symbol) {
		this.symbol = symbol;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		return symbol;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		return false;
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return null;
	}
}
