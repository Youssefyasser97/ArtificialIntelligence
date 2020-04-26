import java.util.ArrayList;


public class EndGame extends Problem {
	
//	ArrayList<String> operators;
//    State initialState;
//    ArrayList<String> stateSpace;
//    boolean goalTest;
//    int pathCost;
	
	grid grid;

	public EndGame(ArrayList<State> stateSpace, grid grid) {
		super(stateSpace);
		this.grid = grid;
		
		this.operators = new ArrayList<String>();
		operators.add("right");
		operators.add("left");
		operators.add("up");
		operators.add("down");
		operators.add("kill");
		operators.add("collect");
		operators.add("snap");
		this.initialState = new State(grid.getPosx(), grid.getPosy(), grid.getStonePositions(), grid.getWarriorPositions(),grid.getThanosx(),grid.getThanosy(),grid.getCols(),grid.getRows());
		this.stateSpace = stateSpace;
		this.goalTest = false;
		this.pathCost = 0;
	}

}
