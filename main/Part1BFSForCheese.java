package main;

import java.util.ArrayList;

public class Part1BFSForCheese extends Searcher{
	
	public Part1BFSForCheese(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY,int goalX,int goalY) {
		super(CharMaze, Nodes, startX, startY,goalX,goalY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<char[]> search()
	{
		return null;
	}
	public int search2() {
		boolean done=false;
		while(!done){
			if(frontier.isEmpty())return 0;
			MazeNode current=dequeue();
			for (MazeNode n : current.getAdjacentNodes()) {
				if(!n.visited&&!n.infrontier){
					n.predecessor=current;
					if(solution.get(n.row)[n.column] == '.'){
						//System.out.println("I FOUND IT");
						int cost=1;
						MazeNode curr=n.predecessor;
						while(curr.predecessor!=null){
							solution.get(curr.row)[curr.column]='.';
							curr=curr.predecessor;
							cost++;
						}
						return cost;
					}
					
					enqueue(n);
					n.infrontier=true;
				}
			}
			
		}
		return 0; //actually this never gets reached, but java will complain otherwise
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
