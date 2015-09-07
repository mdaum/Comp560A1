package main;

import java.util.ArrayList;

public abstract class Searcher {
	ArrayList<MazeNode> frontier = new ArrayList<MazeNode>();
	ArrayList<char[]> solution;
	MazeNode[][] nodes;
	int startX;
	int startY;
	int goalX;
	int goalY;
	public Searcher(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY,int goalX,int goalY){
		solution= deepclone(CharMaze);
		nodes=Nodes;
		this.startX=startX;
		this.startY=startY;
		frontier.add(nodes[startX][startY]);
		frontier.get(0).infrontier=true;
		numNodesExpanded = 1; //set to 1 since the start node is by default expanded
		this.goalX=goalX;
		this.goalY=goalY;
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
