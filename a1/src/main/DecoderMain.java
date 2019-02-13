package main;

import java.io.IOException;

import decoder.HuffmanDecoder;
import io.InsufficientBitsLeftException;

// can successfully decode a file that has been produced by the encoder with d2 uncommented
public class DecoderMain {
	
	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
	//	HuffmanDecoder d = new HuffmanDecoder("data/compressed.txt", "dataOut/output.txt");
		HuffmanDecoder d2 = new HuffmanDecoder("data/recompressed.txt", "dataOut/decodedFromEncoderCompressed.txt");
	//	d.decode();
		d2.decode();
	}

}
