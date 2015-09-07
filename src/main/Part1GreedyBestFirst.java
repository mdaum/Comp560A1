package main;

import java.util.ArrayList;

public class Part1GreedyBestFirst extends Searcher{
	public Part1GreedyBestFirst(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startX, int startY,int goalX,int goalY) {
		super(CharMaze, Nodes, startX, startY,goalX,goalY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<char[]> search() {
		boolean done=false;
		while(!done){
			Part1.printCharMaze(solution); // this is just for debugging purposes
			if(frontier.isEmpty())return null;
			MazeNode current=dequeue();
			for (MazeNode n : current.getAdjacentNodes()) {
				if(!n.visited&&!n.infrontier){
					n.predecessor=current;
					if(n.goal){
						System.out.println("I FOUND IT");
						System.out.println("Number of nodes expanded is " + numNodesExpanded);
						MazeNode curr=n.predecessor;
						while(curr.predecessor!=null){
							solution.get(curr.row)[curr.column]='.';
							curr=curr.predecessor;
						}
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
		solution.get(N.row)[N.column]='F';// this is just for debugging purposes. F is for FRONTIER
		dodaheuristic(N);
		if(frontier.size()==0){
			frontier.add(N);
			return;
		}
		frontier.add(binsearch(N.heuristicvalue,frontier),N);
	}
	public MazeNode dequeue(){
		frontier.get(0).infrontier=false;
		frontier.get(0).visited=true;
		numNodesExpanded++; //ADDED BY TYLER
		solution.get(frontier.get(0).row)[frontier.get(0).column]='C';// this is just for debugging purposes. C is for EXPLORED/VISITED/CHECKED/WHATEVER
		return frontier.remove(0);
	}
	public void dodaheuristic(MazeNode n){
		int Mdist=Math.abs(goalX-n.column)+Math.abs(goalY-n.row);
		n.heuristicvalue=Mdist;
	}
	public int binsearch(int h,ArrayList<MazeNode> f){
		int hi=f.size()-1;
		int lo=0;
		int mid =lo+((hi-lo)/2) ; //about to overwrite java is stupid
		while(lo<hi){
			mid=lo+((hi-lo)/2);
			if(f.get(mid).heuristicvalue==h){
				return mid;
			}
			if(h<f.get(mid).heuristicvalue){
				hi=mid;
			}
			else{
				lo=mid;
			}
		}
		if(f.get(mid).heuristicvalue<=h){
		return mid+1;	
		}
		else return mid;	
	}

}
