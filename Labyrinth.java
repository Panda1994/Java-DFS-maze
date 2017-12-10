import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.*
;/*
 * this class include two method 
 * which present the Graph and get the path to exit
 */
public class Labyrinth {
	private ArrayList<String> lines;
	private Graph graph;
	private char[][] maze;
	private Stack<Node> s;
	private int S;
	private int W;
	private int L;
	private int K1;
	private int K2;
	private int start;
	private int end;
	public Labyrinth(String filename) throws LabyrinthException, IOException, GraphException{
		this.lines=new ArrayList<String>();
		this.s=new Stack<Node>();
		String readfile=filename;
		//we find the file
		File file=new File(readfile);
		String line=null;
		if(file.exists()&&file.isFile()){//open the file if exist
			FileReader filereader=new FileReader(file);
			BufferedReader bufferedreader=new BufferedReader(filereader);
			//we read the line in file and add it to arraylist
			while((line=bufferedreader.readLine())!=null){
				this.lines.add(line);
			}
			bufferedreader.close();
			//we turn the first five elements in list to int
			this.S=Integer.valueOf(lines.get(0)).intValue();
			this.W=Integer.valueOf(lines.get(1)).intValue();// the rooms in one row
			this.L=Integer.valueOf(lines.get(2)).intValue();//the rooms in one column
			this.K1=Integer.valueOf(lines.get(3)).intValue();
			this.K2=Integer.valueOf(lines.get(4)).intValue();
			//now we process the rest of elements in lines
			//we use the content in file to create a 2D array named maze
			this.maze=new char[this.L*2][this.W*2];
			for(int j=0;j<(this.L*2-1);j++){
				String eachline=this.lines.get(j+5);//each line in list
				for(int k=0;k<(this.W*2-1);k++){
					this.maze[j][k]=eachline.charAt(k);
				}
			}
			this.graph=new Graph(this.W*this.L);
			//now we get a 2d array named maze it has 2*L-1 rows and 2*W-1 columns
			//we can create a graph use this 2d array
			//the first step we process the edges in each row

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			for(int ft=0;ft<(this.L*2-1);ft+=2){
				for(int ft2=1;ft2<(this.W*2-1);ft2+=2){
					char rowedge=this.maze[ft][ft2];
					if(rowedge==' '){
						continue;
					}
					else if(rowedge=='h'){//if the edge is wall
						Node fp=new Node((ft2-1)/2+(ft/2)*this.W);
						Node fe=new Node((ft2-1)/2+1+(ft/2)*this.W);
						this.graph.insertEdge(fp, fe, "wall");
					}
					else if(rowedge=='H'){//it is thick wall
						Node fp=new Node((ft2-1)/2+(ft/2)*this.W);
						Node fe=new Node((ft2-1)/2+1+(ft/2)*this.W);
						this.graph.insertEdge(fp, fe, "thickWall");
					}
					else if(rowedge=='m'){//it is the metal edge
						Node fp=new Node((ft2-1)/2+(ft/2)*this.W);
						Node fe=new Node((ft2-1)/2+1+(ft/2)*this.W);
						this.graph.insertEdge(fp, fe, "metalWall");
					}
					else if(rowedge=='-'){//it is corridor
						Node fp=new Node((ft2-1)/2+(ft/2)*this.W);
						Node fe=new Node((ft2-1)/2+1+(ft/2)*this.W);
						this.graph.insertEdge(fp, fe,"corridor");
					}
				}
				
			}
			//now we process the row with all edges
			//it is the second step
			for(int st=1;st<this.L*2-1;st+=2){//the row index start 1
				for(int st2=0;st2<this.W*2-1;st2+=2){
					char rowedge2=this.maze[st][st2];
					if(rowedge2==' '){//it is solid rock
						continue;
					}
					else if(rowedge2=='|'){//it is corridor
						Node fe=new Node(((st+1)/2-1)*this.W+st2/2);
						Node ee=new Node(((st+1)/2)*this.W+st2/2);
						this.graph.insertEdge(fe, ee, "corridor");
					}
					else if(rowedge2=='v'){//normal wall
						Node fe=new Node(((st+1)/2-1)*this.W+st2/2);
						Node ee=new Node(((st+1)/2)*this.W+st2/2);
						this.graph.insertEdge(fe, ee, "wall");
					}
					else if(rowedge2=='V'){//it is a thick wall
						Node fe=new Node(((st+1)/2-1)*this.W+st2/2);
						Node ee=new Node(((st+1)/2)*this.W+st2/2);
						this.graph.insertEdge(fe, ee, "thickWall");
					}
					else{//it is metal wall
						Node fe=new Node(((st+1)/2-1)*this.W+st2/2);
						Node ee=new Node(((st+1)/2)*this.W+st2/2);
						this.graph.insertEdge(fe, ee, "metalWall");
					}
				}
			}
			//now we find the entrance and exit
			//it is step three
			for(int stt=0;stt<(this.L*2-1);stt+=2){
				for(int st3=0;st3<(this.W*2-1);st3+=2){
					char room=this.maze[stt][st3];//now we get the room
					if(room=='+'){//if it is just a room pass
						continue;
					}
					else if(room=='b'){//if it is a entrance
						if(stt==0){
							int indexe=st3/2;
							this.start=st3/2;
							this.graph.getNode(indexe).setMark(false);
						}
						else{
							int index=(stt/2)*this.W+st3/2;
							this.start=index;
							this.graph.getNode(index).setMark(false);
						}
					}
					else{//it is exit!!! we also mark it false
						if(stt==0){
							int indexe=st3/2;
							this.end=indexe;
							this.graph.getNode(indexe).setMark(false);
						}
						else{
							int index=(stt/2)*this.W+st3/2;
							this.end=index;
							this.graph.getNode(index).setMark(false);
						}
					}
				}
			}
			System.out.println(this.graph.getNode(start).getMark());
			this.graph.getNode(start).setMark(true);
			System.out.println(this.graph.getNode(start).getMark());
		}
		//if the file is not exist throw the exception
		else
			throw new LabyrinthException("The file you enter is not exist! \n");
	}
	public Graph getGraph(){
		return this.graph;
	}
	public Iterator<Node> solve() throws GraphException{
		Node current=this.graph.getNode(start);
		this.DFS(current);
		Iterator<Node> itr=this.s.iterator();
		return itr;
	}
	
	private void DFS(Node current) throws GraphException{
		/*if(current.getName()==this.end){
			return;
		}*/
		 current.setMark(true);//it been visited
		 this.s.push(current);//push node in
		// System.out.println("this  is the current mark" +graph.getNode(0).getMark() );
		 Iterator<Edge> edges = this.graph.incidentEdges(current);//get edges
		 System.out.println("this  is current" + current.getName());
		 
		 while(edges.hasNext()){
			 
			 Edge currentedge=edges.next();//current edge
			 Node secondpoint=currentedge.secondEndpoint();//get second node
			 String type=currentedge.getType();
			 
			 if(type=="wall"){//normal wall need one k1!
				 if(this.K1>=1){//we got enough bomb
					 if(secondpoint.getMark()!=true){//not mark!
						 if(secondpoint.getName()==this.end){//it also the exit
							 secondpoint.setMark(true);
							 this.s.push(secondpoint);//push the exit point in stack
							 this.K1--;
							 return;//return the stack
						 }
						 else{//it is not exit
							 secondpoint.setMark(true);//set mark to true
							 this.K1--;//bomb -1
							 DFS(secondpoint);//recursive
							//check condition
							 Node end = graph.getNode(this.end);
							 if (end.getMark() == true){
								 return;
							 }
							 
						 }
					 }
					 continue;//it have been mark
				 }
				 continue;//do not have bomb
			 }
			 else if(type=="thickWall"){
				 if(this.K1>=2){//we got enough bomb
					 if(secondpoint.getMark()!=true){//not mark
						 if(secondpoint.getName()==this.end){//it also the exit
							 secondpoint.setMark(true);
							 this.s.push(secondpoint);//push the exit point in stack
							 this.K1-=2;
							 return;//return the stack
						 }
						 else{//it is not exit
							 secondpoint.setMark(true);
							 this.K1-=2;//bomb -1
							 DFS(secondpoint);//recursive
							 //
							 Node end = graph.getNode(this.end);
							 if (end.getMark() == true){
								 return;
							 }
						 }
					 }
					 continue;//it have been mark
				 }
				 continue;//do not have bomb
			 }
			 else if(type=="metalWall"){
				 if(this.K2>=1){//we got enough bomb
					 if(secondpoint.getMark()!=true){//not mark!
						 if(secondpoint.getName()==this.end){//it also the exit
							 secondpoint.setMark(true);
							 this.s.push(secondpoint);//push the exit point in stack
							 this.K2--;
							 return;//return the stack
						 }
						 else{//it is not exit
							 this.K2--;//bomb -1
							 secondpoint.setMark(true);
							 DFS(secondpoint);//recursive
							 //
							 Node end = graph.getNode(this.end);
							 if (end.getMark() == true){
								 return;
							 }
						 }
					 }
					 continue;//it have been mark
				 }
				 continue;//do not have bomb
			 }
			 else{//it is corridor
					 if(secondpoint.getMark()!=true){//not mark!
						 
						 if(secondpoint.getName()==this.end){//it also the exit
							secondpoint.setMark(true);
							 this.s.push(secondpoint);//push the exit point in stack
							 return;//return the stack
						 }
						 else{//it is not exit
							 //secondpoint.setMark(true);
							 DFS(secondpoint);//recursive
							 //
							 Node end = graph.getNode(this.end);
							 if (end.getMark() == true){
								 return;
							 }
						 }
					 }
					 continue;//it have been mark
			 }
		 }
		 
		 //if no edges we can add!!
		 Node popNodeV=this.s.pop();
		 System.out.println("the is pop" + popNodeV.getName());
		 System.out.println("*****");
		 //System.out.println(popNodeV.getName());
		 Node popNodeU=this.s.peek();
		 //System.out.println(popNodeV.getName());
		 System.out.println("*******");
		 String type1=this.graph.getEdge(popNodeU, popNodeV).getType();
		 if(type1=="wall")
			 this.K1++;
		 else if(type1=="thickWall")
			 this.K1+=2;
		 else if(type1=="metalWall")
			 this.K2++;
		return;
	
	}
	
}
