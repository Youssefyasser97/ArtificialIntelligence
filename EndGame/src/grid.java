import java.util.ArrayList;


public class grid {
String [][] map ;
int rows;
int cols;
int thanosx;
int thanosy;
int playerx;
int playery;
ArrayList<int[]> stonePositions;
ArrayList<int[]> warriorPositions;


public int getPosx() {
	return playerx;
}

public int getPosy() {
	return playery;
}

public ArrayList<int[]> getStonePositions() {
	return stonePositions;
}

public ArrayList<int[]> getWarriorPositions() {
	return warriorPositions;
}

public grid(State state){
	this.rows = state.getRows();
	this.cols = state.getCols();
	this.thanosx = state.getThanosx2();
	this.thanosy = state.getThanosy2();
	this.playerx = state.getPosx();
	this.playery = state.getPosy();
	this.stonePositions = state.getStonePositions();
	this.warriorPositions = state.getWarriorPositions();
	this.map = new String[this.rows][this.cols];
	
	for(int i=0;i<this.stonePositions.size();i++){
		int[] temp = stonePositions.get(i);
		this.map[temp[0]][temp[1]] = "S";
	}
	
	for(int i=0;i<this.warriorPositions.size();i++){
		int[] temp = warriorPositions.get(i);
		this.map[temp[0]][temp[1]] = "W";
	}
	
	this.map[this.thanosx][this.thanosy] = "T";
	this.map[this.playerx][this.playery] = "I";
}

public grid(String input){
	stonePositions = new ArrayList<int[]>();
	warriorPositions = new ArrayList<int[]>();
	String []split = input.split(";");
	//use split2 as a temp array
	String [] split2 = split[0].split(","); //store m and n in split2 
	this.rows = Integer.parseInt(split2[0]);
	this.cols = Integer.parseInt(split2[1]);
	this.map = new String[this.rows][this.cols];
	
	split2 = split[1].split(","); //store ix and iy in split2
	this.playerx = Integer.parseInt(split2[0]);
	this.playery = Integer.parseInt(split2[1]);
	this.map[this.playerx][this.playery] = "I";
	
	
	split2 = split[2].split(","); //store tx and ty in split2
	this.thanosx = Integer.parseInt(split2[0]);
	this.thanosy = Integer.parseInt(split2[1]);
	this.map[thanosx][thanosy] = "T";
	
	split2 = split[3].split(","); //store stone locations in split2
	int tempx;
	int tempy;
	for(int i=0; i<split2.length-1; i+=2){
		tempx = Integer.parseInt(split2[i]);
		tempy = Integer.parseInt(split2[i+1]);
		this.map[tempx][tempy] = "S";
		int[] temp = {tempx,tempy};
		stonePositions.add(temp);
	}
	
//	stonePositions.clear();
	
	split2 = split[4].split(","); //store warrior in split2
	for(int i=0; i<split2.length-1; i+=2){
		tempx = Integer.parseInt(split2[i]);
		tempy = Integer.parseInt(split2[i+1]);
		this.map[tempx][tempy] = "W";
		int[] temp = {tempx,tempy};
		warriorPositions.add(temp);
	}
}

public static void main(String[] args) {
//	String in = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
	String in = "2,2;0,1;0,0;1,0;1,1";
	grid g = new grid(in);
	g.visualize();
}
//LEFT(5),COLLECT(8),UP(-12),SNAP;1


public void visualize(){
	for(int i=0; i<this.cols+1;i++){
		for(int j=0; j<this.rows+1; j++){
			if(i==0){
				if(j==0)
					System.out.print(" |");
				else{
					System.out.print(j-1);
					if(j<this.rows)
						System.out.print("|");
				}
			}
			else{
				if(j==0)
					System.out.print(i-1+"|");
				else{
					if(this.map[i-1][j-1] != null){
						System.out.print(this.map[i-1][j-1]);
					}
					else {
						System.out.print(" "); //print empty space in case of null
					}
					if(j<this.rows){
						System.out.print("|");
					}
				}
			}
			
			
		}
		System.out.println("|");
	}
}

public int getRows() {
	return rows;
}

public int getCols() {
	return cols;
}

public int getThanosx() {
	return thanosx;
}

public int getThanosy() {
	return thanosy;
}

}