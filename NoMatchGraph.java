package Version_9;
import java.util.*;

public class NoMatchGraph{
  public String printErr=""; 
  private final int MAX_PIECES;
  public Piece pieceList[]; // list of vertices
  public boolean adjMat[][];      // adjacency matrix
  public int nPieces;          // current number of vertices
  public int words=0;
  private Queue<Integer> stackQ = new LinkedList<Integer>();
  
  public NoMatchGraph(int size){               // constructor
    MAX_PIECES= size;
    pieceList = new Piece[MAX_PIECES];
    // adjacency matrix
    adjMat = new boolean[MAX_PIECES][MAX_PIECES];
    nPieces = 0;
    for(int y=0; y<MAX_PIECES; y++)      // set adjacency
      for(int x=0; x<MAX_PIECES; x++)   //    matrix to 0
      adjMat[x][y] = false;        
  }  // end constructor
  
  
  public void addPiece(String lab, int p){
    pieceList[p] = new Piece(lab,p);
    pieceList[p].state= State.UNVISITED;  
    pieceList[p].dfs_distance=0;
    pieceList[p].bfs_distance=-1;
    pieceList[p].ending=0;
    pieceList[p].ancestor=-1;
    pieceList[p].count=0;
    nPieces++;
    //System.out.println(nPieces);
  }
  
  
  public void addEdge(int start, int end){
    adjMat[start][end] = true;
    adjMat[end][start] = true;
  } 
  
  public void relationShips (){
    //System.out.println(g.nPieces);
    for(int i=0;i<nPieces;i++){
      ///System.out.println("am here");
      for(int j=0; j<nPieces; j++){
        int matching=matches(pieceList[i].toString(), pieceList[j].toString());
        if(matching==0){          
            addEdge(i,j);         
        }
          //System.out.println( (pieceList[i]) + " and  " + (pieceList[j]) + " have " + matching + " matches");
      }        
    }
  }    
  
  
  private int matches(String p1, String p2){
    int matching=0;
    int revMatch=0;
    String reverse= new StringBuilder(p2).reverse().toString();
    for(int i=0;i<p2.length();i++){
      if(p1.charAt(i)==p2.charAt(i)){
        matching++;
      }
      if(p1.charAt(i)==reverse.charAt(i)){
        revMatch++;
      }
    }
    if(revMatch<matching){
      //isReverse=true;
      return revMatch;
    }
    return matching;
  }
  
  private boolean amReverse(String p1, String p2){
    int matching=0;
    int revMatch=0;
    String reverse= new StringBuilder(p2).reverse().toString();
    for(int i=0;i<p2.length();i++){
      if(p1.charAt(i)==p2.charAt(i)){
        matching++;
      }
      if(p1.charAt(i)==reverse.charAt(i)){
        revMatch++;
      }
    }
    if(revMatch<matching){
      //isReverse=true;
      return true;
    }
    return false;
  }
  
  public void displayPiece(int v) {
    System.out.println(pieceList[v]);
  }
  
  public void displayAdjMat() {
    for(int j=0;j<nPieces;j++){      
      System.out.print("      "+ pieceList[j].label);           
    }
    System.out.println();
    int i = 0;
    for(boolean[] row: adjMat){
      System.out.print(pieceList[i].label+ "  ");
      for(boolean col:row){
        if(col){
        System.out.print(1+"      ");
        }else{
          System.out.print(0+"      ");
        }
      }
      System.out.println(" "  + pieceList[i].position);
      System.out.println();
      if(i<nPieces-1){
        i++;
      }
    }  
  }
  
  public int myNonMatches(ArrayList<Integer> list, int me){
    int nonMatches=0;
    for(int i=0;i<list.size();i++){
      if(adjMat[me][list.get(i)]==true){        
        //System.out.println(me + " have no match with "+ i );
        nonMatches++;        
      }      
    }
    //System.out.println(list);
    //System.out.println(pieceList[me] + " nonMatches = " + nonMatches);
    return nonMatches;
  }
  
  public int bestNonMatch(ArrayList<Integer> list, int me){
    int gotleastNonMatches=-1; 
    int least=Integer.MAX_VALUE;
    for(int each: list){
      int nonMatches=myNonMatches(list,each);
      //System.out.println(each + " Non Matches = " +nonMatches);
      if(nonMatches<least&&adjMat[me][each]==true&&nonMatches>0){
        least=nonMatches;
        gotleastNonMatches=each;
      }
    }
    return gotleastNonMatches;
  }
  
  //
  public int justBest(ArrayList<Integer> list){
    int mostMatches=-1; 
    int least=Integer.MAX_VALUE;
    for(int each: list){
      int nonMatches=myNonMatches(list,each);
      //System.out.println(nonMatches);
      if(nonMatches<least&&nonMatches>0){
        least=nonMatches;
        mostMatches=each;
      }
    }
    //System.out.println("least = " + least);
    return mostMatches;
  }
  
  public ArrayList<Integer> nonMatches(int size){
    ArrayList<Integer> list=new ArrayList<Integer>();
    ArrayList<Integer> result=new ArrayList<Integer>();
    ArrayList<Integer> emptyList=new ArrayList<Integer>();
    for(int i=0;i<pieceList.length;i++){
      list.add(i);
    }    
    int start= justBest(list);
    do{      
      if(start>=0){      
        list.remove(list.indexOf(start));
        result.add(start);
        size--;
        if(size==0){
          return result;
        }else if(list.isEmpty()){
          return list;
        }
        start= bestNonMatch(list,start);
        //System.out.println("start : " + start);
      }else{
        int last=findLast(list,result.get(result.size()-1));
        if(last!=(-1)&&size==1){
          //System.out.println("here");
          result.add(last);
          return result;
        }else{
          return emptyList;
        }
      }
    }while(size>0);
    return emptyList; 
    //System.out.println(start + " = "+pieceList[temp]+ " I ---->" + pieceList[start]+"<--- am you best non-matching neighbour");
    //list=findNonMatches(list,me,size);
  }
  
  public int findLast(ArrayList<Integer> list, int me){
    int you= -1;
    for(int each: list){
      if(adjMat[me][each]){
        you=each;
      }
    }
    return you;
  }
  
   public ArrayList<Integer> myChildren(int index,ArrayList<Integer> list){
    ArrayList<Integer> children= new ArrayList<Integer> ();
    //System.out.println("here " + index);
    for(int i=0;i<adjMat[index].length;i++){
      boolean each= adjMat[index][i];
      if(each && !(list.contains(i))){
        children.add(i);
        //System.out.println(vertexList[i] + " is a child of: " + vertexList[index]);
      }
    }
    return children;
  }
   
   public ArrayList<Integer> balancedMatches(int length, int size){   
     ArrayList<Integer> temp= new ArrayList<Integer>();
     ArrayList<Integer> list= new ArrayList<Integer>();
    for(int i=0;i<nPieces;i++){
      list.add(i);
    }
    
    for(int each: list){
      ArrayList<Integer> chain= new ArrayList<Integer> ();
       chain.add(each);
      temp= findEnd(each,chain,length, size);
      if(!(temp.isEmpty())){       
        return temp;
      }
    }
    return temp;
  }
   
   
   
   
   public int calculateListMatches(ArrayList<Integer> list){
    int total=0;
    for(int i=0;i<list.size()-1;i++){
      int me= list.get(i);
      int you= list.get(i+1);
      boolean each=adjMat[me][you];
      if(!(each)){
        total++;
      }
    }
    return total;
   }
  

  
  public ArrayList<Integer> findEnd(int start, ArrayList<Integer> wordChain, int length, int size) {
    //System.out.println("here " + start);
    ArrayList<Integer> children= myChildren(start, wordChain);
    //System.out.println(start + ":  Children= " +children);
    ArrayList<Integer> bogus= new ArrayList<Integer>();
    int total=calculateListMatches(wordChain);
    //System.out.println(start +" " + total);
    if(children.isEmpty()){
      //System.out.println(start +"here ");
      if(total==length&&wordChain.size()==size){
        //System.out.println("here ");
        return wordChain;
      }else{
        return bogus;
      }
    }
    
    if(total>length){
      return bogus;
    }
    
    
    if(wordChain.size()==size){       
      if(total==length){          
        return wordChain;
      }else{
        return bogus;
      }
    }   
    
    for(int each: children){
      if(!(wordChain.contains(each))){
        ArrayList<Integer> chain = (ArrayList<Integer>) wordChain.clone();
        chain.add(each);
        bogus=findEnd(each,chain,length,size);
        if(!(bogus.isEmpty())){
          return bogus;
        }               
        //if((wordChain.contains(each))){
        //return bogus;
        // }
      }
    }
    return bogus;
  }
  
  public ArrayList<String> fixMe(ArrayList<Integer> list){
    ArrayList<String> strList = new ArrayList<String>();
    strList.add(pieceList[list.get(0)].toString());
     for(int i=0;i<list.size()-1;i++){
       String me=strList.get(i);
       String neighbour=pieceList[list.get(i+1)].toString();
       String reverse= new StringBuilder(neighbour).reverse().toString();
       if(amReverse(me,neighbour)){
         //System.out.println("reversing " + neighbour);
         strList.add(reverse);
       }else{
         strList.add(neighbour);
       }
     }
     return strList;
   }
  
}





















