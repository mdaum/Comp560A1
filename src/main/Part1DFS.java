package main;

import java.util.ArrayList;

public class Part1DFS extends Searcher{
	
	public Part1DFS(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY,int goalX,int goalY) {
		super(CharMaze, Nodes, startX, startY,goalX,goalY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<char[]> search() {
		boolean done=false;
		while(!done){
			//System.out.println();System.out.println();System.out.println();
			//Part1.printCharMaze(solution);
			if(frontier.isEmpty())return null;
			MazeNode current=pop();
			for (MazeNode n : current.getAdjacentNodes()) {
				if(!n.visited&&!n.infrontier){
					n.predecessor=current;
					if(n.goal){
						System.out.println("I FOUND IT");
						MazeNode curr=n.predecessor;
						while(curr.predecessor!=null){
							solution.get(curr.row)[curr.column]='.';
							curr=curr.predecessor;
						}
						return solution;
					}
					push(n);
					n.infrontier=true;
				}
			}
			
		}
		return null;
	}
	public void push(MazeNode N){
		frontier.add(N);
		//solution.get(N.row)[N.column]='F';
	}
	public MazeNode pop(){
		frontier.get(frontier.size()-1).infrontier=false;
		frontier.get(frontier.size()-1).visited=true;
		//solution.get(frontier.get(frontier.size()-1).row)[frontier.get(frontier.size()-1).column]='C';
		return frontier.remove(frontier.size()-1);
	}

}
