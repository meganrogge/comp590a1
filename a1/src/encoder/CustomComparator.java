package encoder;

import java.util.Comparator;

public class CustomComparator implements Comparator<HuffmanNode> {
	
	@Override
	public int compare(HuffmanNode o1, HuffmanNode o2) {
		if(o1.count() != o2.count()) {
			return o1.count()-o2.count();
		} else {
			return o1.height()-o2.height();
		}
	}

}
