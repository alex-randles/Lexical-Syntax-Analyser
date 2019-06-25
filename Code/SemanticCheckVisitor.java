import java.util.*;

public class SemanticCheckVisitor implements CALLanguageVisitor {


    
    //INITIALISE ALL VARIABLES TO BE USED 
    public static String scope = "Global";
    public static boolean FunctionsCalled = true; 
    public static Set<String> SemanticChecks = new HashSet<String>();
    public static String tmp = ""; 
    public static Set<String> IncorrectFunctionArguments = new HashSet<String>();
    public static Integer ArgumentCount = 0; 
    public static Integer ParameterCount = 0; 
    public static ArrayList<DataType> IntegerOperations = new ArrayList<DataType>(); 
    public static STC SymbolTable;
	public static Hashtable<String, LinkedList<String>> WrittenVariables = new Hashtable<String, LinkedList<String>>();
	public static Hashtable<String, LinkedList<String>> ReadVariables = new Hashtable<String, LinkedList<String>>();
	public static Hashtable<String, LinkedList<String>> AssignedType = new Hashtable<String, LinkedList<String>>();
	
	
	private String CheckCorrectAssignment(String id,String type,String iscope){
		Hashtable ST = SymbolTable.getSymbolTable();
		String id1 = "";
		String type1 = ""; 
		String return_statement = ""; 
        LinkedList<String> SymbolTableLinkedList;
   if (ST.containsKey(iscope)){
	    SymbolTableLinkedList = (LinkedList<String>)ST.get(iscope);
		if (type.equals("boolean")){
			for(String variable : SymbolTableLinkedList){
			           id1 = SymbolTable.getId(variable);
			           type1 = SymbolTable.getType(variable);
                       if(id1.equals(id)&&!type.equals(type1)){
						  return_statement =  String.format("Scope %s: Variable %s assigned boolean but expected integer.\n", iscope,id);

					   }
                       
             }
		}
	    else if (type.equals("integer")){
			for(String variable : SymbolTableLinkedList){
			           id1 = SymbolTable.getId(variable);
			           type1 = SymbolTable.getType(variable);
                       if(id1.equals(id)&&!type.equals(type1)){
						  return_statement =  String.format("Scope %s: Variable %s assigned integer but expected boolean.\n", iscope,id);

					   }
                       
             }
		}
		else{
			 String id_type = "";
			 String assigned_type = "";
		     for(String variable1 : SymbolTableLinkedList){
        	           id1 = SymbolTable.getId(variable1);
			           type1 = SymbolTable.getType(variable1);
                       if (id1.equals(id)){
						   id_type = type1;}
					  }	 

		    for(String variable2 : SymbolTableLinkedList){
                       id1 = SymbolTable.getId(variable2);
			           type1 = SymbolTable.getType(variable2);
                       if (id1.equals(type)){
						   assigned_type = type1;
					  }	
			if(!assigned_type.equals(id_type)){
				if(SymbolTable.isFunction(type)==false){
				return_statement =  String.format("Scope %s: Variable %s assigned %s but expected %s.\n",iscope,id,assigned_type,id_type);
			    }
			    else{
					return_statement =  String.format("Scope %s: Variable %s assigned %s but expected %s.\n",iscope,id,type,id_type);
				}	
			
			}

			}

		}
	}
		return return_statement; 	
		
   }	
   
   
   
                           //  i       test_fn 
   private String checkDeclared(String id,String iscope){
	        boolean declared = false; 
	        String return_statement = ""; 
	        ArrayList<String> tmp_array = new ArrayList<String>(); 
	        if(SymbolTable.DeclaredIds.containsKey(iscope)){
				tmp_array = SymbolTable.DeclaredIds.get(iscope);
				if(tmp_array.contains(id)){
					declared = true; 
				}
			}
			if(declared == false){
				return_statement = String.format("Scope %s: Identifier %s has not been declared before use.\n",iscope,id); 
			}	
            
            return return_statement;

   }	   
   private static void addWriitenVariable(String id,String iscope){
		 LinkedList<String> temp_linkedlist = new LinkedList<String>();
		 if (WrittenVariables.containsKey(iscope) == true){
			 LinkedList<String> updated = WrittenVariables.get(iscope);
			 updated.add(id);
			 WrittenVariables.put(iscope,updated);
		 }	 

		 
		 else{
         temp_linkedlist.add(id);
         WrittenVariables.put(iscope,temp_linkedlist); 
	    }
	} 
	
	
   public void removeWrittenVariable(String id, String iscope){
	   ArrayList<String> tmp = new ArrayList<String>(); 
	   if(SymbolTable.WrittenVariabless.containsKey(iscope)){
		   tmp = SymbolTable.WrittenVariabless.get(iscope);
		   if (tmp.contains(id)){ 
		   tmp.remove(id); }
	   }
	   
	   
   }
   
   public void checkAllVariablesWritten(Map<String, ArrayList<String>> NotWritten){
	    ArrayList<String> tmp = new ArrayList<String>(); 
	    for (String k: NotWritten.keySet()){
			tmp = NotWritten.get(k);
			for (String variable: tmp){
				if((SymbolTable.isFunction(variable)) == false){
					System.out.printf("Scope %s: Variable %s has been declared but not written to.\n",k, variable);
				}
				
			}
		}  
	   
   }
		
   public Set CheckFunctionCalls(ArrayList<String> Functions){
	     //Set<String> NotCalledFunctions; 
	     Set<String> NotCalledFunctions = new HashSet<String>() ; 
	     tmp = ""; 
	     for(String function: Functions){
			  NotCalledFunctions.add(String.format("Function %s is never called.\n",function));
			 //SemanticChecks = SemanticChecks + tmp;
			 
	    }	 
	   return NotCalledFunctions; 
	   
	   
	}   

    public void CheckNumberArguments(String id, Integer NumArguments, Integer NumParamaters){
		  if (NumArguments != NumParamaters&&SymbolTable.FunctionParameterCount.containsKey(id)){
			String ArgumentCallCheck = String.format("Scope %s: function %s called with %d arguments but expected %d arguments.\n",scope,id,NumArguments,NumParamaters);
		    IncorrectFunctionArguments.add(ArgumentCallCheck); 
		}	 
	}	
		
   public void PrintSet(Set<String> IncorrectCalls){
	   
	   for(String s:  IncorrectCalls){
		   System.out.print(s); 
	   }
	   
	   
	 }  
	 
	public void AllFunctionsCalled(){
		if (SymbolTable.Function_ids.isEmpty()&&!(SymbolTable.AllFunctionIds.isEmpty())){
			System.out.println("All functions were called."); 
		}
	}
		
		
	
    public Object visit(SimpleNode node, Object data) {
        throw new RuntimeException("Visit SimpleNode");

    }
    

    public Object visit(Prog node, Object data) {

		node.childrenAccept(this, data);
		//System.out.println(data.linenumber);
		System.out.println("\nSemantic Analysis");
		System.out.println("__________________________\n");
        SymbolTable = (STC) data;
        SymbolTable.DuplicateVariables() ;
        PrintSet(CheckFunctionCalls(SymbolTable.Function_ids));
        AllFunctionsCalled(); 
        //checkWrittenVariable(WrittenVariables);
        //System.out.println(SymbolTable.DeclaredIds); 
        checkAllVariablesWritten(SymbolTable.WrittenVariabless);
        PrintSet(IncorrectFunctionArguments); 
        PrintSet(SemanticChecks); 
        return DataType.Prog;
    }

    public Object visit(VarDecl node, Object data) {
        node.childrenAccept(this, data);
        return data;
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
	    scope = (String)(((identifier)node.jjtGetChild(1)).jjtGetValue());
		node.childrenAccept(this, data);
        return DataType.Func;
    }

    public Object visit(FuncRet node, Object data) {
		node.childrenAccept(this, data);
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
        return DataType.ParamList;
    }

    public Object visit(Main node, Object data) {
		scope = "Main";
		node.childrenAccept(this, data);
        return DataType.Main;
    }

    public Object visit(Stm node, Object data) {
		node.childrenAccept(this, data);
        return DataType.Stm;

    }
    public Object visit(If node, Object data){
		node.childrenAccept(this, data);
        return DataType.IfStatement;
		
		
    }
        public Object visit(While node, Object data){
		node.childrenAccept(this, data);
        return DataType.While;
		
		
    }	
    
    public Object visit(Else node, Object data){
		node.childrenAccept(this, data);
	   return DataType.Else;
		
		
    }			


    public Object visit(Assign node, Object data) {
	    node.childrenAccept(this, data);
	    String id = (String) ((identifier)node.jjtGetChild(0)).jjtGetValue();
	    addWriitenVariable(id,scope);
	    removeWrittenVariable(id,scope);
	    //System.out.println(id  + scope); 
	    
	    //SemanticChecks.add(checkDeclared(id,scope)); 
		String type = "";
        DataType child1 = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
	    if (child1 ==DataType.Num){
           type = "integer";
	    }
	    else if (child1 ==DataType.Bool){
           type = "boolean";
	    }
         else if (child1 ==DataType.identifier){
	        type = (String) ((identifier)node.jjtGetChild(1)).jjtGetValue();; 

	    }
        SemanticChecks.add(CheckCorrectAssignment(id,type,scope)); 
        return DataType.Assign;
        
    }
    



    public Object visit(Num node, Object data) {
	    node.childrenAccept(this, data);
        return DataType.Num;
    }

    public Object visit(Bool node, Object data) {
	    node.childrenAccept(this, data);
        return DataType.Bool;
    }
    
    private String getValues(SimpleNode node,Object data){
 		  DataType child1Type = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
          DataType child2Type = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
          String value1 = child1Type.toString();
          String value2 = child2Type.toString(); 
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
		  return value1 + " and " + value2; 
	} 
    private DataType checkArithmeticOperations(SimpleNode node, Object data) {
        DataType child1Type = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
        DataType child2Type = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
        String values = getValues(node,data);
        if ((child1Type == DataType.Num) && (child2Type == DataType.Num)) {
			return DataType.Num;
		}
        SemanticChecks.add(String.format("Scope %s: Non integer values used in arithmetic operation (%s) expected integers but got %s\n",scope,node.value,values)); 
        return DataType.TypeUnknown;
    }

    public Object visit(PlusOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkArithmeticOperations(node, data);
        return datatype;
    }

    public Object visit(MinOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkArithmeticOperations(node, data);
        return datatype;
    }


    private DataType checkComparisonOperations(SimpleNode node, Object data) {
		node.childrenAccept(this, data);
        DataType child1DataType = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
        DataType child2DataType = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
        String values = getValues(node,data);
        if ((child1DataType == DataType.Num) && (child2DataType == DataType.Num)) {
			return DataType.Bool;
		}
        SemanticChecks.add(String.format("Scope %s: Non integer values used in Comparison operation (%s) expected integers but got %s\n",scope,node.value,values)); 
        return DataType.TypeUnknown;
    }

    public Object visit(EqOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkComparisonOperations(node, data);
        return datatype;
    }

    public Object visit(NotOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkComparisonOperations(node, data);
        return datatype;
    }

    public Object visit(LtOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkComparisonOperations(node, data);
        return datatype;
    }

    public Object visit(LtEqOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkComparisonOperations(node, data);
        return datatype;
    }
    public Object visit(GtOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkComparisonOperations(node, data);
        return datatype;
    }

    public Object visit(GtEqOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkComparisonOperations(node, data);
        return datatype;
    }
    

    private DataType checkLogicalOperations(SimpleNode node, Object data) {
		node.childrenAccept(this, data);
        DataType child1DataType = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
        DataType child2DataType = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
        String values = getValues(node,data);
        if ((child1DataType == DataType.Bool) && (child2DataType == DataType.Bool)) {
			return DataType.Bool;
		}
        SemanticChecks.add(String.format("Scope %s: Non boolean values used in logical operation (%s) expected integers but got %s\n",scope,node.value,values)); 
        return DataType.TypeUnknown;
    }

    public Object visit(OrOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkLogicalOperations(node, data);
        return datatype;
    
    }

    public Object visit(AndOp node, Object data) {
		node.childrenAccept(this, data);
        DataType datatype = checkLogicalOperations(node, data);
        return datatype;
    
    }

    public Object visit(ArgList node, Object data) {
	    node.childrenAccept(this, data); 
		ArgumentCount = node.jjtGetNumChildren();
	    CheckNumberArguments(tmp, ArgumentCount,ParameterCount);
        return DataType.ArgList;
    }
		
		
   

    public Object visit(identifier node, Object data) {
		 node.childrenAccept(this, data); 	
		 ArgumentCount = 0; 
		 String id = (String)node.value;  
		 if (!(SymbolTable.FunctionParameterCount.containsKey(id))){
			 SemanticChecks.add(checkDeclared(id,scope));}
	     if(SymbolTable.Function_ids.contains(id)){
			 SymbolTable.Function_ids.remove(id); 
			 //System.out.println(SymbolTable.Function_ids); 

	     }
	     if(SymbolTable.FunctionParameterCount.containsKey(id)){
			 ParameterCount = SymbolTable.FunctionParameterCount.get(id) ;
			 tmp = id; 
	     }	
         return DataType.identifier;
    }

}
