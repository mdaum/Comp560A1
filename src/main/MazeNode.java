package main;

import java.util.ArrayList;

public class MazeNode {
	int row,column;
	int costOfBestPathHere=Integer.MAX_VALUE;//Tyler use for A*?
	int heuristicvalue;//will be assigned on the fly in search implementation...
	boolean visited=false;//true if part of the explored set
	boolean infrontier=false;
	boolean goal=false;
	//predecessor is the node that expanded it: defines the solution path for backtracking
	MazeNode north,south,east,west,predecessor;
	public MazeNode(int Row, int Column, boolean Goal){
		row=Row;
		column=Column;
		goal=Goal;
	}
	public ArrayList<MazeNode> getAdjacentNodes(){
		ArrayList<MazeNode> adjacentNodes = new ArrayList<MazeNode>();
		if(north!=null)
			adjacentNodes.add(north);
		if(south!=null)
			adjacentNodes.add(south);
		if(east!=null)
			adjacentNodes.add(east);
		if(west!=null)
			adjacentNodes.add(west);
		return adjacentNodes;
	}
	public String toString(){
		return row+" "+column+" visited="+visited+" infrontier="+infrontier+" heuristic="+heuristicvalue;
	}
}
