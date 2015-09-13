package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
	static int startRow;
	static int startColumn;
	static int goalRow;
	static int goalColumn;
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
			Part1BFS goo = new Part1BFS(smallCharMaze, nodes, startRow, startColumn,goalRow,goalColumn);
			goo.search();
			System.out.println();
			printCharMaze(goo.solution);
			reset(nodes);
			System.out.println();
			System.out.println("DFS");
			Part1DFS shoe =new Part1DFS(smallCharMaze,nodes,startRow,startColumn,goalRow,goalColumn);
			shoe.search();
			printCharMaze(shoe.solution);
			reset(nodes);
			System.out.println();
			System.out.println("Greedy Best First");
			Part1GreedyBestFirst mrKrabs=new Part1GreedyBestFirst(smallCharMaze, nodes, startRow, startColumn, goalRow, goalColumn);
			mrKrabs.search();
			printCharMaze(mrKrabs.solution);
			reset(nodes);
			//Tyler Test Code starts here, Max:Tyler, I think since you use the Searcher.solution it is a deep copy of the txt file, so just reset nodes no need for new graph construction
			/*smallCharMaze = createCharMaze("mazeForGreedy.txt");
			nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);*/ //commenting out for now, just reseting nodes instead since we have deep copy
			System.out.println();
			System.out.println("A*");
			Part1AStar deathStar = new Part1AStar(smallCharMaze,nodes, startRow, startColumn, goalRow, goalColumn);
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
			Part1GreedyBestFirst mrKrabs=new Part1GreedyBestFirst(smallCharMaze, nodes, startRow, startColumn, goalRow, goalColumn);
			mrKrabs.search();
			printCharMaze(mrKrabs.solution);
			reset(nodes);
			//Tyler Test Code starts here, Max:Tyler, I think since you use the Searcher.solution it is a deep copy of the txt file, so just reset nodes no need for new graph construction
			/*smallCharMaze = createCharMaze("mazeForGreedy.txt");
			nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);*/ //commenting out for now, just reseting nodes instead since we have deep copy
			System.out.println();
			System.out.println("A*");
			Part1AStar deathStar = new Part1AStar(smallCharMaze,nodes, startRow, startColumn, goalRow, goalColumn);
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
			mrKrabs=new Part1GreedyBestFirst(smallCharMaze, nodes, startRow, startColumn, goalRow, goalColumn);
			mrKrabs.search();
			printCharMaze(mrKrabs.solution);
			reset(nodes);
			//Tyler Test Code starts here, Max:Tyler, I think since you use the Searcher.solution it is a deep copy of the txt file, so just reset nodes no need for new graph construction
			/*smallCharMaze = createCharMaze("mazeForGreedy.txt");
			nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);*/ //commenting out for now, just reseting nodes instead since we have deep copy
			System.out.println();
			System.out.println("A*");
			deathStar = new Part1AStar(smallCharMaze,nodes, startRow, startColumn, goalRow, goalColumn);
			printCharMaze(deathStar.search());
		}
		else if(args[0].equals("3")){
			System.out.println("constructing graph based on args[1]");
			ArrayList<char[]> smallCharMaze = createCharMaze(args[1]);
			printCharMaze(smallCharMaze);
			MazeNode[][] nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
			createMazeGraph(smallCharMaze,nodes);
			System.out.println("Graph construction complete");
			System.out.println("Cheese A*");
			ArrayList<MazeNode> cheeseList = createCheeseList(smallCharMaze,nodes);
			Part3CheeseSearch2 sweetChedda = new Part3CheeseSearch2(smallCharMaze,nodes,startRow,startColumn,cheeseList);
			sweetChedda.search();
			printCharMaze(sweetChedda.solution);
			animatePart3(sweetChedda.arrayOfGoalPaths,smallCharMaze);
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
						startRow=i;
						startColumn=j;
					}
					if(charMaze.get(i)[j]=='G'){
						goalRow=i;
						goalColumn=j;
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
	}
	
	public static void animatePart3(ArrayList<MazeNode> solutionNodes, ArrayList<char[]> charMaze)
	{
		for(MazeNode node : solutionNodes)
		{
			while(node != null)
			{
				String unicode_char;
				if(node.successor != null && node.successor.column == node.column+1)
				{
					unicode_char = "\u15E7";
				}
				else if(node.successor != null && node.successor.column == node.column-1)
				{
					unicode_char = "\u15E4";
				}
				else if(node.successor != null && node.successor.row == node.row+1)
				{
					unicode_char = "\u15E3";
				}
				else
				{
					unicode_char = "U";
				}
				printCharMazeExceptSpecialCharacterOn(node.row,node.column, unicode_char,charMaze);
				charMaze.get(node.row)[node.column] = ' ';
				node = node.successor;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void printCharMazeExceptSpecialCharacterOn(int row, int column, String unicode_char, ArrayList<char[]> charMaze)
	{
		for(int y = 0;y < charMaze.size();y++)
		{
			for(int x = 0;x < charMaze.get(0).length;x++)
			{
				if((y == row) && (x == column))
				{
					System.out.print(unicode_char);
				}
				else
				{
					System.out.print(charMaze.get(y)[x]);
				}
			}
			System.out.println();
		}
	}

}
