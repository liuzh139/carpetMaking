package Version_9;
import java.util.*;

public class maxMatchGraph{
  public String printErr="";
  private final int MAX_PIECES;
  public Piece pieceList[]; // list of vertices
  public int adjMat[][];      // adjacency matrix
  public int nPieces;          // current number of vertices
  public int words=0;
  private Queue<Integer> stackQ = new LinkedList<Integer>();
  
  public maxMatchGraph(int size){               // constructor
    MAX_PIECES= size;
    pieceList = new Piece[MAX_PIECES];
    // adjacency matrix
    adjMat = new int[MAX_PIECES][MAX_PIECES];
    nPieces = 0;
    for(int y=0; y<MAX_PIECES; y++)      // set adjacency
      for(int x=0; x<MAX_PIECES; x++)   //    matrix to 0
      adjMat[x][y] = 0;        
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
    if(revMatch>matching){
      //isReverse=true;
      return true;
    }
    return false;
  }
  
  public void addEdge(int start, int end, int matches){
    adjMat[start][end] = matches;
    adjMat[end][start] = matches;
  } 
  
  public void relationShips (){
    //System.out.println(g.nPieces);
    for(int i=0;i<nPieces;i++){
      ///System.out.println("am here");
      for(int j=0; j<nPieces; j++){
        int matching=matches(pieceList[i].toString(), pieceList[j].toString());
        if(matching>0 && (adjMat[i][j])==0 && i!=j){          
          addEdge(i,j,matching);
          //System.out.println( (pieceList[i]) + " and  " + (pieceList[j]) + " have " + matching + " matches");
        }        
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
    if(revMatch>matching){
      //isReverse=true;
      return revMatch;
    }
    return matching;
  }
  
  /*public void displayRelationships() {
   for(int r=0;r<nPieces;r++){
   System.out.print(pieceList[r].label + ": ");
   for(int c=0;c<nPieces;c++){
   if(adjMat[r][c]==true){
   System.out.print(pieceList[c].label + ", ");
   }
   }
   System.out.println();
   }
   }*/
  
  public void displayPiece(int v) {
    System.out.println(pieceList[v]);
  }
  
  public void displayAdjMat() {
    for(int j=0;j<nPieces;j++){      
      System.out.print("    "+ pieceList[j].label);           
    }
    System.out.println();
    int i = 0;
    for(int[] row: adjMat){
      System.out.print(pieceList[i].label+ "  ");
      for(int col:row){
        System.out.print(col+"      ");         
      }
      System.out.println();
      System.out.println();
      if(i<nPieces-1){
        i++;
      }
    }  
  }
  
  public ArrayList<Integer> maxMatch(int size){
    int max=0;
    ArrayList<Integer> maxPiece= new ArrayList<Integer>();
    for(int i=0;i<pieceList.length;i++){
       ArrayList<Integer> matchTotalList= new ArrayList<Integer>();
       matchTotalList.add(i);
       matchTotalList= myMatchTotal(i, matchTotalList,size);
       int matchTotal= calculateListMatches(matchTotalList);
       if(matchTotal>max){
         max=matchTotal;
         maxPiece=(ArrayList<Integer>) matchTotalList.clone();
       }
    }
    return maxPiece;
  }
  
  public int calculateListMatches(ArrayList<Integer> list){
    int total=0;
    for(int i=0;i<list.size()-1;i++){
      int me= list.get(i);
      int you= list.get(i+1);
      total+=adjMat[me][you];
    }
    return total;
  }
  
  public ArrayList<Integer> myMatchTotal(int me,ArrayList<Integer>myList,int size){
    int bestMatch= findMyBest(myList,me);
    if(bestMatch>=0 && myList.size()<size){
      myList.add(bestMatch);
      myList=myMatchTotal(bestMatch,myList,size);
    }
    return myList;
  }
  
  public int findMyBest(ArrayList<Integer> myList, int me){
    int best=-1;
    int bestMatch=0;    
    for(int you=0;you<adjMat[me].length;you++){
      if(adjMat[me][you] > bestMatch && !(myList.contains(you))){
        best= you;
        bestMatch=adjMat[me][you];
      }      
    }
    return best;
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
  
  
















