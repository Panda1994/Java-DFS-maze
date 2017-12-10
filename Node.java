/*
 * this class implement a node of graph
 * which has a value mark and a int name
 */
public class Node {
	private boolean mark ;
	private int name;
	
	public Node(int name){
		this.name=name;
		this.mark = false;
	}
	//this method set mark to this node
	public void setMark(boolean mark){
		this.mark=mark;
	}
	//this return the mark of this node
	public boolean getMark(){
		return this.mark;
	}
	//this return the name of this node
	public int getName(){
		return this.name;
	}
}
