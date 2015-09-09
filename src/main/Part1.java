package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {
	static int startX;
	static int startY;
	static int goalX;
	static int goalY;
	public static void main(String args[]){
		System.out.println("Doing Part "+Integer.parseInt(args[0]));
		if(Integer.parseInt(args[0])==1){//chose to do part 1...will do all searches
		System.out.println("constructing graph based on args[1]");
		ArrayList<char[]> smallCharMaze = createCharMaze(args[1]);
		printCharMaze(smallCharMaze);
		MazeNode[][] nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
		createMazeGraph(smallCharMaze,nodes);
		System.out.println("Graph construction complete");
		System.out.println("BFS");
		Part1BFS goo = new Part1BFS(smallCharMaze, nodes, startX, startY,goalX,goalY);
		goo.search();
		System.out.println();
		printCharMaze(goo.solution);
		reset(nodes);
		System.out.println();
		System.out.println("DFS");
		Part1DFS shoe =new Part1DFS(smallCharMaze,nodes,startX,startY,goalX,goalY);
		shoe.search();
		printCharMaze(shoe.solution);
		reset(nodes);
		System.out.println();
		System.out.println("Greedy Best First");
		Part1GreedyBestFirst mrKrabs=new Part1GreedyBestFirst(smallCharMaze, nodes, startX, startY, goalX, goalY);
		mrKrabs.search();
		printCharMaze(mrKrabs.solution);
		reset(nodes);
		//Tyler Test Code starts here, Max:Tyler, I think since you use the Searcher.solution it is a deep copy of the txt file, so just reset nodes no need for new graph construction
		/*smallCharMaze = createCharMaze("mazeForGreedy.txt");
		nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
		createMazeGraph(smallCharMaze,nodes);*/ //commenting out for now, just reseting nodes instead since we have deep copy
		System.out.println();
		System.out.println("A*");
		Part1AStar deathStar = new Part1AStar(smallCharMaze,nodes, startX, startY, goalX, goalY);
		printCharMaze(deathStar.search());
		}
		else if(Integer.parseInt(args[0])==2){//doing part 2
			System.out.println("constructing graph in which A* wins");
			ArrayList<char[]> smallCharMaze = createCharMaze("mazeForAStar.txt");
			printCharMaze(smallCharMaze);
			MazeNode[][] nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);
			System.out.println("Graph construction complete");
			System.out.println();
			System.out.println("Greedy Best First");
			Part1GreedyBestFirst mrKrabs=new Part1GreedyBestFirst(smallCharMaze, nodes, startX, startY, goalX, goalY);
			mrKrabs.search();
			printCharMaze(mrKrabs.solution);
			reset(nodes);
			//Tyler Test Code starts here, Max:Tyler, I think since you use the Searcher.solution it is a deep copy of the txt file, so just reset nodes no need for new graph construction
			/*smallCharMaze = createCharMaze("mazeForGreedy.txt");
			nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);*/ //commenting out for now, just reseting nodes instead since we have deep copy
			System.out.println();
			System.out.println("A*");
			Part1AStar deathStar = new Part1AStar(smallCharMaze,nodes, startX, startY, goalX, goalY);
			printCharMaze(deathStar.search());
			System.out.println("A* wins... now constructing graph where Greedy wins");
			try {
				System.out.println("sleeping");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//sleeping so we can make sure memory clears
			smallCharMaze = createCharMaze("mazeForGreedy.txt");
			printCharMaze(smallCharMaze);
			nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);
			System.out.println("Graph construction complete");
			System.out.println();
			System.out.println("Greedy Best First");
			mrKrabs=new Part1GreedyBestFirst(smallCharMaze, nodes, startX, startY, goalX, goalY);
			mrKrabs.search();
			printCharMaze(mrKrabs.solution);
			reset(nodes);
			//Tyler Test Code starts here, Max:Tyler, I think since you use the Searcher.solution it is a deep copy of the txt file, so just reset nodes no need for new graph construction
			/*smallCharMaze = createCharMaze("mazeForGreedy.txt");
			nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);*/ //commenting out for now, just reseting nodes instead since we have deep copy
			System.out.println();
			System.out.println("A*");
			deathStar = new Part1AStar(smallCharMaze,nodes, startX, startY, goalX, goalY);
			printCharMaze(deathStar.search());
		}
		
	}
	//this returns the ArrayList of char[]s that make up a representation of the .txt files
	public static ArrayList<char[]> createCharMaze(String filePath){
		ArrayList<char[]> charMazeRows = new ArrayList<char[]>();
		try{
			Scanner s = new Scanner(new File(filePath));
			while(s.hasNextLine()){
				charMazeRows.add(s.nextLine().toCharArray());
			}
			s.close();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return charMazeRows;
	}
	//this just prints out an ArrayList of char[]s
	public static void printCharMaze(ArrayList<char[]> charMaze){
		for(char[] row : charMaze){
			for(char c : row){
				System.out.print(c);
			}
			System.out.println();
		}
	}
	//this creates an actual graph structure out of an empty double array of nodes 
	//and the parsed form of a .txt in the form of that ArrayList of char[]s
	public static void createMazeGraph(ArrayList<char[]> charMaze, MazeNode[][] nodes){
		for(int i=0;i<charMaze.size();i++){
			for(int j=0;j<charMaze.get(i).length;j++){
				if(charMaze.get(i)[j]!='%'){
					if(charMaze.get(i)[j]=='S'){
						startX=i;
						startY=j;
					}
					if(charMaze.get(i)[j]=='G'){
						goalX=i;
						goalY=j;
					}
					nodes[i][j] = new MazeNode(i,j,charMaze.get(i)[j]=='G');
					if(i>0 && charMaze.get(i-1)[j]!='%'){//we can check for edges with tiles above us
						nodes[i][j].north=nodes[i-1][j];
						nodes[i-1][j].south=nodes[i][j];
					}
					if(j>0 && charMaze.get(i)[j-1]!='%'){//we can check for edges with tiles to our left
						nodes[i][j].west=nodes[i][j-1];
						nodes[i][j-1].east=nodes[i][j];
					}
				}
			}
		}
	}
	public static ArrayList<MazeNode> createCheeseList(ArrayList<char[]> charMaze, MazeNode[][] nodes){
		ArrayList<MazeNode> cheeseList = new ArrayList<MazeNode>();
		for(int i=0;i<charMaze.size();i++){
			for(int j=0;j<charMaze.get(i).length;j++){
				if(charMaze.get(i)[j]=='.'){
					cheeseList.add(nodes[i][j]);
				}
			}
		}
		return cheeseList;
	}
	//the way we're doing things doesn't use special data structures to store the explored set
	//or even the frontier. Instead we just flag the nodes themselves with booleans.
	//since we're not recreating the graph for each search performed, we do need to
	//go through the graph and "reset" it, unflagging every node as not being part
	//of the frontier or explored set
	public static void reset(MazeNode[][] Graph){
		for (MazeNode[] mazeNodes : Graph) {
			for (MazeNode mazeNode : mazeNodes) {
				if(mazeNode!=null){
				mazeNode.visited=false;
				mazeNode.infrontier=false;
				}
			}
		}
	};

}
