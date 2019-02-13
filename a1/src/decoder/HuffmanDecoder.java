package decoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffmanDecoder {

	InputStreamBitSource source;
	TreeMap<Integer, LinkedList<Integer>> counts = new TreeMap<Integer, LinkedList<Integer>>((a,b)-> a.intValue()-b.intValue());
	Map<String, Character> codes = new TreeMap<String, Character>((a,b)-> a.length()-b.length());

	public HuffmanDecoder(String inputFile, String outputFile) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream out = null;
		try {
			out = new PrintStream(outputFile);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(out);
		this.source = new InputStreamBitSource(in);
	}

	public void decode() throws InsufficientBitsLeftException, IOException {
		counts = getLengths();
		codes = getCodes();
		readIn(getSizeOfCrypticText());
	}

	public TreeMap<Integer, LinkedList<Integer>> getLengths() {
		TreeMap<Integer, LinkedList<Integer>> counts = new TreeMap<Integer, LinkedList<Integer>>((a,b)-> a.intValue()-b.intValue());
		for(int i = 0; i < 256; i++) {
			try {
				int nextLength = source.next(8);
				if(counts.containsKey(nextLength)) {
					counts.get(nextLength).add(i);
				} else {
					LinkedList<Integer> associatedIndices = new LinkedList<Integer>();
					associatedIndices.add(i);
					counts.put(nextLength, associatedIndices);
				}
			} catch (InsufficientBitsLeftException | IOException e) {
				e.printStackTrace();
			}
		}

		return counts;
	}

	public Map<String, Character> getCodes() {
		int code = -1;
		String sCode = makeBinOfLength(code, counts.firstKey());
		codes.put(sCode, (char) 0);
		codes = new TreeMap<String, Character>();
		int x = 0;
		for(Integer length : counts.keySet()) {
			x = 0;
			int diff = 0;
			for(Integer index : counts.get(length)) {
				if(length == counts.firstKey() || x != 0){
					code = code+1;
					sCode = makeBinOfLength(code, length);
				} else {
					diff = length-counts.lowerKey(length);
					code = (code+1) << diff;
					sCode = (makeBinOfLength(code, length));

				}
				codes.put(sCode, (char) (int) (index));
				x++;
			}
		}
		return codes;
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

	private int getSizeOfCrypticText() {
		try {
			return source.next(32);
		} catch (InsufficientBitsLeftException | IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	int bitsToInt(List<String> bits) {
		StringBuilder s = new StringBuilder();
		bits.forEach(bit -> {
			s.append(bit);
		});
		return (int) Integer.parseInt(s.toString(),2);
	}

	int bitLengthOfInt(int i) {
		return Integer.toBinaryString(i).length();
	}
	private List<Character> readIn(int encodedTextSize) throws IOException, InsufficientBitsLeftException  {
		List<Character> list = new LinkedList<Character>();
		StringBuilder builder = new StringBuilder();
		String current = builder.toString();

		while(encodedTextSize > 0) {
			current = builder.toString();
			if(codes.containsKey(current)) {
				list.add(codes.get(current));
				encodedTextSize--;
				builder = new StringBuilder();
			} else {
				builder.append(source.next(1));
			}
		}
		StringBuilder s = new StringBuilder();
		list.forEach(c->{
			s.append(c);
		});
		System.out.print(s.toString());
		return list;
	}

}
