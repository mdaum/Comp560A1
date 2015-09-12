package main;

import java.util.ArrayList;

/**
 * Gonna extend A* and change the heuristic and solution completion
 * gonna add a cheese list and stuff (the "Part1.java" main class that needs to be renamed
 * already parses cheese locations
 */
public class Part3CheeseSearch extends Part1AStar{//cheesus christ <- TEN OUTTA TEN
	ArrayList<MazeNode> cheeses = new ArrayList<MazeNode>();
	int lastCheeseFound=49;//ascii values are afoot. 49 means 1
	public Part3CheeseSearch(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY, ArrayList<MazeNode> CheeseList){
		super(CharMaze,Nodes,startX,startY,-1,-1);
		cheeses = CheeseList;
	}
	//overwrite this jazz
	public ArrayList<char[]> search()
	{
		MazeNode node = null;
		while(!frontier.isEmpty() && !cheeses.isEmpty())
		{
			node = dequeFrontierNode(); //Take a node off the frontier
			if(cheeses.contains(node))
			{
				//label the cheese as being found on the solution with a number
				solution.get(node.row)[node.column]=(char)lastCheeseFound;//ASCII VALUES AHOY 
				lastCheeseFound=lastCheeseFound<59||lastCheeseFound>=65?lastCheeseFound+1:65;
				//remove this cheese from the list of cheeses
				cheeses.remove(node);
				//empty out the frontier and clean info on every node EXCEPT this one
				frontier.clear();
				for(int i=0;i<nodes.length;i++){//row
					for(MazeNode chump : nodes[i]){//column (cell)
						if(chump!=node && chump!=null){
							chump.visited=false;
							chump.infrontier=false;
							chump.costOfBestPathHere=Integer.MAX_VALUE;
						}
					}
				}
			}
			
			node.visited = true; //Have now visited this node
			enqueChildren(node); //Add its children to the frontier
		}//we done diddly did it boyos
		System.out.println("Cost to get here is " + node.costOfBestPathHere);
		System.out.println("Nodes expanded is " + numNodesExpanded); 
		return null; //return null if frontier is ever empty
	}
	//overwrite this jazz (done)
	public void enqueNode(MazeNode parent, MazeNode child){
		child.costOfBestPathHere = parent.costOfBestPathHere + 1;
		//the heuristic calculation is now different because we have all dem chezes instead of a goal
		//child.heuristicvalue = (Math.abs(child.column - goalX) + Math.abs(child.row - goalY));
		//child.heuristicvalue=secondHeuristic(child);
		child.heuristicvalue=firstHeuristic(child);
		child.predecessor = parent;
		frontier.add(child);
	}
	//nearest cheese's max distance from other cheese
	public int firstHeuristic(MazeNode node){
		int distanceOfNearestCheeseFromFurthestOtherCheese = Integer.MAX_VALUE;
		MazeNode closestCheese = nearestCheese(node);
		for(MazeNode cheese: cheeses){
			if(Math.abs(cheese.row-closestCheese.row)+Math.abs(cheese.column-closestCheese.column)>distanceOfNearestCheeseFromFurthestOtherCheese)
				distanceOfNearestCheeseFromFurthestOtherCheese=Math.abs(cheese.row-closestCheese.row)+Math.abs(cheese.column-closestCheese.column);
		}
		return distanceOfNearestCheeseFromFurthestOtherCheese;
	}
	//node's shortest distance to a cheese
	public int secondHeuristic(MazeNode node){
		int bestDistance = Integer.MAX_VALUE;
		for(MazeNode cheese : cheeses){
			if(Math.abs(node.column-cheese.column)+Math.abs(node.row-cheese.row)<bestDistance)
				bestDistance=Math.abs(node.column-cheese.column)+Math.abs(node.row-cheese.row);
		}
		return bestDistance;
	}
	//return the cheese closest to this node
	public MazeNode nearestCheese(MazeNode node){
		MazeNode chosenOne;
		int minValue = Integer.MAX_VALUE;
		int idx = -1;
		for(int i = 0;i < cheeses.size();i++)
		{
			if(frontier.get(i).heuristicvalue + frontier.get(i).costOfBestPathHere < minValue)
			{
				minValue = frontier.get(i).heuristicvalue + frontier.get(i).costOfBestPathHere;
				idx = i;
			}
			if(frontier.get(i).heuristicvalue + frontier.get(i).costOfBestPathHere == minValue){
				if(secondHeuristic(frontier.get(i)) < secondHeuristic(frontier.get(idx)))
					chosenOne = frontier.get(i);
				else
					chosenOne = frontier.get(idx);
			}
		}
		chosenOne = frontier.get(idx);
		frontier.remove(idx);
		numNodesExpanded++;
		return chosenOne;
	}
}
