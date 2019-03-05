package encoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import io.OutputStreamBitSink;
//to see the decoder decode what this encoder has encoded, go to main/DecoderMain.java
public class HuffEncode {

	public static void main(String[] args) throws IOException, InsufficientBitsLeftException {
		String input_file_name = "data/uncompressed.txt";
		String output_file_name = "data/recompressed.txt";

		FileInputStream fis = new FileInputStream(input_file_name);
		InputStreamBitSource source = new InputStreamBitSource(fis);
		int[] symbol_counts = new int[256];
		int num_symbols =  (int) new File(input_file_name).length();
		int in = 0;
		try {
			while(in  <  num_symbols) {
				int n  = source.next(8);
				symbol_counts[n]++;
				in++;
			}
		} catch (InsufficientBitsLeftException e) {
			System.out.println("insufficient bits to read " + num_symbols);
			e.printStackTrace();
		}
		
		fis.close();

		int[] symbols = new int[256];
		for (int i=0; i<256; i++) {
			symbols[i] = i;
		}
		
		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);

		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i = 0; i < 256; i++) {
				bit_sink.write(encoder.getCode(i).length(), 8);
		}
		
		// Write out total number of symbols as 32 bit value.
		bit_sink.write(num_symbols, 32);

		// Reopen input file.
		fis = new FileInputStream(input_file_name);
		InputStreamBitSource i = new InputStreamBitSource(fis);
	
		int index = 0;
		while(index < num_symbols) {
			try {
				bit_sink.write(encoder.getCode(i.next(8)));
			} catch (InsufficientBitsLeftException e) {
				System.out.println("insufficient bits to write " + index);
				break;
				
			}
			index++;
		}
		
		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
	}
}
