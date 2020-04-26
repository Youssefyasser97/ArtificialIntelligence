
public class Node
{
    State state;
	Node parent;
    Operator operator;
    int depth;
    int pathcost;
    
    public Node(State state, Node parent, int depth, int pathcost){
    	this.state = state;
        this.parent = parent;
        this.operator = operator.init;
        this.depth = depth;
        this.pathcost = pathcost;
    }
    
    public Node(State state, Node parent, Operator operator, int depth, int pathcost){
        this.state = state;
        this.parent = parent;
        this.operator = operator;
        this.depth = depth;
        this.pathcost = pathcost;
    }
    
    public State getState() {
		return state;
	}

	public Node getParent() {
		return parent;
	}

	public Operator getOperator() {
		return operator;
	}

	public int getDepth() {
		return depth;
	}

	public int getPathcost() {
		return pathcost;
	}
	public String toString(){
		return this.state.posx + "   " + this.state.posy + "   " + this.depth ;
//		return "" + state + parent + operator + depth + Integer.toString(pathcost) ; 
		//return  Integer.toString(pathcost);
	}
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		State state1 = this.state;
		Node Node1 = ((Node)arg0);
		State state2 = Node1.state;
		return (state1.equals(state2));
	}
}

//&& Node1.parent.equals(this.parent) && Node1.pathcost == this.pathcost && Node1.operator.equals(this.operator)
