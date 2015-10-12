package Version_9;
import java.util.*;

public class Piece{
  public String label;        // label (e.g. 'A')
  public State state;
  public int position;
  public int ancestor;
  public int dfs_distance;
  public int bfs_distance;
  public int ending;  
  public int count;
  
  public Piece(String lab, int p){
    label = lab;
    position=p;    
  }
  
  public String toString(){
    return label;
  }  
}



