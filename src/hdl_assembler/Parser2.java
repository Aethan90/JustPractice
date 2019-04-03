package hdl_assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser2 {
	 public static HashMap<String,Integer> symbols = new HashMap<String, Integer>();
	    public static HashMap<String,String> compTableA = new HashMap<String, String>();
	    public static HashMap<String,String> compTableM = new HashMap<String, String>();
	    public static HashMap<String,String> destTable = new HashMap<String, String>();
	    public static HashMap<String,String> jumpTable = new HashMap<String, String>();

	    static {
	        //put all predefined vars into a HashMap
	        symbols.put("SP",0);
	        symbols.put("LCL",1);
	        symbols.put("ARG",2);
	        symbols.put("THIS",3);
	        symbols.put("THAT",4);
	        symbols.put("R0",0);
	        symbols.put("R1",1);
	        symbols.put("R2",2);
	        symbols.put("R3",3);
	        symbols.put("R4",4);
	        symbols.put("R5",5);
	        symbols.put("R6",6);
	        symbols.put("R7",7);
	        symbols.put("R8",8);
	        symbols.put("R9",9);
	        symbols.put("R10",10);
	        symbols.put("R11",11);
	        symbols.put("R12",12);
	        symbols.put("R13",13);
	        symbols.put("R14",14);
	        symbols.put("R15",15);
	        symbols.put("SCREEN",16384);
	        symbols.put("KBD",24576);

	        //for c instructions comp=dst;jmp
	        //put all comp posibilities with A into a HashMap,a=0
	        compTableA.put("0","101010");
	        compTableA.put("1","111111");
	        compTableA.put("-1","111010");
	        compTableA.put("D","001100");
	        compTableA.put("A","110000");
	        compTableA.put("!D","001101");
	        compTableA.put("!A","110001");
	        compTableA.put("-D","001111");
	        compTableA.put("-A","110011");
	        compTableA.put("D+1","011111");
	        compTableA.put("A+1","110111");
	        compTableA.put("D-1","001110");
	        compTableA.put("A-1","110010");
	        compTableA.put("D+A","000010");
	        compTableA.put("D-A","010011");
	        compTableA.put("A-D","000111");
	        compTableA.put("D&A","000000");
	        compTableA.put("D|A","010101");

	        //put all comp posibilities with M into a HashMap,a=1
	        compTableM.put("M","110000");
	        compTableM.put("!M","110001");
	        compTableM.put("-M","110011");
	        compTableM.put("M+1","110111");
	        compTableM.put("M-1","110010");
	        compTableM.put("D+M","000010");
	        compTableM.put("D-M","010011");
	        compTableM.put("M-D","000111");
	        compTableM.put("D&M","000000");
	        compTableM.put("D|M","010101");

	        //put all dst posibilities into a HashMap
	        destTable.put("","000");
	        destTable.put("M","001");
	        destTable.put("D","010");
	        destTable.put("MD","011");
	        destTable.put("A","100");
	        destTable.put("AM","101");
	        destTable.put("AD","110");
	        destTable.put("AMD","111");

	        //put all jmp posibilities into a HashMap
	        jumpTable.put("","000");
	        jumpTable.put("JGT","001");
	        jumpTable.put("JEQ","010");
	        jumpTable.put("JGE","011");
	        jumpTable.put("JLT","100");
	        jumpTable.put("JNE","101");
	        jumpTable.put("JLE","110");
	        jumpTable.put("JMP","111");
	    }
	    //Helper function to remove Comments from asm files
	    public static String removeComments(String commandLine){

	        int position = commandLine.indexOf("//");

	        if (position != -1){

	            commandLine = commandLine.substring(0, position);

	        }

	        return commandLine;
	    }
	    
	    //Helper function to remove Spaces from asm file
	    public static String removeSpaces(String commandLine){
	        String result = "";

	        if (commandLine.length() != 0){

	            String[] segs = commandLine.split(" ");

	            for (String s: segs){
	                result += s;
	            }
	        }

	        return result;
	    }
	    public static String padLeftZero(String strIn, int len){

	        for (int i = strIn.length(); i < len; i++){
	            strIn = "0" + strIn;
	        }

	        return strIn;
	    }
	    
	    public static boolean asmCheck(File fileIn){

	        String filename = fileIn.getName();
	        int position = filename.lastIndexOf(".");

	        if (position != -1) {

	            String ext = filename.substring(position);

	            if (ext.toLowerCase().equals(".asm")) {
	                return true;
	            }
	        }

	        return false;
	    }
	    
	    public static HashMap<String,Integer> firstRun(String asmfile){

	        HashMap<String,Integer> labels = new HashMap<String, Integer>();
	        Scanner scan = new Scanner(asmfile);
	        String line = "";
	        int pc = 0;
	        Pattern labelFind = Pattern.compile("^\\([^0-9][0-9A-Za-z\\_\\:\\.\\$]+\\)$");//start with ( and end with ) and consist of uppercase
	        Matcher labelMatch =null;

	        while (scan.hasNextLine()){

	            line = scan.nextLine();

	            labelMatch = labelFind.matcher(line);

	            //it is a L instruction
	            if (labelMatch.find()){

	                //get rid of ( and )
	                labels.put(labelMatch.group().substring(1,labelMatch.group().length()-1), pc);

	            }else {

	                pc++;

	            }

	        }

	        return labels;
	    }


	    public static String hackOutput(String asmfile){

	        Scanner scan = new Scanner(asmfile);

	        int addressDec = 0,//value of @value
	            pc = 0,//pc count
	            lineNumber = 0,//record lineNumber for exception
	            startAddress = 16,//address for variable
	            temp = 0,
	            equalsSymbol = -1,//toDetect "="
	            jmpSymbol = -1;//toDetect ";"

	        String line = "",
	               varName = "",// @value
	               value = "",//for A instruction, 0+value
	               //C-Instruction components
	               a = "",
	               dest = "",
	               comp = "",
	               jmp = "",
	               instructions = "";

	        // A user-defined symbol can be any sequence of letters, digits, underscore (_),
	        //dot (.), dollar sign ($), and colon (:) that does not begin with a digit.
	        Pattern variable = Pattern.compile("^[^0-9][0-9A-Za-z\\_\\:\\.\\$]+");
	       
	        //start with ( and end with ) and consist of uppercase
	        //for L instruction

	        Pattern parseLabel = Pattern.compile("^\\([^0-9][0-9A-Za-z\\_\\:\\.\\$]+\\)$");
	       
	        HashMap<String,Integer> labels = firstRun(asmfile);

	        HashMap<String,Integer> symbols = new HashMap<String, Integer>();

	        while (scan.hasNextLine()){

	            lineNumber++;

	            line = scan.nextLine();

	            if (line.charAt(0) == '@'){
	                //A instructions
	                varName = line.substring(1);
	                
	                //if this is jump address for next instruction
	                if (labels.containsKey(varName)){
	                    value = padLeftZero(Integer.toBinaryString(labels.get(varName)),15);
	                }else {

	                    //varName is a value
	                    if (varName.matches("[0-9]+")) {
	                        value = padLeftZero(Integer.toBinaryString(Integer.parseInt(varName)), 15);

	                    } else {
	                        //varName is an user-defined symbol
	                        if (symbols.containsKey(varName)){
	                            value = padLeftZero(Integer.toBinaryString(symbols.get(varName)), 15);

	                        }else {

	                            if (variable.matcher(varName).find()) {
	                                //if map contains this key then get its value and translate into binary
	                                if (symbols.containsKey(varName)) {

	                                    temp = symbols.get(varName);
	                                    value = padLeftZero(Integer.toBinaryString(temp), 15);

	                                } else {
	                                    //if not put it into map and its value is startAddress + map.size()
	                                    addressDec = symbols.size() + startAddress;
	                                    //if use too much memory, give "out of memory" exception
	                                    if (addressDec >= 16384) {
	                                        throw new IllegalStateException("Out of memory!Too many user defined symbols! Line " + lineNumber);

	                                    }

	                                    symbols.put(varName, addressDec);

	                                    value = padLeftZero(Integer.toBinaryString(addressDec), 15);

	                                }

	                            } else {
	                                throw new IllegalStateException("Illegal user-defined symbol! Line " + lineNumber);

	                            }
	                        }

	                    }
	                }

	                instructions += "0" + value + "\n";

	                pc++;
	                // For L instruction, pc count not incremented
	            }else if (parseLabel.matcher(line).find()) {

	                continue;

	            }else {
	                //check for C instructions

	                equalsSymbol = line.indexOf("=");
	                jmpSymbol = line.indexOf(";");
	                dest = "";
	                comp = "";
	                jmp = "";

	                //dest=comp;jump
	                if (equalsSymbol != -1 && jmpSymbol != -1){

	                    dest = line.substring(0,equalsSymbol);
	                    comp = line.substring(equalsSymbol + 1,jmpSymbol);
	                    jmp = line.substring(jmpSymbol + 1);

	                //comp;jump
	                }else if (equalsSymbol == -1 && jmpSymbol != -1){

	                    comp = line.substring(0,jmpSymbol);
	                    jmp = line.substring(jmpSymbol + 1);

	                //dest=comp
	                }else if (equalsSymbol != -1 && jmpSymbol == -1){

	                    dest = line.substring(0,equalsSymbol);
	                    comp = line.substring(equalsSymbol + 1);

	                //dest
	                }else {

	                    dest = line;

	                }

	                if (destTable.containsKey(dest) && (compTableM.containsKey(comp) || compTableA.containsKey(comp)) && jumpTable.containsKey(jmp)){

	                    if (compTableA.containsKey(comp)){

	                        a = "0";
	                        comp = compTableA.get(comp);

	                    }else {

	                        a = "1";
	                        comp = compTableM.get(comp);

	                    }

	                    instructions += "111" + a + comp + destTable.get(dest) + jumpTable.get(jmp) + "\n";

	                }else{

	                    throw new IllegalStateException("Wrong instruction format!Line " + lineNumber);

	                }


	                //System.out.println(dst + " " + comp + " " + jmp);


	            }

	        }
	        //System.out.println(instructions);

	        return instructions;
	    }

	    
	    public static void translation(String fileType){

	        File fileIn = new File(fileType);

	        //if input file is not an .asm file, throw an exception and stop translation
	        if (!asmCheck(fileIn)){
	            throw new IllegalArgumentException("Wrong file format!");
	        }

	        try {
	            Scanner scan = new Scanner(fileIn);
	            String preprocessed = "";

	            while (scan.hasNextLine()){

	                String line = scan.nextLine();

	                line = removeSpaces(removeComments(line));

	                if (line.length() > 0){
	                    preprocessed += line + "\n";
	                }

	            }

	            //get rid of last "\n"
	            preprocessed = preprocessed.trim();

	            //System.out.println(preprocessed);
	            String result = hackOutput(preprocessed);

	            String fileName = fileIn.getName().substring(0,fileIn.getName().indexOf("."));

	            PrintWriter p = new PrintWriter(new File(fileIn.getParentFile().getAbsolutePath() + "/" + fileName + ".hack"));

	            p.print(result);

	            p.close();

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	    	System.out.println("Enter filename, .asm extension: ");
	    	Scanner userInput = new Scanner(System.in);
	    	String userIn = userInput.nextLine();
	    	
	    	  translation(userIn);

	            //System.out.println("Provide asm filename");
	            return;

	        
	        
	      
	        
	    }

}
