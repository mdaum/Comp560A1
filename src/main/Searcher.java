package main;

import java.util.ArrayList;

public abstract class Searcher {
	ArrayList<MazeNode> frontier = new ArrayList<MazeNode>();
	char[][] charMaze;
	MazeNode[][] nodes;
	int startX;
	int startY;
	public Searcher(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY){
		charMaze = new char[CharMaze.size()][CharMaze.get(0).length];
		nodes=Nodes;
		this.startX=startX;
		this.startY=startY;
	}
	public abstract char[][] search();
}
