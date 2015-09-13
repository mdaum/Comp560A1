package main;

import java.util.ArrayList;

public class Part1AStar extends Searcher
{
	/**
	 * Constructor for an A* Searcher
	 * @param CharMaze - character representation of the maze
	 * @param Nodes - graph representation of mades made up of MazeNodes
	 * @param startX - the x coordinate of the start node
	 * @param startY - the y coordinate of the start node
	 * @param goalX - the x coordinate of the goal node
	 * @param goalY - the y coordinate of the goal node
	 */
	public Part1AStar(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY,int goalX,int goalY)
	{
		super(CharMaze, Nodes, startX, startY, goalX, goalY);
		
		//Get first node in frontier (start node) and set its properties
		MazeNode node = frontier.get(0);
		node.heuristicvalue = (Math.abs(node.column - goalX) + Math.abs(node.row - goalY));
		node.costOfBestPathHere = 0;
		//frontier.remove(0);
		//frontier.add(node);
	}
	
	public ArrayList<char[]> search()
	{
		while(!frontier.isEmpty())
		{
			MazeNode node = dequeFrontierNode(); //Take a node off the frontier
			if(node.goal) //If it is the goal node, compute the solution maze and return it
			{
				computeSolution(node);
				return solution;
			}
			
			node.visited = true; //Have now visited this node
			enqueChildren(node); //Add its children to the frontier
		}
		return null; //return null if frontier is ever empty
	}
	
	/**
	 * Method to pull a node off the frontier.  Iterate through the frontier and finds the node with the smallest
	 * value of heuristic value + path cost
	 * @return - the MazeNode with the smallest cost
	 */
	public MazeNode dequeFrontierNode()
	{
		int minValue = Integer.MAX_VALUE;
		int idx = -1;
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
		return chosenOne;
	}
	
	/**
	 * Adds the children of the parent node to the frontier, using multiple calls to 
	 * enqueNode()
	 * @param parent - the parent whose children is to be added
	 */
	public void enqueChildren(MazeNode parent)
	{
		if(parent.north != null && !parent.north.visited)
		{
			enqueNode(parent,parent.north);
		}
		if(parent.south != null && !parent.south.visited)
		{
			enqueNode(parent,parent.south);
		}
		if(parent.east != null && !parent.east.visited)
		{
			enqueNode(parent,parent.east);
		}
		if(parent.west != null && !parent.west.visited)
		{
			enqueNode(parent,parent.west);
		}
	}
	
	/**
	 * Adds a node to the frontier, and sets the relevant properties
	 * @param parent - the parent of the child node, needed here to set predecessor relationships
	 * @param child - the node to be added to the frontier
	 */
	public void enqueNode(MazeNode parent, MazeNode child)
	{
		child.costOfBestPathHere = parent.costOfBestPathHere + 1;
		child.heuristicvalue = (Math.abs(child.column - goalColumn) + Math.abs(child.row - goalRow));
		child.predecessor = parent;
		frontier.add(child);
	}
	
	/**
	 * Computes the solution to the maze.  Starts at goal, and iterates through predecessors until
	 * reaching start, replacing each blank space at that nodes' position with a period
	 * @param goal - the goal node that has been reached
	 */
	public void computeSolution(MazeNode goal)
	{
		System.out.println("Cost to get here is " + goal.costOfBestPathHere); //commenting this out since we don't track this for greedy and we are apparently checking based on nodes expanded
		System.out.println("Nodes expanded is " + numNodesExpanded); 
		while(goal.predecessor.predecessor != null)
		{
			goal = goal.predecessor;
			solution.get(goal.row)[goal.column] = '.';
		}
	}

}
