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
			if(!priorityQueueInOrder()){ //this is just for debugging purposes
				System.out.println("GOODBYE WORLD");
				System.exit(0);
			}
			if(frontier.isEmpty())return null;
			MazeNode current=dequeue();
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
		solution.get(frontier.get(0).row)[frontier.get(0).column]='C';// this is just for debugging purposes. C is for EXPLORED/VISITED/CHECKED/WHATEVER
		return frontier.remove(0);
	}
	public void dodaheuristic(MazeNode n){
		int Mdist=Math.abs(goalY-n.column)+Math.abs(goalX-n.row);
		System.out.println(n.toString()+" is "+Mdist+" away");// this is just for debugging purposes
		n.heuristicvalue=Mdist;
	}
	public boolean priorityQueueInOrder(){
		for(int i=0;i<frontier.size()-1;i++){
			if(frontier.get(i).heuristicvalue>frontier.get(i+1).heuristicvalue)
				return false;
		}
		return true;
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
				hi=mid-1;
			}
			else{
				lo=mid+1;
			}
		}
		/*if(f.get(mid).heuristicvalue<=h){
			return mid+1;	
		}
		else return mid;*/
		if(f.get(lo).heuristicvalue<h){ //this is hacky and icky but it works
			if(f.get(hi).heuristicvalue>=h)
				return hi;
			else return hi+1;
		}
		else
			return lo;
	}

}
