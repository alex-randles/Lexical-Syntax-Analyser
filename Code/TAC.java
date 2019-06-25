import java.io.*;
import java.util.*;
public class TAC implements CALLanguageVisitor 
{
  //USING SYNTAX DIRECTED DEFINITION AS SEEN IN PREVIOUS EXAM PAPERS
  public static String FileName = "ThreeAddressCode.ir";
  public static String scope = ""; 

  public static Hashtable<String,Integer> function_paramaters = new Hashtable<String, Integer>(); 
  public static int InstructionCount = 1;
  public static int ParameterCount = 1;
  public static int LabelCount = 0; 
  public static int ElseCount = 0; 
  public static String tmp = ""; 
  public static String tmp_id = ""; 
  public static String statement_tmp = ""; 
  public static String stm_tmp = "";
  public static STC SymbolTable;
  
  
  private void CreateNewFile(){
	  File file = new File(FileName);
	if(file.exists()){
	file.delete();
	try {
		file.createNewFile();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	  
  }	  
  private void WriteToFile(String s)  {
try(FileWriter fw = new FileWriter(FileName, true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw))
{
    out.print(s);
    
} catch (IOException e) {
    //exception handling left as an exercise for the reader
}
	}
      
  
  
  
  private void PrintInstruction(String id){
	   WriteToFile(String.format("        %s\n",id));
	   System.out.printf("        %s\n",id);

	  
  }	  
  private void PrintAssignment(String id,String value){
       System.out.printf("        %s = %s\n",id,value);
	   WriteToFile(String.format("        %s = %s\n",id,value));


  }
  
  private String PrintFunction(String id,Integer NumArguments){
	  String return_statement = String.format("        call %s, %d \n",id,NumArguments);  
	  return return_statement;
	  
	  
  }
     private String PrintAssignedFunction(String id,String funcId,Integer NumArguments){
	  String return_statement = String.format("       %s := call %s, %d \n",id,funcId, NumArguments);  
	  return return_statement;
	  
	  
  } 
  private void PrintFunctionReturn(String value){
 	   WriteToFile(String.format("        return %s\n",value));
       System.out.printf("        return %s\n",value);


  }
  
  private void PrintParameter(String id){
 	   WriteToFile(String.format("        %s = getparam %d\n",id,ParameterCount));
       System.out.printf("        %s = getparam %d\n",id,ParameterCount);


  }
  private void PrintLabel(String LabelName){
 	   WriteToFile(String.format("%s:\n",LabelName));
       System.out.printf("%s:\n",LabelName);


  }
  
  private String PrintOperation(SimpleNode node,Object data){
 		  DataType child1Type = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
          DataType child2Type = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
          String value1 = "";
          String value2 = ""; 
          if (child1Type == DataType.Num){
  	            value1 =  (String)((Num)node.jjtGetChild(0)).jjtGetValue();
  		  }
  		  else if(child1Type == DataType.Bool){
			      value1 =  (String)((Bool)node.jjtGetChild(0)).jjtGetValue();
		  } 	
  		  else if (child1Type == DataType.identifier){
			    value1 =  (String)((identifier)node.jjtGetChild(0)).jjtGetValue();
		  } 
		  else if (child1Type == DataType.type){
			      value2 =  (String)((type)node.jjtGetChild(0)).jjtGetValue();
		  } 
		  if (child2Type == DataType.Num){
  		         value2 =  (String)((Num)node.jjtGetChild(1)).jjtGetValue();
  		   }
  		  else if(child2Type == DataType.Bool){
			      value2 =  (String)((Bool)node.jjtGetChild(1)).jjtGetValue();
		  } 		  
 		  else if (child2Type == DataType.identifier){
			      value2 =  (String)((identifier)node.jjtGetChild(1)).jjtGetValue();
		  } 
		  else if (child2Type == DataType.type){
			      value2 =  (String)((type)node.jjtGetChild(1)).jjtGetValue();
		  } 
		  String result = value1 + " " + node.value + " " + value2;
		  //String result = String.format("%s %s %s\n".format(value1,value2,node.value));
		  //System.out.print(result); 
		  //PrintInstruction(result); 
          return result;
  }
 

    private String PrintStatmentOperation(SimpleNode node,Object data){
 		  DataType child1Type = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
          DataType child2Type = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
          String value1 = "";
          String value2 = ""; 
          if (child1Type == DataType.Num){
  	            value1 =  (String)((Num)node.jjtGetChild(0)).jjtGetValue();
  		  }
  		  else if(child1Type == DataType.Bool){
			      value1 =  (String)((Bool)node.jjtGetChild(0)).jjtGetValue();
		  } 	
  		  else if (child1Type == DataType.identifier){
			    value1 =  (String)((identifier)node.jjtGetChild(0)).jjtGetValue();
		  } 
		  else if (child1Type == DataType.type){
			      value2 =  (String)((type)node.jjtGetChild(0)).jjtGetValue();
		  } 
		  if (child2Type == DataType.Num){
  		         value2 =  (String)((Num)node.jjtGetChild(1)).jjtGetValue();
  		   }
  		  else if(child2Type == DataType.Bool){
			      value2 =  (String)((Bool)node.jjtGetChild(1)).jjtGetValue();
		  } 		  
 		  else if (child2Type == DataType.identifier){
			      value2 =  (String)((identifier)node.jjtGetChild(1)).jjtGetValue();
		  } 
		  else if (child2Type == DataType.type){
			      value2 =  (String)((type)node.jjtGetChild(1)).jjtGetValue();
		  } 	  		  
		  String result = value1 + " " + node.value + " " + value2 ;
		  //String result = String.format("%s %s %s\n".format(value1,value2,node.value));
		  //System.out.print(result); 
		  //PrintInstruction(result); 
          return result;
  }
  private void PrintgotoLabel(){
	   WriteToFile(String.format("        goto L%d\n",LabelCount-1)); 
	    System.out.printf("        goto L%d\n",LabelCount-1); 

  }	  
  private void PrintDict(Map<String, String> dict){
	    for (String key : dict.keySet()) {
			System.out.println(key);
			System.out.print((dict.get(key)));
}
	  
  }

  private void PrintHeading(){
	   WriteToFile(String.format("Three Address Code\n")); 
	  // WriteToFile(String.format("%0s\n","_____________________________"));
	  	  System.out.println("\nThree Address Code"); 
	  System.out.println("__________________________\n");
  }	  
  

    public Object visit(SimpleNode node, Object data){
         throw new RuntimeException("Visit SimpleNode");
    }

    public Object visit(Prog node, Object data){
	    System.out.printf("\n**The three address code is stored in: %s in your current working directory.**\n",FileName);
		CreateNewFile();
		PrintHeading(); 				
        
		node.childrenAccept(this, data);

        //createThreeAdressCodeFile();
        //System.out.println(function_paramaters); 
        SymbolTable = (STC) data;
        return DataType.Prog;
    }
    public Object visit(VarDecl node, Object data) {
        node.childrenAccept(this, data);
        return DataType.VarDecl;
    }


    public Object visit(ConstDecl node, Object data) {
		node.childrenAccept(this, data);
        return DataType.ConstDecl;
    }

    public Object visit(ConstAssign node, Object data) {
		node.childrenAccept(this, data);
		return DataType.ConstAssign;
    }

    public Object visit(Func node, Object data) {
		ParameterCount = 1;
		String id =  (String)(((identifier)node.jjtGetChild(1)).jjtGetValue());
		scope = id; 
        PrintLabel(id);
		node.childrenAccept(this, data);
		function_paramaters.put(id,ParameterCount-1); 

        return null;
    }
   
   public Object visit(FuncRet node, Object data) {
	    node.childrenAccept(this, data);
	    String id = ""; 
	   DataType child1Type = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
	   if (child1Type == DataType.identifier){
	     id =  (String)(((identifier)node.jjtGetChild(0)).jjtGetValue());}
        PrintFunctionReturn(id);
		
        return DataType.FuncRet; 

}
    public Object visit(type node, Object data) {
		node.childrenAccept(this, data);
        String val = (String) node.value;
        if (val.equals("boolean")) {
            return DataType.Bool;}
        if (val.equals("integer")){ 
               return DataType.Num;}

        return DataType.TypeUnknown;
    }
    
    public Object visit(ParamList node, Object data) {
		node.childrenAccept(this, data);
        return DataType.ParamList;
    }    

    public Object visit(NempParamList node, Object data) {
		   node.childrenAccept(this, data);
           String paramater = (String)(((identifier)node.jjtGetChild(0)).jjtGetValue());
           PrintParameter(paramater);
           ParameterCount++;
           return DataType.ParamList;

}
    public Object visit(Main node, Object data) {
		PrintLabel("main");
		scope = "main"; 
		node.childrenAccept(this, data);
        return DataType.Main;
    }


    

    public Object visit(If node, Object data){
		SimpleNode condition = (SimpleNode)(node.jjtGetChild(0));
		PrintInstruction("if " + PrintStatmentOperation(condition,data) + " goTo L" + LabelCount); 
		String label_helper =  Integer.toString(LabelCount + 1);
		PrintInstruction("     goTo L" + label_helper);
		PrintLabel("L" + LabelCount);
		LabelCount = LabelCount + 1; 
		node.childrenAccept(this, data);
        return DataType.IfStatement;
		
		
    }		
    public Object visit(While node, Object data){
		SimpleNode condition = (SimpleNode)(node.jjtGetChild(0));
		PrintInstruction("if " + PrintStatmentOperation(condition,data) + " goTo L" + LabelCount); 
		String label_helper =  Integer.toString(LabelCount + 1);
		PrintInstruction("     goTo L" + label_helper);
	    PrintLabel("L" + LabelCount);
	    node.childrenAccept(this, data);
	    if (LabelCount <= 0){
	    PrintInstruction("goTo " + scope);}
      	PrintLabel("L" + label_helper);
      	LabelCount = LabelCount + 2; 
        return DataType.While;
		
		
    }		
    
    
    
    public Object visit(Else node, Object data){

		node.childrenAccept(this, data);
		PrintLabel("L" + LabelCount);

        return DataType.Else;
		
		
    }		
    
    public Object visit(Assign node, Object data) {

		node.childrenAccept(this, data);
	    SimpleNode child1 = (SimpleNode)(node.jjtGetChild(0));
		SimpleNode child2 = (SimpleNode)(node.jjtGetChild(1));
		DataType child2Type = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
	if(child2Type != DataType.ArgList){
		if (child2.jjtGetNumChildren()!=0){
			String assignment = child1.jjtGetValue().toString() + node.value + " " + PrintOperation(child2, data); 
			PrintInstruction(assignment);  
		}
		else{
		PrintInstruction(PrintOperation(node, data));  
	}}
	else{
	    }
		return DataType.Assign;}
   
   
   
    public Object visit(Num node, Object data) {
		
	    node.childrenAccept(this, data);
        return DataType.Num;
    }

    public Object visit(Bool node, Object data) {
	    node.childrenAccept(this, data);
        return DataType.Bool;
    }


    public Object visit(PlusOp node, Object data) {
		  node.childrenAccept(this, data);
          return DataType.PlusOp;
    }


    public Object visit(MinOp node, Object data) {
		node.childrenAccept(this, data);
        return null;
    }

    public Object visit(EqOp node, Object data) {
		node.childrenAccept(this, data);
        return null;
    }

    public Object visit(NotOp node, Object data) {
		node.childrenAccept(this, data);
        return null;
    }

    public Object visit(LtOp node, Object data) {
	    tmp = tmp + PrintOperation(node, data);  
		statement_tmp = statement_tmp + " " + PrintStatmentOperation(node, data) +" goto L" + LabelCount; 
		node.childrenAccept(this, data);
		return null;
    }

    public Object visit(LtEqOp node, Object data) {
		PrintOperation(node, data);
		node.childrenAccept(this, data);
        return null;
    }

    public Object visit(GtOp node, Object data) {
		PrintOperation(node, data);
		node.childrenAccept(this, data);
        return null;
    }

    public Object visit(GtEqOp node, Object data) {
		PrintOperation(node, data);
		node.childrenAccept(this, data);
        return null;
    }
    



    public Object visit(OrOp node, Object data) {
		PrintOperation(node, data);		
        node.childrenAccept(this, data);
        return null;
    
    }

    public Object visit(AndOp node, Object data) {
		PrintOperation(node, data);
		 node.childrenAccept(this, data);
        return null;
    
    }

    public Object visit(ArgList node, Object data) {
		//node.childrenAccept(this, data);
		if (!tmp.equals("")){
		tmp = PrintFunction(tmp,node.jjtGetNumChildren());
		for (int i=0;i<node.jjtGetNumChildren();i++){
			String currentParam = (String)(((identifier)node.jjtGetChild(i)).jjtGetValue());
			String addOn = String.format("        param %s\n",currentParam) ;
			tmp = addOn + tmp ;
		}	
		System.out.print(tmp); 
		WriteToFile(tmp);}
		tmp = ""; 
        return DataType.ArgList;
    }

    public Object visit(identifier node, Object data) {
         node.childrenAccept(this, data);
         String id = node.jjtGetValue().toString();
        if(node.jjtGetNumChildren()>0){
         SimpleNode n1 = (SimpleNode)(node.jjtGetChild(0));
         String child0 = n1.jjtGetValue().toString();
         System.out.println(child0); }
         tmp = id; 
		 //System.out.println(tmp); 
         return DataType.identifier;
    }


}
