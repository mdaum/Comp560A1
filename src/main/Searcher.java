package main;

import java.util.ArrayList;

public abstract class Searcher {
	char[][] charMaze;
	MazeNode[][] nodes;
	public Searcher(ArrayList<char[]> CharMaze, MazeNode[][] Nodes){
		charMaze = new char[CharMaze.size()][CharMaze.get(0).length];
		nodes=Nodes;
	}
	public abstract char[][] search();
}
