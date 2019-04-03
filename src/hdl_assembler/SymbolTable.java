//package hdl_assembler;
//
//import java.util.HashMap;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
////import java.util.Map;
//
//public class SymbolTable {
//	
//	SymbolTable(){
//	 HashMap<String,Integer> symbolsTable = new HashMap<String,Integer>(50);
//	
//	
//		//Label Symbols- Predefined for Hack
//		symbolsTable.put("SP",0);
//		symbolsTable.put("LCL",1);
//		symbolsTable.put("ARG",2);
//		symbolsTable.put("THIS",3);
//		symbolsTable.put("THAT",4);
//		symbolsTable.put("R0",0);
//		symbolsTable.put("R1",1);
//		symbolsTable.put("R2",2);
//		symbolsTable.put("R3",3);
//		symbolsTable.put("R4",4);
//		symbolsTable.put("R5",5);
//		symbolsTable.put("R6",6);
//		symbolsTable.put("R7",7);
//		symbolsTable.put("R8",8);
//		symbolsTable.put("R9",9);
//		symbolsTable.put("R10",10);
//		symbolsTable.put("R11",11);
//		symbolsTable.put("R12",12);
//		symbolsTable.put("R13",13);
//		symbolsTable.put("R14",14);
//		symbolsTable.put("R15",15);
//		symbolsTable.put("SCREEN",16384);
//		symbolsTable.put("KBD",24576);
//	
//	
//	
//	}
//	
//
//       
//       /* String line = "";
//        int pc = 0;
//        Pattern p = Pattern.compile("^\\([^0-9][0-9A-Za-z\\_\\:\\.\\$]+\\)$");//start with ( and end with ) and consist of uppercase
//        
//
//        while (scan.hasNextLine()){
//
//            line = scan.nextLine();
//
//            m = p.matcher(line);
//
//            //if find, it is a L instruction
//            if (m.find()){
//
//                //get rid of ( and )
//                labels.put(m.group().substring(1,m.group().length()-1), pc);
//
//            }else {
//
//                pc++;
//
//            }
//
//        }
//
//      */
//  
//	
//	public static void main(String args[]) {
//		String lhs = "D=1";
//		String s = lhs.replaceAll("=.*", "");
//		String line = "";
//		 Pattern p = Pattern.compile("^\\([^0-9][0-9A-Z\\_\\:\\.\\$]+\\)$");//start with ( and end with ) and consist of uppercase
//		 Matcher m =null;
//		 line = "(LABEL_)";
//		 m = p.matcher(line);
//		 if(m.find()) {
//			 System.out.println(m);
//		 }
//		
//	}
//
//}