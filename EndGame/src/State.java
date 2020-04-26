import java.util.ArrayList;

public class State
{
	int posx;
	int posy;
	int thanosx;
	int thanosy;
	int cols;
	int rows;
    ArrayList<int[]> stonePositions;
    ArrayList<int[]> warriorPositions;
	
    public State(int posx, int posy, ArrayList<int[]> stonePositions,ArrayList<int[]> warriorPositions,int thanosx,int thanosy,int cols,int rows ){
    	this.posx = posx;
    	this.posy = posy;
        this.stonePositions = stonePositions;
        this.warriorPositions = warriorPositions;
        this.thanosx=thanosx;
        this.thanosy=thanosy;
        this.cols=cols;
        this.rows=rows;
        
//        this.stonePositions.clear();
       
    }

	

	

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public int getThanosx2() {
		return thanosx;
	}

	public int getThanosy2() {
		return thanosy;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public ArrayList<int[]> getStonePositions() {
		return stonePositions;
	}

	public void setStonePositions(ArrayList<int[]> stonePositions) {
		this.stonePositions = stonePositions;
	}

	public ArrayList<int[]> getWarriorPositions() {
		return warriorPositions;
	}

	public void setWarriorPositions(ArrayList<int[]> warriorPositions) {
		this.warriorPositions = warriorPositions;
	}
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stu
		State state2 = (State)arg0;
		return (state2.posx == this.posx && state2.posy == this.posy && state2.stonePositions.size() == this.stonePositions.size() && state2.warriorPositions.size() == this.warriorPositions.size());
		}
	}
