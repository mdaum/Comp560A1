package main;

import java.util.ArrayList;

public class ManSearch extends Searcher{
	ArrayList<MazeNode> cheeses = new ArrayList<MazeNode>();
	int lastCheeseFound=49;//ascii values are afoot. 49 means 1
	int[][] interCheeseDistances;
	ArrayList<MazeNode> arrayOfGoalPaths;
	public ManSearch(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startY, int startX, ArrayList<MazeNode> CheeseList){
		super(CharMaze,Nodes,startY,startX,-1,-1);//we will set the goal soon. It will be a specific cheese.
		MazeNode node = frontier.get(0);
		node.costOfBestPathHere = 0;
		cheeses = CheeseList;
		pickCheeseGoal();//here is where we set the goal
		arrayOfGoalPaths = new ArrayList<MazeNode>();
	}
	public ArrayList<char[]> search(){
		MazeNode node = null;
		while(!cheeses.isEmpty()){
			node = dequeFrontierNode();
			if(node.row==goalRow && node.column==goalColumn){//we have hit our temporary goal (a specific cheese)
				//knock off every cheese that we walked over
				MazeNode nodeOnPath = node;
				ArrayList<MazeNode> cheeseList = new ArrayList<MazeNode>();
				while(nodeOnPath!=null){
					if(cheeses.contains(nodeOnPath)){
						cheeseList.add(nodeOnPath);
						cheeses.remove(nodeOnPath);
					}
					nodeOnPath=nodeOnPath.predecessor;
				}
				for(int i=cheeseList.size()-1;i>=0;i--){
					solution.get(cheeseList.get(i).row)[cheeseList.get(i).column]=(char)lastCheeseFound;//ASCII VALUES AHOY 
					lastCheeseFound=lastCheeseFound<57||lastCheeseFound>=65?lastCheeseFound+1:65;
				}
				cheeses.remove(node);
				//don't allow path to get pulled out of control
				node.predecessor=null;
				//we have to wipe every other MazeNode but this one clean as we "restart"
				System.out.println("Found goal at " + goalColumn + " " + goalRow);
				System.out.println("Copied node at " + node.column + "   " + node.predecessor.row);
				arrayOfGoalPaths.add(deepCopyJustPredecessors(node,new MazeNode(node.row,node.column,node.goal)));
				wipeNodesExceptFor(node);
				node.predecessor = null;
				//pick the next cheese goal
				startColumn=node.column;
				startRow=node.row;
				if(!cheeses.isEmpty())
					pickCheeseGoal();
			}
			for(MazeNode child : node.getAdjacentNodes())
				if(!child.infrontier&&!child.visited)
					enqueNode(node,child);
		}//if the while loop is done, we have found every cheese
		System.out.println("Cost to get here is " + nodes[goalRow][goalColumn].costOfBestPathHere); //Hacking a solution for now
		System.out.println("Nodes expanded is " + numNodesExpanded); 
		return null;
	}
	//wipe every node other than the goal cheese we just hit
	public void wipeNodesExceptFor(MazeNode node){
		for(int i=0;i<nodes.length;i++){//row
			for(MazeNode chump : nodes[i]){//column (cell)
				if(chump!=node && chump!=null){
					chump.visited=false;
					chump.infrontier=false;
					chump.predecessor=null;
					frontier.clear();//most important line
				}
			}
		}
	}
	//currently uses new aspects of MazeNode that I added just for the cheese distances. Ugh.
	public void pickCheeseGoal(){
		int greatestDistance=Integer.MIN_VALUE;
		MazeNode candidateCheese=cheeses.get(0);
		MazeNode a,b;
		for(int i=0;i<cheeses.size()-1;i++){
			a=cheeses.get(i);
			for(int j=i+1;j<cheeses.size();j++){
				b=cheeses.get(j);
				if(greatestDistance<Math.abs(a.row-b.row)+Math.abs(a.column-b.column)){//one of these is our candidateCheese
					greatestDistance=Math.abs(a.row-b.row)+Math.abs(a.column-b.column);
					//we have to pick the one closer to start
					if(Math.abs(a.row-startRow)+Math.abs(a.column-startColumn)<Math.abs(b.row-startRow)+Math.abs(b.column-startColumn))
						candidateCheese=a;
					else
						candidateCheese=b;
				}
			}
		}
		goalRow=candidateCheese.row;
		goalColumn=candidateCheese.column;
	}
	public void enqueNode(MazeNode parent, MazeNode child)
	{
		child.costOfBestPathHere = parent.costOfBestPathHere + 1;
		System.out.println(child.costOfBestPathHere+" "+child.row+","+child.column);
		child.heuristicvalue = (Math.abs(child.column - goalColumn) + Math.abs(child.row - goalRow));
		child.predecessor = parent;
		frontier.add(child);
		child.infrontier=true;
	}
	public MazeNode dequeFrontierNode()
	{
		int minValue = Integer.MAX_VALUE;
		int idx = 0;
		for(int i = 0;i < frontier.size();i++)
		{
			if(frontier.get(i).heuristicvalue + frontier.get(i).costOfBestPathHere < minValue)
			{
				minValue = frontier.get(i).heuristicvalue + frontier.get(i).costOfBestPathHere;
				idx = i;
			}
		}
		MazeNode chosenOne = frontier.get(idx);
		frontier.remove(idx);
		numNodesExpanded++;
		chosenOne.visited=true;
		chosenOne.infrontier=false;
		return chosenOne;
	}
	
	public MazeNode deepCopyJustPredecessors(MazeNode node,MazeNode copyNode)
	{
		if(node.predecessor == null)
		{
			return copyNode;
		}
		MazeNode predecessorCopy = new MazeNode(node.predecessor.row,node.predecessor.column,node.predecessor.goal);
		copyNode.predecessor = predecessorCopy;
		predecessorCopy.successor = copyNode;
		System.out.println("Copied node at " + copyNode.predecessor.column + "   " + copyNode.predecessor.row);
		return deepCopyJustPredecessors(node.predecessor,copyNode.predecessor);
		
	}
}
