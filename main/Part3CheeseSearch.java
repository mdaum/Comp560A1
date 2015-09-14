package main;

import java.util.ArrayList;

public class Part3CheeseSearch extends Searcher{
	ArrayList<MazeNode> cheeses = new ArrayList<MazeNode>();
	int lastCheeseFound=49;//ascii values are afoot. 49 means 1
	int[][] interCheeseDistances;
	ArrayList<MazeNode> arrayOfGoalPaths;
	public Part3CheeseSearch(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startY, int startX, ArrayList<MazeNode> CheeseList){
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
			if(cheeses.contains(node)){//we have hit our temporary goal (a specific cheese)
				cheeses.remove(node);
				solution.get(node.row)[node.column]=(char)lastCheeseFound;//ASCII VALUES AHOY 
				lastCheeseFound=lastCheeseFound<57||lastCheeseFound>=65?lastCheeseFound+1:65;
				arrayOfGoalPaths.add(deepCopyJustPredecessors(node,new MazeNode(node.row,node.column,node.goal)));
				wipeNodesExceptFor(node);
				node.predecessor = null;
				//pick the next cheese goal
				startColumn=node.column;
				startRow=node.row;
				if(cheeses.size() == 0)
				{
					break;
				}
			}
			for(MazeNode child : node.getAdjacentNodes())
				if(!child.infrontier&&!child.visited)
					enqueNode(node,child);
		}//if the while loop is done, we have found every cheese
		System.out.println("Cost to get here is " + node.costOfBestPathHere); //Hacking a solution for now
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

	public void enqueNode(MazeNode parent, MazeNode child)
	{
		child.costOfBestPathHere = parent.costOfBestPathHere + 1;
		child.heuristicvalue = computeHeuristic(child);
		child.predecessor = parent;
		frontier.add(child);
		child.infrontier=true;
	}
	
	public int computeHeuristic(MazeNode node)
	{
		//int result = bfs(node);
		//return result;
		return avgDistance(node);
	}
	
	public int avgDistance(MazeNode node)
	{
		int distance = 0;
		for(MazeNode cheese : cheeses)
		{
			distance += (Math.abs(node.column - cheese.column) + Math.abs(node.row + cheese.row));
		}
		return distance/cheeses.size();
	}
	public int bfs(MazeNode start)
	{
		ArrayList<MazeNode> frontier2 = new ArrayList<MazeNode>();
		ArrayList<MazeNode>  visitedList = new ArrayList<MazeNode>();
		ArrayList<Integer> pathCosts = new ArrayList<Integer>();
		frontier2.add(start);
		visitedList.add(start);
		pathCosts.add(1);
		while(solution.get(start.row)[start.column] != '.')
		{
			if(start.east != null && (!visitedList.contains(start.east)))
			{
				frontier2.add(start.east);
				pathCosts.add(pathCosts.get(0)+1);
			}
			if(start.north != null && (!visitedList.contains(start.north)))
			{
				frontier2.add(start.north);
				pathCosts.add(pathCosts.get(0)+1);
			}
			if(start.south != null && (!visitedList.contains(start.south)))
			{
				frontier2.add(start.south);
				pathCosts.add(pathCosts.get(0)+1);
			}
			if(start.west != null && (!visitedList.contains(start.west)))
			{
				frontier2.add(start.west);
				pathCosts.add(pathCosts.get(0)+1);
			}
			if(frontier2.size() == 0)
			{
				return 1;
			}
				start = frontier2.remove(0);
				pathCosts.remove(0);
				visitedList.add(start);
		}
		return pathCosts.get(0);
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
		return deepCopyJustPredecessors(node.predecessor,copyNode.predecessor);
		
	}
}
