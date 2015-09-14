package main;

import java.util.ArrayList;

public class Part3CheeseSearch3 extends Searcher{
	ArrayList<MazeNode> cheeses = new ArrayList<MazeNode>();
	int lastCheeseFound=49;//ascii values are afoot. 49 means 1
	int[][] interCheeseDistances;
	ArrayList<MazeNode> arrayOfGoalPaths;
	public Part3CheeseSearch3(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startY, int startX, ArrayList<MazeNode> CheeseList){
		super(CharMaze,Nodes,startY,startX,-1,-1);//we will set the goal soon. It will be a specific cheese.
		MazeNode node = frontier.get(0);
		node.costOfBestPathHere = 0;
		cheeses = CheeseList;
		interCheeseDistances = new int[cheeses.size()][cheeses.size()];
		arrayOfGoalPaths = new ArrayList<MazeNode>();
	}
	public ArrayList<char[]> search(){
		MazeNode node = null;
		while(!cheeses.isEmpty()){
			node = dequeFrontierNode();
			pickCheeseGoal(node);//here is where we set the goal
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
				//System.out.println("Found goal at " + goalColumn + " " + goalRow);
				//System.out.println("Copied node at " + node.column + "   " + node.predecessor.row);
				arrayOfGoalPaths.add(deepCopyJustPredecessors(node,new MazeNode(node.row,node.column,node.goal)));
				wipeNodesExceptFor(node);
				node.predecessor = null;
				//pick the next cheese goal
				startColumn=node.column;
				startRow=node.row;
				if(!cheeses.isEmpty())
					pickCheeseGoal(node);
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
	public void pickCheeseGoal(MazeNode node){
		for (int i=0;i<cheeses.size()-1;i++) {
			MazeNode target= cheeses.get(i);
			int val=Math.abs(node.column-target.column)+Math.abs(node.row-target.row);
			for(int j=0;j<i;j++){
				MazeNode target2=cheeses.get(j);
				val+=Math.abs(target.column-target2.column)+Math.abs(target.row-target2.row);
			}
			for(int k=i+1;k<cheeses.size()-1;k++){
				MazeNode target3 = cheeses.get(k);
				val+=Math.abs(target.column-target3.column)+Math.abs(target.row-target3.row);
			}
			target.heuristicvalue=val;
		}
		int min=Integer.MAX_VALUE;
		MazeNode chosen = null;
		for (MazeNode cheese : cheeses) {
			if(min>cheese.heuristicvalue)
				min=cheese.heuristicvalue;
				chosen=cheese;
		}
		goalRow=chosen.row;
		goalColumn=chosen.column;
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
