import java.util.ArrayList;

abstract class Problem
{
    ArrayList<String> operators;
    State initialState;
    ArrayList<State> stateSpace;
    boolean goalTest;
    int pathCost;
    
    
    public Problem(ArrayList<State> stateSpace){
        this.stateSpace = stateSpace;
        this.goalTest = goalTest;
        this.pathCost = pathCost;
        
    }

}
