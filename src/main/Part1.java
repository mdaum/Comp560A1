package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {
	static int startX;
	static int startY;
	public static void main(String args[]){
		ArrayList<char[]> smallCharMaze = createCharMaze("smallMaze.txt");
		printCharMaze(smallCharMaze);
		MazeNode[][] nodes = new MazeNode[smallCharMaze.size()][smallCharMaze.get(0).length];
		createMazeGraph(smallCharMaze,nodes);
		
	}
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
	public static void printCharMaze(ArrayList<char[]> charMaze){
		for(char[] row : charMaze){
			for(char c : row){
				System.out.print(c);
			}
			System.out.println();
		}
	}
	public static void createMazeGraph(ArrayList<char[]> charMaze, MazeNode[][] nodes){
		for(int i=0;i<charMaze.size();i++){
			for(int j=0;j<charMaze.get(i).length;j++){
				if(charMaze.get(i)[j]!='%'){
					if(charMaze.get(i)[j]=='S'){
						startX=i;
						startY=j;
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
	public static void reset(MazeNode[][] Graph){
		for (MazeNode[] mazeNodes : Graph) {
			for (MazeNode mazeNode : mazeNodes) {
				mazeNode.visited=false;
			}
		}
	};

}
