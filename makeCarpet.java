package Version_9;
import java.util.*;

public class makeCarpet{  
  public static void main(String[] args) {        
    ArrayList<String> pieces = new ArrayList<String>();
    int n = Integer.parseInt(args[0]);
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      String strip= in.nextLine();
      if(!(pieces.isEmpty())){        
        if(strip.length()!=pieces.get(0).length()){
          System.out.println("All carpet strips must be of the same size");
        }else{
          reverseIt(strip, pieces);
        }
      }else{
        reverseIt(strip, pieces);
      }          
      //pieces.add();    
    }
    
    if(args[1].equals("-n")){
      makeNoMatchCarpet(n, pieces);
    }else if(args[1].equals("-m")){     
      makeMaxMatchCarpet(n, pieces);
    }else if(args[1].equals("-b")){
      makeBalancedCarpet(n, pieces);
    }else{
      throw new IllegalArgumentException("Bad argument");
    }
  }
  
  public static void makeMaxMatchCarpet(int n, ArrayList<String> pieces){
    if(n<=pieces.size()){
      maxMatchGraph g= new maxMatchGraph(pieces.size());
      g.printErr+="Max Matches: \n\n";
      Collections.sort(pieces);
      //System.out.println(pieces + " length: " + pieces.size());
      for(int i=0;i<pieces.size();i++){
        g.addPiece(pieces.get(i).toString(),i);
      }
      for(Piece each: g.pieceList){
        g.printErr+=each +" ";
      }
      g.printErr+="\n\n";
      g.relationShips ();
      //g.displayAdjMat();
      ArrayList<Integer> list = new ArrayList<Integer>();
      list= g.maxMatch(n);
      
      System.out.println("\nMax Matches for carpet length " + list.size() +" = " + g.calculateListMatches(list));
      System.out.println();
      ArrayList<String> strList = new ArrayList<String>();
          strList=g.fixMe(list);
          for(String each:strList){
            System.out.println(each);
       }   
      //System.out.println(g.pieceList[0] +"\n"+g.pieceList[1] +"\n"+g.pieceList[2] +"\nMatches : " + g.calculateListMatches(list));   
      //System.out.print(g.printErr);
    }else{
      System.out.println("Carpet size specified is greater than avialable stock");
    }
  }
  
  public static void makeNoMatchCarpet(int n, ArrayList<String> pieces){
    if(n<=pieces.size()){
      System.out.println("\nMake Non Matching Carpet of Size " + n+"....\n");  
      NoMatchGraph g= new NoMatchGraph(pieces.size());
      g.printErr+="No Matches: \n\n";
      Collections.sort(pieces);
      //System.out.println(pieces + " length: " + pieces.size());
      for(int i=0;i<pieces.size();i++){
        g.addPiece(pieces.get(i).toString(),i);
      }
      for(Piece each: g.pieceList){
        g.printErr+=each +" ";
      }
      
      g.printErr+="\n\n";
      g.relationShips ();
      //g.displayAdjMat();
      ArrayList<Integer> list = new ArrayList<Integer>();
      list=g.nonMatches(n);
      //System.out.println(list);
      if(list.size()==n){
        ArrayList<String> strList = new ArrayList<String>();
        strList=g.fixMe(list);
        for(String each:strList){
          System.out.println(each);
        }      
      }else{ 
        list= g.balancedMatches(0,n);
        //System.out.println(balList);
        if(!(list.isEmpty())){
          ArrayList<String> strList = new ArrayList<String>();
          strList=g.fixMe(list);
          for(String each:strList){
            System.out.println(each);
          }        
        }else{
          System.out.println("Non Matching Carpet of Size " + n+ " is not possible");
        }
      }    
        System.out.println();      
      }else{
        System.out.println("Carpet size specified is greater than avialable stock");
      }
  }
  
  public static void makeBalancedCarpet(int n, ArrayList<String> pieces){
    if(n<=pieces.size()){
      System.out.println("\nMake a Balanced Carpet....\n");
      balMatchGraph g= new balMatchGraph(pieces.size());
      g.printErr+="Max Matches: \n\n";
      Collections.sort(pieces);
      //System.out.println(pieces + " length: " + pieces.size());
      for(int i=0;i<pieces.size();i++){
        g.addPiece(pieces.get(i).toString(),i);
      }
      for(Piece each: g.pieceList){
        g.printErr+=each +" ";
      }
      g.printErr+="\n\n";
      g.relationShips ();
      //ArrayList<String> list= g.randomList(n);
      //System.out.println(list.size() + ": List---> =" + list);
      ArrayList<String> list=new ArrayList<String> ();
      list = g.findBalance(n);
      System.out.println();
      System.out.println();
      if(list.size()==n){          
          System.out.println("Carpet size: " + n + "\nBalance: " + g.calculateBal(list)+"\n");
          for(String each:list){
            System.out.println(each);
          }
      }else{
        System.out.println("Balance not possible: " + list);
      }
      //int i=0;
      //while(true){
        //System.out.println(i);
        //i++;
      //}
      
      
      //g.displayAdjMat();
//      int stripSize=pieces.get(0).length();
//      //System.out.println("stripSize: " + stripSize);
//      int maximum= (n-1)*stripSize;
//      //System.out.println("maximum: " + maximum);
//      int balMatch;
//      if(maximum%2==0){
//        balMatch=maximum/2;
//      }else{
//        balMatch=maximum/2;
//        balMatch++;
//      }
////      ArrayList<Integer> balList= g.balancedMatches(balMatch,n);
//      //System.out.println(balList);
//      if(!(balList.isEmpty())){
//        ArrayList<String> strList = new ArrayList<String>();
//          strList=g.fixMe(balList);
//          for(String each:strList){
//            System.out.println(each);
//        }       
//        System.out.println("\nNumber of Matches in this carpet= " + g.calculateListMatches(balList));
//        System.out.println("Number of Matches expected for balance= " + balMatch);
//      }else{
//        System.out.println("\nBalanced Carpet of size " + n+" is not possible");
//      }
    }else{
      System.out.println("Carpet size specified is greater than avialable stock");
    }
  }
  
  private static ArrayList<String> reverseIt(String strip, ArrayList<String> list){        
    String reverse= new StringBuilder(strip).reverse().toString();
    if(list.isEmpty()){
      list.add(strip);
    }else if(list.contains(reverse)){
      list.add(reverse);
    }else{
      list.add(strip);
    }
    return list;
  }  
  
}