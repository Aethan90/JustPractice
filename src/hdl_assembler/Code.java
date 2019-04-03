//package hdl_assembler;
//
//import java.util.HashMap;
//
//public class Code {
//	
//	public static HashMap<String,String> destTable = new HashMap<String,String>(50);
//	public static HashMap<String,String> jumpTable = new HashMap<String,String>(50);
//	public static HashMap<String,String> compTableA = new HashMap<String,String>(50);
//	public static HashMap<String,String> compTableM = new HashMap<String,String>(50);
//	
//	static {
//		
//	//Destination Symbols with equivalent binary
//	destTable.put("","000");
//	destTable.put("M","001");
//	destTable.put("D","010");
//	destTable.put("MD","011");
//	destTable.put("A","100");
//	destTable.put("AM","101");
//	destTable.put("AD","110");
//	destTable.put("AMD","111");
//	
//	//Jump Symbols and the equivalent binary
//	jumpTable.put("","000");
//	jumpTable.put("JGT","001");
//	jumpTable.put("JEQ","010");
//	jumpTable.put("JGE","011");
//	jumpTable.put("JLT","100");
//	jumpTable.put("JNE","101");
//	jumpTable.put("JLE","110");
//	jumpTable.put("JMP","111");
//	
//	//computation table for a = 0
//	compTableA.put("0","101010");
//	compTableA.put("1","111111");
//	compTableA.put("-1","111010");
//	compTableA.put("D","001100");
//	compTableA.put("A","110000");
//	compTableA.put("!D","001101");
//	compTableA.put("!A","110001");
//	compTableA.put("-D","001111");
//	compTableA.put("-A","110011");
//	compTableA.put("D+1","011111");
//	compTableA.put("A+1","110111");
//	compTableA.put("D-1","001110");
//	compTableA.put("A-1","110010");
//	compTableA.put("D+A","000010");
//	compTableA.put("D-A","010011");
//	compTableA.put("A-D","000111");
//	compTableA.put("D&A","000000");
//	compTableA.put("D|A","010101");
//	
//	//computation table for a = 1; M
//	compTableM.put("M","110000");
//	compTableM.put("!M","110001");
//	compTableM.put("-M","110011");
//	compTableM.put("M+1","110111");
//	compTableM.put("M-1","110010");
//	compTableM.put("D+M","000010");
//	compTableM.put("D-M","010011");
//	compTableM.put("M-D","000111");
//	compTableM.put("D&M","000000");
//	compTableM.put("D|M","010101");
//
//	}
//}
