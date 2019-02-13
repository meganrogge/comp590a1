package encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import io.OutputStreamBitSink;

public class HuffmanEncoder {
	private TreeMap<Integer, List<Entry<Integer, String>>> counts = new TreeMap<>();
	TreeMap<Integer, String> codes = new TreeMap<Integer, String>();
	List<Integer> lengths = new ArrayList<Integer>();

	public HuffmanEncoder(int[] symbols, int[] symbol_counts) {
		assert symbols.length == symbol_counts.length;

		List<HuffmanNode> nodeList = new ArrayList<HuffmanNode>();
		List<HuffmanNode> leaves = new ArrayList<HuffmanNode>();

		for(int i = 0; i < symbols.length; i++) {
			LeafHuffmanNode l = new LeafHuffmanNode(symbols[i]);
			l.count = symbol_counts[i];
			nodeList.add(l);
		}

		nodeList.sort(null);
		leaves = new ArrayList<HuffmanNode>(nodeList);

		while(nodeList.size() > 1) {

			HuffmanNode one =  nodeList.remove(0);
			HuffmanNode two = nodeList.remove(0);

			InternalHuffmanNode internal = new InternalHuffmanNode();
			internal.count = one.count()+two.count();
			internal.left = one;
			internal.right = two;

			nodeList.add(internal);

			nodeList.sort(null);
		}

		Map<Integer, String> cmap = new HashMap<Integer, String>();

		for(int index = 0; index < symbols.length; index++) {
			traverse(cmap, nodeList.get(0), "", index);
		}

		for(int index = 0; index < leaves.size(); index++) {
			int depth = getDepth(nodeList.get(0), leaves.get(index).symbol(), 1);
			lengths.add(depth);
			Collections.sort(lengths);
		}

		Set<Integer> lengthKey = new HashSet<Integer>(lengths);
		for(Integer length : lengthKey) {
			counts.put(length, cmap.entrySet().stream().filter(c->c.getValue().length()==length).collect(Collectors.toCollection(ArrayList::new)));
		}

		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();
		Collections.reverse(leaves);

		for(int x = 0; x < leaves.size(); x++) {
			sym_with_length.add(new SymbolWithCodeLength(leaves.get(x).symbol(), cmap.get(leaves.get(x).symbol()).length()));
			sym_with_length.sort(null);
		}

		int code = -1;
		int length =  sym_with_length.get(0).codeLength();
		int prevLength =  sym_with_length.get(0).codeLength();
		String sCode = makeBinOfLength(code, length);

		for(int i = 0; i < sym_with_length.size(); i++) {
			length =  sym_with_length.get(i).codeLength();
			if(length == sym_with_length.get(0).codeLength() || (prevLength==length)) {
				code = code+1;
				sCode = makeBinOfLength(code, length);
			} else {
				int diff = length - prevLength;
				code = (code+1) << diff;
				sCode = (makeBinOfLength(code, length));
			}
			prevLength = length;
			codes.put(sym_with_length.get(i).value, sCode);
		}

	}

	private String makeBinOfLength(int num, Integer length) {

		StringBuilder s = new StringBuilder();
		String bin = Integer.toBinaryString(num);


		for(int i = bin.length(); i < length; i++) {
			s.append("0");
		}
		s.append(bin);
		return s.toString();
	}

	public void traverse(Map<Integer, String> map, HuffmanNode node, String binString, int i) {
		if(node == null) return;
		if(node.isLeaf() && node.symbol()==i) {
			map.put(i, binString);
			return;
		}if(!node.isLeaf()) {
			traverse(map, node.right(), binString+"1", i);
			traverse(map, node.left(), binString+"0", i);
		}

	}

	private int getDepth(HuffmanNode root, int i, int depth) {
		if(root == null) return 0;
		if(root.isLeaf()) {
			if(i==root.symbol()) {
				return depth;
			}
		}  
		int d = getDepth(root.left(), i, depth + 1); 
		if (d != 0) {
			return d; 
		}
		d = getDepth(root.right(), i, depth + 1); 
		return d; 

	}

	public String getCode(int symbol) {
		return codes.get(symbol);
	}

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(codes.get(symbol));
	}

	public List<Integer> getLengths() {
		return lengths;
	}

}
