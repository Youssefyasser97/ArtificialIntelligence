import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

	public class Main {
		EndGame myGame;
		static grid myGrid;

		
		static boolean snapped = false;


		public static void main(String[] args) {

		
	
//		ArrayList<Operator> ex = expand(myNode);
//		System.out.println(ex);
//		ArrayList<Node> x = transform(myNode);
		String gridString = "3,3;0,1;0,0;0,2,2,2,1,1;2,1,1,0";
//		String gridString = "4,4;0,1;0,0;1,0,2,2,2,1;1,1,0,2";
//		String gridString = "5,5;1,2;3,1;1,1,2,1,2,2,4,0;0,3,3,0,3,2,4,3";
//		String gridString = "6,6;5,3;0,1;4,3,2,1,3,0,1,2,4,5,1,1;1,3,3,3,1,0,1,4,2,2";
		
		
//		String gridString = "5,5;1,2;3,1;0,2,4,2;0,0";
		String strategy = "BF";
		System.out.println(solve(gridString, strategy, true));
		}
		
		

		
	
	
		
		private static boolean goalTest(Node node){
			if(node.getParent()!= null && node.getPathcost() < 100 && node.getParent().getOperator() == Operator.snap){
				return true;
			}
			return false;
		}
		
		private static String backTrack(Node currNode, boolean visualize){
			Stack<Node> result = new Stack<Node>();
			Stack<String> stringResult = new Stack<>();
			int finalDamage = 0;
			while(true){
				currNode = currNode.getParent();
				if(currNode == null){
					break;
				}
				stringResult.push("");
//				stringResult.push("(" + Integer.toString(currNode.getPathcost()) + ")");
				result.push(currNode);
//				System.out.println("backTrack" + currNode.getPathcost());
				finalDamage += currNode.getPathcost();
			}
			String finalResult = "";
//			result.pop();
//			result.pop();
			Node resultNode = result.pop();
			String currOperator = resultNode.getOperator().toString();
			if(visualize){
				System.out.println(currOperator + ":");
				grid grid = new grid(resultNode.getState());
				grid.visualize();
				System.out.println("");
			}
			stringResult.pop();
			String cost = "";
			while(!result.isEmpty()){
				resultNode = result.pop();
				cost = stringResult.pop();
				currOperator = resultNode.getOperator().toString();
				if(visualize){
					System.out.println(currOperator + cost + ":");
					grid grid = new grid(resultNode.getState());
					grid.visualize();
				}
//				finalDamage += resultNode.getPathcost();
				finalResult += currOperator + cost + ",";
			}
			finalResult = finalResult.substring(0, finalResult.length()-1);
			finalResult = finalResult + ";" + finalDamage;
			return finalResult;
		}
	
		private static String backTrack(Node currNode){
			Stack<String> result = new Stack<String>();
			int finalDamage = 0;
			while(true){
				currNode = currNode.getParent();
				if(currNode == null){
					break;
				}
				result.push("("+ Integer.toString(currNode.getPathcost()) + ")");
				result.push(currNode.getOperator().toString());
				finalDamage += currNode.getPathcost();
			}
			String finalResult = "";
//			result.pop();
//			result.pop();
			while(!result.isEmpty()){
				String currOperator = result.pop();
				finalResult += currOperator + ",";
			}
			finalResult = finalResult.substring(0, finalResult.length()-1);
			finalResult = finalResult + ";" + finalDamage;
			return finalResult;
		}
		
	private static String deapthFirst(Node node, boolean visualize){
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		int count = 0;
		while(!nodes.isEmpty()){
			Node currNode = nodes.get(0);
			nodes.remove(0);
			count++;
				if(goalTest(currNode)){
					String finalResult = backTrack(currNode, visualize);
					finalResult = finalResult + ";" + count;
					return finalResult;
				}
				else{
					if(currNode.getPathcost()<100 && transform(currNode)!=null){
						ArrayList<Node> temp = transform(currNode);
						temp.addAll(nodes);
						nodes.clear();
						nodes.addAll(temp);
					}
				}
			}
		return "";
	}
	
	private static String iterativeDeepening(Node node, boolean visualize){
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		int count = 0;
		int maxDeapth = 0;
		while(!nodes.isEmpty()){
			Node currNode = nodes.get(0);
			nodes.remove(0);
			count++;
				if(goalTest(currNode)){
					String finalResult = backTrack(currNode, visualize);
					finalResult = finalResult + ";" + count;
					return finalResult;
				}
				else{
					if(currNode.getPathcost()<100 && transform(currNode)!=null){
						if(currNode.getDepth()<maxDeapth){
							ArrayList<Node> temp = transform(currNode);
							temp.addAll(nodes);
							nodes.clear();
							nodes.addAll(temp);
						}
						else{
							maxDeapth++;
							ArrayList<Node> temp = transform(node);
							temp.addAll(nodes);
							nodes.clear();
							nodes.addAll(temp);
						}
					}
				}
			}
		return "";
	}
		
	private static boolean notIn(State state1, ArrayList<State> states){
		for(int i=0;i<states.size();i++){
			State state2 = states.get(i);
			if(state1.getPosx() == state2.getPosx() && state1.getPosy() == state2.getPosy() && state1.getStonePositions().size() == state2.getStonePositions().size()){
				return false;
			}
		}
		return true;
	}
//  BFS using hashsets		
//	 static String breadthFirst(Node node, boolean visualize){
//		ArrayList<Node> nodes = new ArrayList<>();
//		HashSet<Node> h=new HashSet<>();
//		nodes.add(node);
//		for(int i=0;i<nodes.size();i++){
//			Node currNode = nodes.get(i);
//			if(h.contains(currNode))
//				continue;
//			if(goalTest(currNode)){
//				String finalResult = backTrack(currNode, visualize);
//				int count = i+1;
//				finalResult = finalResult + ";" + count;
//				return finalResult;
//			}
//			else{
//				if(currNode.getPathcost()<100 && transform(currNode)!=null){
//					nodes.addAll(transform(currNode));
//					h.addAll(transform(currNode));
//				}
//			}
//		}
//		return "no answer";
//	}
//	
	
	
//	breadthfirst using ArrayList:
	 static String breadthFirst(Node node, boolean visualize){
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		for(int i=0;i<nodes.size();i++){
			Node currNode = nodes.get(i);
			if(goalTest(currNode)){
				String finalResult = backTrack(currNode, visualize);
				int count = i+1;
				finalResult = finalResult + ";" + count;
				return finalResult;
			}
			else{
				if(currNode.getPathcost()<100 && transform(currNode)!=null){
					nodes.addAll(transform(currNode));
				}
			}
		}
		return "";
	}

	
	
	
	 static ArrayList<Node> aSort1(ArrayList<Node> n){
		 ArrayList<Node> nodes = n;
		 ArrayList<Node> sorted = new ArrayList<>();
		 int min=0;
		 while(!nodes.isEmpty()){
			 int index = 0;
			 min = (nodes.get(0).getState().getStonePositions().size()*3 + 5 + nodes.get(0).getPathcost());
			 for(int j=0;j<nodes.size();j++){
				 int check = nodes.get(j).getState().getStonePositions().size()*3 + 5 + nodes.get(j).getPathcost(); 
				 if(check<min){
					 min = check;
					 index = j;
				 }
			 }
			 sorted.add(nodes.get(index));
			 nodes.remove(index);
		 }
		 return sorted;
	 }
	 
	 static String aStar1(Node node, boolean visualize){
			ArrayList<Node> nodes = new ArrayList<>();
			nodes.add(node);
//				State state = new State(0, 0, null, null, 0, 0, 5, 5);
//				Node curNode = new Node(state, node, Operator.snap, 0, 0);
//				nodes.addAll(transform(curNode));
			for(int i=0;i<nodes.size();i++){
				Node currNode = nodes.get(i);
				if(goalTest(currNode)){
					String finalResult = backTrack(currNode, visualize);
					int count = i+1;
					finalResult = finalResult + ";" + count;
					return finalResult;
				}
				else{
					if(currNode.getPathcost()<100 && transform(currNode)!=null){
						nodes.addAll(aSort1(transform(nodes.get(i))));
					}
				}
			}
			return "";
		}

	 static ArrayList<Node> aSort2(ArrayList<Node> n){
		 ArrayList<Node> nodes = n;
		 ArrayList<Node> sorted = new ArrayList<>();
		 int min=0;
		 while(!nodes.isEmpty()){
			 int index = 0;
			 min = (nodes.get(0).getState().getStonePositions().size()*3 + nodes.get(0).getPathcost());
			 for(int j=0;j<nodes.size();j++){
				 int check = nodes.get(j).getState().getStonePositions().size()*3 + nodes.get(j).getPathcost(); 
				 if(check<min){
					 min = check;
					 index = j;
				 }
			 }
			 sorted.add(nodes.get(index));
			 nodes.remove(index);
		 }
		 return sorted;
	 }
	 
	 static String aStar2(Node node, boolean visualize){
			ArrayList<Node> nodes = new ArrayList<>();
			nodes.add(node);
//				State state = new State(0, 0, null, null, 0, 0, 5, 5);
//				Node curNode = new Node(state, node, Operator.snap, 0, 0);
//				nodes.addAll(transform(curNode));
			for(int i=0;i<nodes.size();i++){
				Node currNode = nodes.get(i);
				if(goalTest(currNode)){
					String finalResult = backTrack(currNode, visualize);
					int count = i+1;
					finalResult = finalResult + ";" + count;
					return finalResult;
				}
				else{
					if(currNode.getPathcost()<100 && transform(currNode)!=null){
						nodes.addAll(aSort2(transform(nodes.get(i))));
					}
				}
			}
			return "";
		}
	 
	 static ArrayList<Node> uCSort(ArrayList<Node> n){
		 ArrayList<Node> nodes = n;
		 ArrayList<Node> sorted = new ArrayList<>();
		 int min=0;
		 while(!nodes.isEmpty()){
			 int index = 0;
			 min = nodes.get(0).getPathcost();
			 for(int j=0;j<nodes.size();j++){
				 int check = nodes.get(j).getPathcost(); 
				 if(check<min){
					 min = check;
					 index = j;
				 }
			 }
			 sorted.add(nodes.get(index));
			 nodes.remove(index);
		 }
		 return sorted;
	 }

	 static String uCost(Node node, boolean visualize){
		 ArrayList<Node> nodes = new ArrayList<>();
			nodes.add(node);
//				State state = new State(0, 0, null, null, 0, 0, 5, 5);
//				Node curNode = new Node(state, node, Operator.snap, 0, 0);
//				nodes.addAll(transform(curNode));
			for(int i=0;i<nodes.size();i++){
				Node currNode = nodes.get(i);
				if(goalTest(currNode)){
					String finalResult = backTrack(currNode, visualize);
					int count = i+1;
					finalResult = finalResult + ";" + count;
					return finalResult;
				}
				else{
					if(currNode.getPathcost()<100 && transform(currNode)!=null){
						nodes.addAll(uCSort(transform(nodes.get(i))));
					}
				}
			}
			return "";
	 }
	 
	 static ArrayList<Node> g1Sort(ArrayList<Node> n){
		 ArrayList<Node> nodes = n;
		 ArrayList<Node> sorted = new ArrayList<>();
		 int min=0;
		 while(!nodes.isEmpty()){
			 int index = 0;
			 min = nodes.get(0).getState().getStonePositions().size()*3 + 5;
			 for(int j=0;j<nodes.size();j++){
				 int check = nodes.get(j).getState().getStonePositions().size()*3 + 5; 
				 if(check<min){
					 min = check;
					 index = j;
				 }
			 }
			 sorted.add(nodes.get(index));
			 nodes.remove(index);
		 }
		 return sorted;
	 }
	 
	 static String greedy1(Node node, boolean visualize){
		 ArrayList<Node> nodes = new ArrayList<>();
			nodes.add(node);
//				State state = new State(0, 0, null, null, 0, 0, 5, 5);
//				Node curNode = new Node(state, node, Operator.snap, 0, 0);
//				nodes.addAll(transform(curNode));
			for(int i=0;i<nodes.size();i++){
				Node currNode = nodes.get(i);
				if(goalTest(currNode)){
					String finalResult = backTrack(currNode, visualize);
					int count = i+1;
					finalResult = finalResult + ";" + count;
					return finalResult;
				}
				else{
					if(currNode.getPathcost()<100 && transform(currNode)!=null){
						nodes.addAll(g1Sort(transform(nodes.get(i))));
					}
				}
			}
			return "";
	 }
	 
	 static ArrayList<Node> g2Sort(ArrayList<Node> n){
		 ArrayList<Node> nodes = n;
		 ArrayList<Node> sorted = new ArrayList<>();
		 int min=0;
		 while(!nodes.isEmpty()){
			 int index = 0;
			 min = nodes.get(0).getState().getStonePositions().size()*3 + 5 + nodes.get(0).getState().getWarriorPositions().size()*1;
			 for(int j=0;j<nodes.size();j++){
				 int check = nodes.get(j).getState().getStonePositions().size()*3 + 5 + nodes.get(j).getState().getWarriorPositions().size()*1; 
				 if(check<min){
					 min = check;
					 index = j;
				 }
			 }
			 sorted.add(nodes.get(index));
			 nodes.remove(index);
		 }
		 return sorted;
	 }
	 
	 static String greedy2(Node node, boolean visualize){
		 ArrayList<Node> nodes = new ArrayList<>();
			nodes.add(node);
//				State state = new State(0, 0, null, null, 0, 0, 5, 5);
//				Node curNode = new Node(state, node, Operator.snap, 0, 0);
//				nodes.addAll(transform(curNode));
			for(int i=0;i<nodes.size();i++){
				Node currNode = nodes.get(i);
				if(goalTest(currNode)){
					String finalResult = backTrack(currNode, visualize);
					int count = i+1;
					finalResult = finalResult + ";" + count;
					return finalResult;
				}
				else{
					if(currNode.getPathcost()<100 && transform(currNode)!=null){
						nodes.addAll(g2Sort(transform(nodes.get(i))));
					}
				}
			}
			return "";
	 }
	 
	 static int distance(State state){
		 return Math.abs(state.getThanosx2() - state.getPosx()) + Math.abs(state.getThanosy2() - state.getPosy()); 
	 }
	 
	 //	breadthfirst using Queue:
//	static String breadthFirst(Node node){
//		Queue<Node> nodes = new LinkedList<>();
//		nodes.add(node);
////			State state = new State(0, 0, null, null, 0, 0, 5, 5);
////			Node curNode = new Node(state, node, Operator.snap, 0, 0);
////			nodes.addAll(transform(curNode));
//		int count = 0;
//		while(!nodes.isEmpty()){
//			Node currNode = nodes.remove();
//			count++;
//			if(goalTest(currNode)){
//				String finalResult = backTrack(currNode);
//				finalResult = finalResult + ";" + count;
//				return finalResult;
//			}
//			else{
//				if(currNode.getPathcost()<100 && transform(currNode)!=null){
//					nodes.addAll(transform(currNode));
//				}
//			}
//		}
//		return "no answer";
//	}

//	breadthfirst using HashSets:
//	 static String breadthFirst(Node node){
//		HashSet<Node> nodes = new HashSet<>();
//		nodes.add(node);
////			State state = new State(0, 0, null, null, 0, 0, 5, 5);
////			Node curNode = new Node(state, node, Operator.snap, 0, 0);
////			nodes.addAll(transform(curNode));
//		int count = 0;
//        Iterator<Node> i = nodes.iterator();
//		while(i.hasNext()){
//			System.out.println(nodes.size());
//			Node currNode = i.next();
//			count++;
//			if(goalTest(currNode)){
//				String finalResult = backTrack(currNode);
//				finalResult = finalResult + ";" + count;
//				return finalResult;
//			}
//			else{
//				if(currNode.getPathcost()<100 && transform(currNode)!=null){
//					nodes.addAll(transform(currNode));
//				}
//			}
//		}
//		return "no answer";
//	}
	
	public static String solve(String g, String strategy, boolean visualize){
		String plan = "";
		grid grid = new grid(g);
		Node startNode = new Node(new State(grid.getPosx(), grid.getPosy(), grid.getStonePositions(), grid.getWarriorPositions(), grid.getThanosx(), grid.getThanosy(), grid.getCols(), grid.getRows()), null, 0, 0);
		switch(strategy){
		case "BF":
			plan=breadthFirst(startNode, visualize);
			break;
		case "DF":
			plan=deapthFirst(startNode, visualize);
			break;
		case "ID":
			plan = iterativeDeepening(startNode, visualize);
			break;
		case "UC":
			plan = uCost(startNode, visualize);
			break;
		case "GR1":
			plan = greedy1(startNode, visualize);
			break;
		case "GR2":
			plan = greedy1(startNode, visualize);
			break;
		case "AS1":
			plan = aStar1(startNode, visualize);
			break;
		case "AS2":
			plan = aStar2(startNode, visualize);
			break;
		default:
			plan = "Unsupported Search Strategy";
			break;
	}
		
		return plan;
		
	}
		
	
		
	public static ArrayList<Operator> expand(Node node){
		
		ArrayList<Operator> operators = new ArrayList<Operator>();
		
		int posx = node.getState().getPosx();
		int posy = node.getState().getPosy();
//		System.out.println(posx + "," + posy);
		int thanosx = node.getState().getThanosx2();
		int thanosy = node.getState().getThanosy2();
		int damage = node.getPathcost();
		int cols=node.getState().getCols();
		int rows=node.getState().getRows();
		
		
		for(int i=0;i<node.getState().stonePositions.size();i++){
			int x =node.getState().stonePositions.get(i)[0];
			int y =node.getState().stonePositions.get(i)[1];			
			if(x==node.getState().getPosx()&& y==node.getState().getPosy()){
				operators.add(Operator.collect);
			}
			}
		
		if(node.getState().getPosx() == node.getState().getThanosx2() && node.getState().getPosy() == node.getState().getThanosy2() && node.getState().getStonePositions().size()==0){
//			System.out.println("tiger");
			operators.add(Operator.snap);	
			
		}
		
		int[] temp = {0,0};
		temp[0] = posx;
		temp[1] = posy-1;
		boolean warrior_Left =false;
		for(int i=0;i<node.getState().getWarriorPositions().size();i++){
			if(i>=0 && i<node.getState().getWarriorPositions().size()){
				int [] arr =node.getState().warriorPositions.get(i);
				if(arr[0]==posx  && arr[1]==posy-1){
					warrior_Left=true;
					break;
				}
			}
		}
		if(posy>0 && !warrior_Left && (thanosy!=posy-1 || thanosx!=posx || node.getState().getStonePositions().size()==0) && node.getOperator()!=Operator.right){
			operators.add(Operator.left);
			warrior_Left=false;
		}
		temp[0] = posx;
		temp[1] = posy+1;

		boolean warrior_Right =false;
		for(int i=0;i<node.getState().getWarriorPositions().size();i++){
			int [] arr =node.getState().warriorPositions.get(i);
			if(arr[0]==posx  && arr[1]==posy+1){
				warrior_Right=true;
				break;
			}
		}
		if(posy <cols-1 && !warrior_Right && (thanosy!=posy+1 || thanosx!=posx || node.getState().getStonePositions().size()==0)){
			operators.add(Operator.right);
			warrior_Right=false;
		}
		
		temp[0] = posx-1;
		temp[1] = posy;
		boolean warrior_Up =false;
		for(int i=0;i<node.getState().getWarriorPositions().size();i++){
			int [] arr =node.getState().warriorPositions.get(i);
			if(arr[0]==posx -1 && arr[1]==posy){
				warrior_Up=true;
				break;
			}
		}
		if(posx>0 && !warrior_Up && (thanosx!=posx-1 || thanosy!=posy || node.getState().getStonePositions().size()==0)){
			operators.add(Operator.up);
			warrior_Up=false;
			
		}
		temp[0] = posx+1;
		temp[1] = posy;
		boolean warrior_Down =false;
		for(int i=0;i<node.getState().warriorPositions.size();i++){
			int [] arr =node.getState().warriorPositions.get(i);
			if(arr[0]==posx + 1 && arr[1]==posy){
				warrior_Down=true;
				break;
			}
		}
		
		if(posx<rows-1 && !warrior_Down && (thanosx!=posx+1 || thanosy != posy || node.getState().getStonePositions().size()==0)){
			operators.add(Operator.down);
			warrior_Down = false;
		}
		
		

		
		temp[0] = posx;
		temp[1] = posy;
		for(int i=0;i<node.getState().warriorPositions.size();i++){
			int [] arr =node.getState().warriorPositions.get(i);
			if((arr[0]==posx && arr[1]==posy-1) || (arr[0]==posx && arr[1]==posy+1) || (arr[0]==posx+1 && arr[1]==posy) || (arr[0]==posx-1 && arr[1]==posy) ){
				operators.add(Operator.kill);
				break;
			}
			}
			
		

			
		
		
		return operators;
	}

	
	
	
//	public static ArrayList<Node> visitedNodes = new ArrayList<Node>();
	static int c =0;

	public static ArrayList<Node> transform(Node node){
		 boolean warriorLeft = false;
		 boolean warriorRight = false;
		 boolean warriorUp = false;
		 boolean warriorDown = false;
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Operator> operators = expand(node);
//		System.out.println(expand(node));
//		visitedNodes.add(node);
//		boolean visited = false;
//		boolean visited2 = false;
//		boolean visited3 = false;
//		boolean visited4 = false;
//		boolean visited5 = false;
		int posx = node.getState().getPosx();
		int posy = node.getState().getPosy();
		int thanosx = node.getState().getThanosx2();
		int thanosy = node.getState().getThanosy2();
		ArrayList<int[]> warriorPositions = node.getState().getWarriorPositions();
		int depth = node.getDepth()+1;
		 State state = node.getState();
		int damage = node.getPathcost();

		Node newNode;
		State state2;
		for(int i = 0; i<operators.size(); i++){
			switch(operators.get(i)){
			case collect:
//				System.out.println("dakhal fel collect");
				damage=0;

				boolean warrior_Left = false;
				boolean warrior_Right = false;
				boolean warrior_Up = false;
				boolean warrior_Down = false;
				
				state=node.getState();
				ArrayList<int[]> stonesArray = new ArrayList<int[]>();
				stonesArray = (ArrayList<int[]>) state.getStonePositions().clone();
//				System.out.println(stonesArray.size());
				state2 = new State(state.getPosx(), state.getPosy(), stonesArray,  state.getWarriorPositions(), state.getThanosx2(), state.getThanosy2(), state.getCols(), state.getRows());
				
				
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
					if(arr[0]==state2.getPosx() && arr[1]==state2.getPosy()-1){
						 warrior_Left=true;
						break;
					}
				}
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
					if(arr[0]==state2.getPosx() + 1 && arr[1]==state2.getPosy()){
						 warrior_Down=true;
						break;
					}
				}
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
					if(arr[0]==state2.getPosx()-1 && arr[1]==state2.getPosy()){
						warrior_Up=true;
						break;
					}
				}
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
					if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()+1){
						warrior_Right=true;
						break;
					}
				}
				
				
				if(warrior_Up)
					damage++;
				if(warrior_Down)
					damage++;
				if(warrior_Left)
					damage++;
				if(warrior_Right)
					damage++;
				
				
				if(thanosAdjacent(state2.getPosx(), state2.getPosy(), node)){
					damage+=5;
				}
				
				damage+=3;
				
				for (int j = 0; j < stonesArray.size(); j++) {
					int [] arr =state2.getStonePositions().get(j);
					if(state2.getPosx() == arr[0] && state2.getPosy() == arr[1] ){
						stonesArray.remove(j);
						break;
					}
					
				}
				
				state2 = new State(state.getPosx(), state.getPosy(), stonesArray,  state.getWarriorPositions(), state.getThanosx2(), state.getThanosy2(), state.getCols(), state.getRows());
//				state2.stonePositions = stonesArray;
				
				
				newNode = new Node(state2, node, Operator.collect, depth, damage);
				nodes.add(newNode);
				damage=0;
				break;
								
	
			case up:
				damage=0;

				 state2 =new State(state.getPosx()-1, node.getState().getPosy(), node.getState().stonePositions,  node.getState().warriorPositions,  node.getState().getThanosx2(),  node.getState().getThanosy2(), node.getState().cols, node.getState().rows);
				
				newNode = new Node(state2, node, Operator.up, depth, damage);
				warriorAdjacent(newNode);
				 warrior_Left =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()-1){
						warrior_Left=true;
						break;
					}
				}
				 warrior_Down =false;
				for(int j=0;j<node.getState().warriorPositions.size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx() + 1 && arr[1]==state2.getPosy()){
						warrior_Down=true;
						break;
					}
				}
				 warrior_Up =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx()-1 && arr[1]==state2.getPosy()){
						warrior_Up=true;
						break;
					}
				}
				 warrior_Right =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()+1){
						warrior_Right=true;
						break;
					}
				}
				
				
				if(warrior_Up)
					damage++;
				if(warrior_Down)
					damage++;
				if(warrior_Left)
					damage++;
				if(warrior_Right)
					damage++;
								
				if(thanosAdjacent(state2.getPosx(), state2.getPosy(), node))
					damage+=5;
				
				newNode = new Node(state2, node, Operator.up, depth, damage);


				
//				if(visitedNodes.contains(newNode)){
//			}
			
				nodes.add(newNode);
//				visitedNodes.add(newNode);

				damage=0;
				break;
				
				
			case down:
				damage=0;

				 state2 =new State(state.getPosx()+1, node.getState().getPosy(), node.getState().stonePositions,  node.getState().warriorPositions,  node.getState().getThanosx2(),  node.getState().getThanosy2(), node.getState().cols, node.getState().rows);
//					state.setPosx(state.getPosx()-1);
					
					newNode = new Node(state2, node, Operator.down, depth, damage);
					warriorAdjacent(newNode);
					 warrior_Left =false;
					for(int j=0;j<node.getState().getWarriorPositions().size();j++){
						int [] arr =node.getState().warriorPositions.get(j);
						if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()-1){
							warrior_Left=true;
							break;
						}
					}
					 warrior_Down =false;
					for(int j=0;j<node.getState().warriorPositions.size();j++){
						int [] arr =node.getState().warriorPositions.get(j);
						if(arr[0]==state2.getPosx() + 1 && arr[1]==state2.getPosy()){
							warrior_Down=true;
							break;
						}
					}
					 warrior_Up =false;
					for(int j=0;j<node.getState().getWarriorPositions().size();j++){
						int [] arr =node.getState().warriorPositions.get(j);
						if(arr[0]==state2.getPosx()-1 && arr[1]==state2.getPosy()){
							warrior_Up=true;
							break;
						}
					}
					 warrior_Right =false;
					for(int j=0;j<node.getState().getWarriorPositions().size();j++){
						int [] arr =node.getState().warriorPositions.get(j);
						if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()+1){
							warrior_Right=true;
							break;
						}
					}
					
					
					if(warrior_Up)
						damage++;
					if(warrior_Down)
						damage++;
					if(warrior_Left)
						damage++;
					if(warrior_Right)
						damage++;
									
					if(thanosAdjacent(state2.getPosx(), state2.getPosy(), node))
						damage+=5;
					
					newNode = new Node(state2, node, Operator.down, depth, damage);
					
					
					
//					if(visitedNodes.contains(newNode)){
//				}
					nodes.add(newNode);
//					visitedNodes.add(newNode);
					
					damage=0;

					break;
				
				
				
			case right:
				damage=0;

				 state2 =new State(state.getPosx(), node.getState().getPosy()+1, node.getState().stonePositions,  node.getState().getWarriorPositions(), thanosx, thanosy, node.getState().cols, node.getState().rows);

				newNode = new Node(state2, node, Operator.right, depth, damage);
				warriorAdjacent(newNode);
				 warrior_Left =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()-1){
						warrior_Left=true;
						break;
					}
					
					
					
				}
				 warrior_Down =false;
				for(int j=0;j<node.getState().warriorPositions.size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx() + 1 && arr[1]==state2.getPosy()){
						warrior_Down=true;
						break;
					}
				}
				 warrior_Up =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx() -1 && arr[1]==state2.getPosy()){
						warrior_Up=true;
						break;
					}
				}
				 warrior_Right =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()+1){
						warrior_Right=true;
						break;
					}
				}
				
				
				if(warrior_Up)
					damage++;
				if(warrior_Down)
					damage++;
				if(warrior_Left)
					damage++;
				if(warrior_Right)
					damage++;

				if(thanosAdjacent(state2.getPosx(), state2.getPosy(), node))
					damage+=5;
				
				newNode = new Node(state2, node, Operator.right, depth, damage);
				
//			
				
//				if(visitedNodes.contains(newNode)){
//			}
				nodes.add(newNode);
//				visitedNodes.add(newNode);
				
				damage=0;

				break;
				
			case left:
				damage=0;

//				state.setPosy(state.getPosy()-1);
				 state2 =new State(state.getPosx(), node.getState().getPosy()-1, node.getState().stonePositions,  node.getState().getWarriorPositions(), thanosx, thanosy, node.getState().cols, node.getState().rows);

				newNode = new Node(state2, node, Operator.left, depth, damage);
				warriorAdjacent(newNode);
				 warrior_Left =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx() && arr[1]==state2.getPosy()-1){
						warrior_Left=true;
						break;
					}
				}
				 warrior_Down =false;
				for(int j=0;j<node.getState().warriorPositions.size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx()+1 && arr[1]==state2.getPosy()){
						warrior_Down=true;
						break;
					}
				}
				 warrior_Up =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx() -1 && arr[1]==state2.getPosy()){
						warrior_Up=true;
						break;
					}
				}
				 warrior_Right =false;
				for(int j=0;j<node.getState().getWarriorPositions().size();j++){
					int [] arr =node.getState().warriorPositions.get(j);
					if(arr[0]==state2.getPosx()  && arr[1]==state2.getPosy()+1){
						warrior_Right=true;
						break;
					}
				}
				
				
				if(warrior_Up)
					damage++;
				if(warrior_Down)
					damage++;
				if(warrior_Left)
					damage++;
				if(warrior_Right)
					damage++;
				

				if(thanosAdjacent(state2.getPosx(), state2.getPosy(), node))
					damage+=5;
				
				newNode = new Node(state2, node, Operator.left, depth, damage);				
				
				
//					if(visitedNodes.contains(newNode)){
//				}
				
					nodes.add(newNode);
//					visitedNodes.add(newNode);
				

				damage=0;

				break;

				
				
			case kill:
				damage=0;
				
				ArrayList<int[]> warriors = new ArrayList<int[]>();
				warriors=(ArrayList<int[]>) node.state.warriorPositions.clone();
				state2 =new State(state.getPosx(), node.getState().getPosy(), node.getState().stonePositions, warriors, thanosx, thanosy, node.getState().cols, node.getState().rows);
				int[] Left = {state2.getPosx(), state2.getPosy()-1};
				int[] Right = {state2.getPosx(), state2.getPosy()+1};
				int[] Up = {state2.getPosx()-1, state2.getPosy()};
				int[] Down = {state2.getPosx()+1, state2.getPosy()};
				
				newNode = new Node(state2, node, Operator.kill, depth, damage);

				
				warriorAdjacent(node);
				
				
				
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
//					System.out.println("a77aaaa");
					if(arr[0]==state.getPosx() && arr[1]==state.getPosy()-1){
						warrior_Left=true;
						warriors.remove(j);

						break;
					}
				}
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
//					System.out.println("a77aaaa");
					if(arr[0]==state.getPosx() + 1 && arr[1]==state.getPosy()){
						warrior_Down=true;
						warriors.remove(j);
						break;
					}
				}
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
//					System.out.println("a77aaaa");
					if(arr[0]==state.getPosx()-1 && arr[1]==state.getPosy()){
						warrior_Up=true;
						warriors.remove(j);
						break;
					}
				}
				for(int j=0;j<state2.getWarriorPositions().size();j++){
					int [] arr =state2.getWarriorPositions().get(j);
//					System.out.println("a77aaaa"+arr[0]+arr[1]);
					if(arr[0]==state.getPosx()  && arr[1]==state.getPosy()+1){
						warrior_Right=true;
						warriors.remove(j);
						break;
					}
				}
				
				if(thanosAdjacent(node.getState().getPosx(), node.getState().getPosy(), newNode)){
					damage+=5;
					
				}
				damage+=2;
				newNode = new Node(state2, node, Operator.kill, depth, damage);

				nodes.add(newNode);
//				visitedNodes.add(newNode);
				damage=0;

				break;
				
			
			case snap:
				snapped = true;
				if(damage < 100){				
				newNode = new Node(state, node, Operator.snap, depth, damage);
				nodes.add(newNode);
				}
				break;
				
			default:
				break;
			}
		}
		warriorLeft = false;
		warriorRight = false;
		warriorUp = false;
		warriorDown = false;
		return nodes;
	}
	
	private static boolean thanosAdjacent(int x, int y,Node node){
		int thanosx = node.getState().getThanosx2();
		int thanosy = node.getState().getThanosy2();

		if((thanosx==x-1 && thanosy==y) || (thanosx==x+1 && thanosy==y) || (thanosy==y-1 && thanosx==x) || (thanosy==y+1 && thanosx==x)){
			return true;
		}
		else{
			return false;
		}	
	}
	
	private static void warriorAdjacent(Node node){
		 boolean warriorLeft = false;
		 boolean warriorRight = false;
		 boolean warriorUp = false;
		 boolean warriorDown = false;
	int posx = node.getState().getPosx();
	int posy = node.getState().getPosy();
	ArrayList<Operator> operators = new ArrayList<Operator>();

	for(int i=0;i<node.getState().getWarriorPositions().size();i++){
		int [] arr =node.getState().warriorPositions.get(i);

		if(arr[0]==posx  && arr[1]==posy+1){
			warriorRight=true;
		}
		
		if(arr[0]==posx  && arr[1]==posy-1){
			warriorLeft=true;
		}
		
		if(arr[0]==posx -1 && arr[1]==posy){
			warriorUp=true;
		}
		
		if(arr[0]==posx + 1 && arr[1]==posy){
			warriorDown=true;
		}	
	}	
	}
}
