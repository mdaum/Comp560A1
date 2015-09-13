package main;

import java.util.ArrayList;

public abstract class Searcher {
	ArrayList<MazeNode> frontier = new ArrayList<MazeNode>();
	ArrayList<char[]> solution;
	MazeNode[][] nodes;
	int startColumn;
	int startRow;
	int goalColumn;
	int goalRow;
	int numNodesExpanded;
	public Searcher(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startY, int startX,int goalX,int goalY){//swapped startY and startX to match
		solution= deepclone(CharMaze);
		nodes=Nodes;
		this.startColumn=startX;
		this.startRow=startY;
		frontier.add(nodes[startY][startX]);//[startX][startY] is the wrong order. it is [ROW][COLUMN]
		frontier.get(0).infrontier=true;
		numNodesExpanded = 1; //set to 1 since the start node is by default expanded
		this.goalColumn=goalX;
		this.goalRow=goalY;
	}
	public abstract ArrayList<char[]> search();
	public ArrayList<char[]> deepclone(ArrayList<char[]> original){
		ArrayList<char[]> toReturn = new ArrayList<char[]>();
		for(int i=0;i<original.size();i++){
			toReturn.add(new char[original.get(0).length]);
			for(int j=0;j<original.get(0).length;j++){
				toReturn.get(i)[j]=original.get(i)[j];
			}
		}
		return toReturn;
	}
}
