package main;

import java.util.ArrayList;

public class Part1AStar extends Searcher
{
	private int numNodesExpanded = 1;
	
	public Part1AStar(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY,int goalX,int goalY)
	{
		super(CharMaze, Nodes, startX, startY, goalX, goalY);
		MazeNode node = frontier.get(0);
		node.heuristicvalue = (Math.abs(node.column - goalX) + Math.abs(node.row - goalY));
		node.costOfBestPathHere = 0
				;
		frontier.remove(0);
		frontier.add(node);
	}
	
	public ArrayList<char[]> search()
	{
		boolean done = false;
		while(!done)
		{
			if(frontier.isEmpty())
			{
				return null;
			}
			
			MazeNode node = dequeFrontierNode();
			if(node.goal)
			{
				computeSolution(node);
				return solution;
			}
			
			node.visited = true;
			enqueChildren(node);
		}
		return null;
	}
	
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
		return chosenOne;
	}
	
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
	
	public void enqueNode(MazeNode parent, MazeNode child)
	{
		child.costOfBestPathHere = parent.costOfBestPathHere + 1;
		child.heuristicvalue = (Math.abs(child.column - goalX) + Math.abs(child.row - goalY));
		child.predecessor = parent;
		numNodesExpanded++;
		frontier.add(child);
	}
	
	public void computeSolution(MazeNode goal)
	{
		System.out.println("Cost to get here is " + goal.costOfBestPathHere);
		System.out.println("Nodes expanded is " + numNodesExpanded); 
		while(goal.predecessor.predecessor != null)
		{
			goal = goal.predecessor;
			solution.get(goal.row)[goal.column] = '.';
		}
	}

}
