import java.util.*;

public class Graph implements GraphADT{
	private ArrayList<Node> vexs = new ArrayList<Node>();//the list store the Nodes
	private Map<Integer,LinkedList<Edge>> Adjalist;
	//constructor to create a empty graph with n nodes
	public Graph(int n){
		Adjalist=new HashMap<Integer,LinkedList<Edge>>();
		this.vexs=new ArrayList<Node>();
		for(int i=0;i<n;i++){
			Node vex=new Node(i);
			vexs.add(vex);
		}
		int length=vexs.size();
		//initialized the adjacency list and no edges
		//the elements in the linked list is edges
		for(int j=0;j<length;j++){
			this.Adjalist.put(j, new LinkedList<Edge>());
		}
	}
	//helper method to check if the target node exist in graph
	private boolean NodeExist(ArrayList<Node> arr,Node target){
		for(int i=0;i<arr.size();i++){
			if(arr.get(i).getName()==target.getName()){
				return true;
			}
		}
		return false;//return false otherwise
	}
	
	@Override
	public void insertEdge(Node nodeu, Node nodev, String edgeType) throws GraphException {	
		if(this.NodeExist(vexs, nodeu)==true && this.NodeExist(vexs, nodev)==true){
			int indexU=nodeu.getName();
			int indexV=nodev.getName();
			Node uu=this.getNode(indexU);
			Node vv=this.getNode(indexV);
			Edge newEdgeU=new Edge(uu,vv,edgeType);
			Edge newEdgeV=new Edge(vv,uu,edgeType);
			LinkedList listU=this.Adjalist.get(indexU);
			int sizeU=listU.size();
			//we use for loop to check if the edge between u and v already exist
			for(int i=0;i<sizeU;i++){
				if(listU.get(i).equals(newEdgeU)){//if exist throw exception
					throw new GraphException("The edge is exist \n");
				}
				//else continue loop
				continue;
			}
			//add the edge to both of the vexs
			this.Adjalist.get(indexU).add(newEdgeU);
			this.Adjalist.get(indexV).add(newEdgeV);
		}
		//node not exist
		else
			throw new GraphException("Nodes not exist \n");
	}

	@Override
	public Node getNode(int name) throws GraphException {
		for(int i=0;i<this.vexs.size();i++){
			if(this.vexs.get(i).getName()==name){
				return this.vexs.get(i);
			}
		}
		throw new GraphException("The target node is not exist in graph ! \n");
	}

	@Override
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		if(this.NodeExist(vexs, u)){
			LinkedList<Edge> listU=this.Adjalist.get(u.getName());
			int size=listU.size();
			if(size==0){//if no edges of node u return null
				return null;
			}
			else{//return the iterator of edges of u
				return listU.iterator();
			}
		}
		else
			throw new GraphException("The node not exist \n");
		
	}

	@Override
	public Edge getEdge(Node u, Node v) throws GraphException {
		if(this.NodeExist(vexs, u)&&this.NodeExist(vexs, v)){
			int index=u.getName();
			int indexv=v.getName();
			LinkedList<Edge> list=this.Adjalist.get(u.getName());
			LinkedList<Edge> list1=this.Adjalist.get(v.getName());
			for(int i=0;i<list.size();i++){
				if(list.get(i).firstEndpoint().getName()==u.getName()&&list.get(i).secondEndpoint().getName()==v.getName()){
					return list.get(i);
				}
				else
					continue;
			}
			for(int j=0;j<list1.size();j++){
				if(list1.get(j).firstEndpoint().getName()==v.getName()&&list1.get(j).secondEndpoint().getName()==u.getName()){
					return list1.get(j);
				}
				continue;
			}
			throw new GraphException("There is no edge between these nodes \n");
		}
		else
			throw new GraphException("These nodes are not Exist! \n");
	}

	@Override
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if(this.NodeExist(vexs, u)&&this.NodeExist(vexs, v)){
			int index=u.getName();
			LinkedList<Edge> list=this.Adjalist.get(index);
			for(int i=0;i<list.size();i++){
				if((list.get(i).firstEndpoint()==u||list.get(i).secondEndpoint()==v)||(list.get(i).firstEndpoint()==v&&list.get(i).secondEndpoint()==u)){
					return true;
				}
				else
					continue;
			}
			return false;
		}
		else
			throw new GraphException("These nodes not exist! \n");
		
		
		
		
	}

}













