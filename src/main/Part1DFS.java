package main;

import java.util.ArrayList;

public class Part1DFS extends Searcher{
	
	public Part1DFS(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY) {
		super(CharMaze, Nodes, startX, startY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char[][] search() {
		
		return null;
	}
	public void enqueue(MazeNode N){
		frontier.add(N);
	}
	public MazeNode dequeue(){
		return frontier.remove(0);
	}

}
