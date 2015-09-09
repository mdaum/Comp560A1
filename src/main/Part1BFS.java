package main;

import java.util.ArrayList;

public class Part1BFS extends Searcher{
	
	public Part1BFS(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY,int goalX,int goalY) {
		super(CharMaze, Nodes, startX, startY,goalX,goalY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<char[]> search() {
		boolean done=false;
		while(!done){
			if(frontier.isEmpty())return null;
			MazeNode current=dequeue();
			for (MazeNode n : current.getAdjacentNodes()) {
				if(!n.visited&&!n.infrontier){
					n.predecessor=current;
					if(n.goal){
						//System.out.println("I FOUND IT");
						int cost=1;
						MazeNode curr=n.predecessor;
						while(curr.predecessor!=null){
							solution.get(curr.row)[curr.column]='.';
							curr=curr.predecessor;
							cost++;
						}
						System.out.println("Cost to get here is " + cost); //commenting this out since we don't track this for greedy and we are apparently checking based on nodes expanded
						System.out.println("Nodes expanded is " + numNodesExpanded); 
						return solution;
					}
					enqueue(n);
					n.infrontier=true;
				}
			}
			
		}
		return null;
	}
	public void enqueue(MazeNode N){
		frontier.add(N);
		//solution.get(N.row)[N.column]='F';
	}
	public MazeNode dequeue(){
		numNodesExpanded++; //ADDED BY TYLER
		frontier.get(0).infrontier=false;
		frontier.get(0).visited=true;
		//solution.get(frontier.get(0).row)[frontier.get(0).column]='C';
		return frontier.remove(0);
	}

}
