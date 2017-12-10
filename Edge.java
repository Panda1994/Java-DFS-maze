/*
 * this class implement the Edge object
 * it include first end point,second end point and the type
 */
public class Edge {
	private Node Fendpoint;
	private Node Sendpoint;
	private String type;
	public Edge(Node u,Node v,String type){
		this.Fendpoint=u;
		this.Sendpoint=v;
		this.type=type;
	}
	//get first end point
	public Node firstEndpoint(){
		return this.Fendpoint;
	}
	//get second end point
	public Node secondEndpoint(){
		return this.Sendpoint;
	}
	//get the type of the edge
	public String getType(){
		return this.type;
	}
	//set a new type of this edge
	public void setType(String type){
		this.type=type;
	}
}
