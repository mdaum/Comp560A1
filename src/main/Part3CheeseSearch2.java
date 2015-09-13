package main;

import java.util.ArrayList;

public class Part3CheeseSearch2 extends Searcher{
	ArrayList<MazeNode> cheeses = new ArrayList<MazeNode>();
	int lastCheeseFound=49;//ascii values are afoot. 49 means 1
	int[][] interCheeseDistances;
	public Part3CheeseSearch2(ArrayList<char[]> CharMaze, MazeNode[][] Nodes,int startY, int startX, ArrayList<MazeNode> CheeseList){
		super(CharMaze,Nodes,startY,startX,-1,-1);//we will set the goal soon. It will be a specific cheese.
		MazeNode node = frontier.get(0);
		node.costOfBestPathHere = 0;
		cheeses = CheeseList;
		interCheeseDistances = new int[cheeses.size()][cheeses.size()];
		pickCheeseGoal();//here is where we set the goal
	}
	public ArrayList<char[]> search(){
		MazeNode node = null;
		while(!cheeses.isEmpty()){
			node = dequeFrontierNode();
			if(cheeses.contains(node)){//we can knock this cheese off the list
				cheeses.remove(node);
				//also we have to label this cheese on the text array as being the nth one found
				solution.get(node.row)[node.column]=(char)lastCheeseFound;//ASCII VALUES AHOY 
				lastCheeseFound=lastCheeseFound<57||lastCheeseFound>=65?lastCheeseFound+1:65;
				//wipe this cheese from every other cheese's memorization of inter-cheese distances
			}
			if(node.row==goalRow && node.column==goalColumn){//we have hit our temporary goal (a specific cheese)
				//we have to wipe every other MazeNode but this one clean as we "restart"
				wipeNodesExceptFor(node);
				//pick the next cheese goal
				startColumn=node.column;
				startRow=node.row;
				if(!cheeses.isEmpty())
					pickCheeseGoal();
			}
			for(MazeNode child : node.getAdjacentNodes())
				if(!child.infrontier&&!child.visited)
					enqueNode(node,child);
		}//if the while loop is done, we have found every cheese
		System.out.println("Cost to get here is " + nodes[goalRow][goalColumn].costOfBestPathHere); //Hacking a solution for now
		System.out.println("Nodes expanded is " + numNodesExpanded); 
		return null;
	}
	//wipe every node other than the goal cheese we just hit
	public void wipeNodesExceptFor(MazeNode node){
		for(int i=0;i<nodes.length;i++){//row
			for(MazeNode chump : nodes[i]){//column (cell)
				if(chump!=node && chump!=null){
					chump.visited=false;
					chump.infrontier=false;
					chump.heuristicvalue=Integer.MAX_VALUE;//probably unnecessary
					frontier.clear();//most important line
				}
			}
		}
	}
	//currently uses new aspects of MazeNode that I added just for the cheese distances. Ugh.
	public void pickCheeseGoal(){
		int greatestDistance=Integer.MIN_VALUE;
		MazeNode candidateCheese=cheeses.get(0);
		MazeNode a,b;
		for(int i=0;i<cheeses.size()-1;i++){
			a=cheeses.get(i);
			for(int j=i+1;j<cheeses.size();j++){
				b=cheeses.get(j);
				if(greatestDistance<Math.abs(a.row-b.row)+Math.abs(a.column-b.column)){//one of these is our candidateCheese
					greatestDistance=Math.abs(a.row-b.row)+Math.abs(a.column-b.column);
					//we have to pick the one closer to start
					if(Math.abs(a.row-startRow)+Math.abs(a.column-startColumn)<Math.abs(b.row-startRow)+Math.abs(b.column-startColumn))
						candidateCheese=a;
					else
						candidateCheese=b;
				}
			}
		}
		goalRow=candidateCheese.row;
		goalColumn=candidateCheese.column;
	}
	public void enqueNode(MazeNode parent, MazeNode child)
	{
		child.costOfBestPathHere = parent.costOfBestPathHere + 1;
		System.out.println(child.costOfBestPathHere);
		child.heuristicvalue = (Math.abs(child.column - goalColumn) + Math.abs(child.row - goalRow));
		child.predecessor = parent;
		frontier.add(child);
		child.infrontier=true;
	}
	public MazeNode dequeFrontierNode()
	{
		int minValue = Integer.MAX_VALUE;
		int idx = 0;
		for(int i = 0;i < frontier.size();i++)
		{
			if(frontier.get(i).heuristicvalue + frontier.get(i).costOfBestPathHere < minValue)
			{
				minValue = frontier.get(i).heuristicvalue + frontier.get(i).costOfBestPathHere;
				idx = i;
			}
		}
		MazeNode chosenOne = frontier.get(idx);
		frontier.remove(idx);
		numNodesExpanded++;
		chosenOne.visited=true;
		chosenOne.infrontier=false;
		return chosenOne;
	}
}
