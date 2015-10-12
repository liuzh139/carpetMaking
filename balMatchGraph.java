package Version_9;
import java.util.*;

public class balMatchGraph{
  public Random rand = new Random();
  public String printErr="";
  private final int MAX_PIECES;
  public Piece pieceList[]; // list of vertices
  public int adjMat[][];      // adjacency matrix
  public int nPieces;          // current number of vertices
  public int words=0;
  private Queue<Integer> stackQ = new LinkedList<Integer>();
  
  public balMatchGraph(int size){               // constructor
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
  
  public int matching(String p1, String p2){
    int matching=0;    
    for(int i=0;i<p2.length();i++){
      if(p1.charAt(i)==p2.charAt(i)){
        matching++;
      }
    }
    return matching;
  }
  
  public int nonMatching(String p1, String p2){
    int nonMatching=0;    
    for(int i=0;i<p2.length();i++){
      if(p1.charAt(i)!=p2.charAt(i)){
        nonMatching++;
      }
    }
    return nonMatching;
  }
  
  public int calculateBal(ArrayList<String> list){
    int bal=0;
    int nonMatching=0;
    int matching=0;
    for(int i=0;i<list.size()-1;i++){
      String me=list.get(i);
      String next=list.get(i+1);
      nonMatching+= nonMatching(me,next);
      matching+=matching(me,next);
    }
    bal= matching-nonMatching;
    return bal;
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
  
  public ArrayList<String> generateList(int size){
    int parity;
    ArrayList<String> strList= new ArrayList<String>();
    ArrayList<Integer> intList= new ArrayList<Integer>();
    int r=randomInt(nPieces);
    intList.add(r);
    strList.add(pieceList[r].toString());
     int tenSecs=0;    
    while(strList.size()<size && tenSecs< 10500000){
      parity=calculateBal(strList);
      ArrayList<String> temp= (ArrayList<String>) strList.clone();
      ArrayList<String> tempR= (ArrayList<String>) strList.clone();
      r=randomInt(nPieces);
      if(!(intList.contains(r))){
        String me= pieceList[r].toString(); 
        String rev= new StringBuilder(me).reverse().toString();
        temp.add(me);
        tempR.add(rev);
        int par1=calculateBal(temp);
        int parR=calculateBal(tempR);
        if(parity>1){
          if(par1<parity && parR<parity){
            if(par1<=parR && par1>=-2){
              intList.add(r);
              strList.add(me);
            }else if(parR>=-1){
              intList.add(r);
              strList.add(rev);
            }
          }else if((par1<parity&&par1>=-2)){
            intList.add(r);
            strList.add(me);
          }else if((parR<parity&&parR>=-2)){
            intList.add(r);
            strList.add(rev);
          }
        }else if(parity<-2){
          if(par1>parity && parR>parity){
            if(par1>=parR && par1<=2){
              intList.add(r);
              strList.add(me);
            }else if(parR<=2){
              intList.add(r);
              strList.add(rev);
            }
          }else if((par1>parity&&par1<=2)){
            intList.add(r);
            strList.add(me);
          }else if((parR>parity&&parR<=2)){
            intList.add(r);
            strList.add(rev);
          }
        }else{
          if(strList.size()==size-2||strList.size()==size-1){
            if(par1>-2&&par1<2){
              intList.add(r);
              strList.add(me);
            }else if(parR>-2&&parR<2){
              intList.add(r);
              strList.add(rev);
            }
          }else if((parity>=0 && par1<parR)||(parity<=0 && par1>parR)){
            intList.add(r);
            strList.add(me);
          }else{
            intList.add(r);
            strList.add(rev); 
          }
        }
      }      
      tenSecs++;
    }
    //System.out.println("Balance: " + calculateBal(strList) + " List: " + strList + " Size: "+strList.size()+ " time: " + tenSecs );
    return strList;
  }
  
  public ArrayList<String> findBalance(int size){
    ArrayList<String> list=new ArrayList<String> ();      
      int tries=0; 
      System.out.print("trial 0 of 60");
      while(tries<60){        
        list = generateList(size);
        if(list.size()==size){
          return list;
        }
        System.out.print("   unsuccessful\ntrial " + (tries+1) +" of " + 60);
        tries++;
      }      
    return list;
  }
  
public ArrayList<String> randomList(int size){
  int r;
  ArrayList<String> strList= new ArrayList<String>();
  ArrayList<Integer> list= new ArrayList<Integer>();   
  while(list.size()<size){
    r= randomInt(nPieces);
    if(!(list.contains(r))){
      list.add(r);
    }
  }
  strList=convertList(list);
  return strList;
}

public int randomInt(int upperBound){   
  int randNum = rand.nextInt(upperBound);
  return randNum;
}

public ArrayList<String> convertList(ArrayList<Integer> list){
  ArrayList<String> strList= new ArrayList<String>();
  for(int each: list){
    String me= pieceList[each].toString();
    strList.add(me);
  }
  return strList;
}
}





















