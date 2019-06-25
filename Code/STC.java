import java.util.*;



public class STC extends Object
{
	public static Hashtable<String, LinkedList<String>> SymbolTable;
	public static Hashtable<String,Integer> FunctionParameterCount;
    public static ArrayList<String> Function_ids;
    public static ArrayList<String> Functions;
    public static ArrayList<String> AllFunctionIds;
    public static Map<String, ArrayList<String>> DeclaredIds; 
    public static Map<String, ArrayList<String>> WrittenVariabless; 


    public STC()
    {
		SymbolTable = new Hashtable<String, LinkedList<String>>(); 
		Function_ids = new ArrayList<String>();  
		Functions = new ArrayList<String>(); 
	    AllFunctionIds = new ArrayList<String>(); 
		FunctionParameterCount = new Hashtable<String,Integer>();
		DeclaredIds = new HashMap<String, ArrayList<String>>(); 
        WrittenVariabless = new HashMap<String, ArrayList<String>>(); 

    }
    
    public static void add(String iname, String iid, String itype,String iscope)
    {
         ArrayList<String> tmpIdArray = new ArrayList<String>(); 
         if (DeclaredIds.containsKey(iscope)){
			 tmpIdArray = DeclaredIds.get(iscope);
			 tmpIdArray.add(iid);
			 DeclaredIds.put(iscope,tmpIdArray); 
		 }
		 else{
			 tmpIdArray.add(iid);
			 DeclaredIds.put(iscope,tmpIdArray); 
		 }
		 ArrayList<String> tmpIdArray2 = new ArrayList<String>(); 
		if(!iname.equals("Parameter")){
		 if (WrittenVariabless.containsKey(iscope)){
			 tmpIdArray2 = WrittenVariabless.get(iscope);
			 tmpIdArray2.add(iid);
			 WrittenVariabless.put(iscope,tmpIdArray2); 
		 }
		 else{
			 tmpIdArray2.add(iid);
			 WrittenVariabless.put(iscope,tmpIdArray2); 
		 }}
		 String SymbolInfo = String.format("%s %s : %s", iname, iid,itype);
		 LinkedList<String> temp_linkedlist = new LinkedList<String>();
		 if (SymbolTable.containsKey(iscope) == true){
			 LinkedList<String> updated = SymbolTable.get(iscope);
			 updated.add(SymbolInfo);
			 SymbolTable.put(iscope,updated);
		 }	 

		 
		 else{
         temp_linkedlist.add(SymbolInfo);
         SymbolTable.put(iscope,temp_linkedlist); 
	    }
	    SymbolInfo = getName(SymbolInfo);
	    if(iname.equals("Function") && !iscope.equals("Global")){
			 Function_ids.add(iid); 
			 Function_ids.add(iid); 
			 AllFunctionIds.add(iid); 


		 }
	    
    }
    
    public void removeEntry(String id, String scope){
         LinkedList<String> temp_linkedlist = (LinkedList<String>)SymbolTable.get(scope);
         for (String variable: temp_linkedlist){
			 String tmp_id = getId(variable);
			 if (id.equals(tmp_id)){
				 temp_linkedlist.remove(variable); 
			 }
	      }		 

		
	}
    
    public void addFunctionParameterCount(String id,Integer count){
		FunctionParameterCount.put(id,count); 
		
		
	}	
    public static Hashtable getSymbolTable(){
		return SymbolTable; 
	}
	
	public static String getName(String variable){
			String[] splitStr1 =variable.split("\\s+");
            String name1 = String.format("%s",splitStr1[0]);
		    return name1;
	}	
	
		public static String getType(String variable){
			String[] splitStr1 = variable.split("\\s+");
            String type1 = String.format("%s",splitStr1[3]);
            return type1; 
		
	}	                

	
		public static String getId(String variable){
			String[] splitStr1 = variable.split("\\s+");
            String id1 = String.format("%s",splitStr1[1]);
		    return id1; 
	}	
	

 

	public static boolean isFunction(String id){
		
		return AllFunctionIds.contains(id);
		
	}
	

    
    public static void printSymbolTable()
    {
		   System.out.println("\nSymbol Table");
		   System.out.println("__________________________\n");
		   Enumeration t = SymbolTable.keys();
           String scope;
           LinkedList<String> temp_linkedlist;
           while (t.hasMoreElements())
           {
                  scope = (String)t.nextElement();
			      temp_linkedlist = (LinkedList<String>)SymbolTable.get(scope);
			      System.out.println(scope);
			      for(String str: temp_linkedlist){
					   
					  System.out.println(str);
					  
					}	  
					System.out.println();
                  }
            }
          
		
	public void DuplicateVariables() 
	{
		   boolean DuplicateVariables = true; 
	       Enumeration t = SymbolTable.keys();
           String scope ;
           List temp_list = new ArrayList(); ;
           LinkedList<String> temp_linkedlist;
           while (t.hasMoreElements())
           {
                  scope = (String)t.nextElement();
                  temp_linkedlist = (LinkedList<String>)SymbolTable.get(scope);
                  List<String> variables = new ArrayList<>();
				  for(String str: temp_linkedlist)
				  {
					        String[] splitStr = str.split("\\s+");
							String name_id = String.format("%s %s",splitStr[0],splitStr[1]);
							variables.add(name_id);
							if (Collections.frequency(variables,name_id)>1){
							   System.out.printf("%s is declared more than once in the %s scope. \n",name_id,scope);
							   variables.removeAll(Collections.singleton(name_id));
							   DuplicateVariables = false;
							}	

				  }
              } if (DuplicateVariables == true && SymbolTable.isEmpty() == false){
				  System.out.println("No identifier is declared more than once in the same scope.");
			  }
	}

	

	

	
	     

						  
					  
					  

}

				

 		
		
		

	
	

	




    
    
    






































   
  
