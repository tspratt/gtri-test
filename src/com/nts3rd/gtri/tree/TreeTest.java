package com.nts3rd.gtri.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tracy on 1/12/2016.
 * Solution for GTRI Java test question 2
 */
public class TreeTest<N> {
	public static void main(String[] args) {
		System.out.println("TreeTest");
		HashMap tree = new HashMap();																						//turns out this is not needed
		Node root = buildTestTree(tree);																				//build the in-memory tree of node.childNodes
		System.out.println("node count: " + tree.size());												//early sanity check
		System.out.println("Depth First display: " + root.treeDisplayDepth());	//Standard traversal
		System.out.println("Generation display: " + root.treeDisplayGen());			//Display by "generation", first level children, second level, etc
		System.out.println("Indented Tree: \n" + root.treeString());						//Displays tree with depth indents.
	}

	/**
	 * Class defining tree nodes
	 * I have a lot to learn about "generics"
	 */
	public static class Node<N> {
		private String sId;
		private Node<N> parent;
		private ArrayList<Node<N>> childNodes;

		/**
		 * Node constructior
		 * @param parent
		 * @param sId
		 * @param tree			//turns out this is not used
		 */
		public Node (Node parent, String sId, HashMap tree) {
			this.sId = sId;																	//set instance vars
			this.parent = parent;
			this.childNodes = new ArrayList();
			tree.put(this.sId, this);												//add new node to tree hashmap
		}

		public Node addChild (Node child){
			childNodes.add(child);
			return child;
		}

		/**
		 * begins generation of standard depth first traversal
		 * @return
		 */
		private String treeDisplayDepth() {
			String sReturn = this.recursorDepth(this);
			return sReturn;
		}

		private String recursorDepth (Node nodeCur) {
			String sReturn = " ";
			sReturn += nodeCur.sId;
			Node child;
			for (int i = 0; i < nodeCur.childNodes.size(); i++) {
				child = (Node)nodeCur.childNodes.get(i);
				sReturn += this.recursorDepth(child);
			}
			return sReturn;
		}

		/**
		 * Begins generatin-based display generation
		 * @return
		 */
		private String treeDisplayGen() {
			ArrayList<String> generations = new ArrayList<String>();
			this.recursorGen(this, generations);
			String sReturn = "";
			for (String generation: generations) {
				sReturn += " " + generation;
			}
			return sReturn;
		}

		/**
		 * Recursive function does not return values
		 * but rather updates the appropriate element of the referenced ArrayList parameter
		 * @param nodeCur
		 * @param generations
		 */
		private void recursorGen (Node nodeCur, ArrayList<String> generations) {
			Node child;
			int generation = nodeCur.getDepth();
			String sGeneration = "";
			if (generations.size() == 0 || generations.size() <= generation) {
				generations.add(nodeCur.sId);
			}
			else {
				sGeneration = generations.get(generation) + " " + nodeCur.sId;
				generations.set(generation, sGeneration);
			}

			for (int i = 0; i < nodeCur.childNodes.size(); i++) {
				child = (Node)nodeCur.childNodes.get(i);
				this.recursorGen(child, generations);
			}
		}

		/**
		 * Begins generation of indented tree display.
		 * I actually created this first and discovered it had most of the functionality needed for the generational display
		 * @return
		 */
		public String treeString () {
			return this.recursor(this);
		}

		private String recursor (Node nodeCur) {
			String sReturn = "";
			int depth = nodeCur.getDepth();
			sReturn += this.padString(depth, " ");
			sReturn += nodeCur.sId;
			Node child;
			for (int i = 0; i < nodeCur.childNodes.size(); i++) {
				child = (Node)nodeCur.childNodes.get(i);
				sReturn += "\n";
				sReturn += this.recursor(child);
			}

			return sReturn;
		}

		/**
		 * Travels up the ancestor chain to get the hierarchy depth.
		 * this also turned out to be the "generation"
		 * Note: it might be more efficient to calculate and store the generation on the node
		 * @return
		 */
		private int getDepth() {
			int depth = 0;
			Node nodeParent = this.parent;
			while (nodeParent != null) {
				depth ++;
				nodeParent = nodeParent.parent;
			}
			return depth;
		}

		/**
		 * Just generates padding characters.
		 * @param count
		 * @param padChar
		 * @return
		 */
		private String padString (int count, String padChar) {
			String sReturn = "";
			for (int i = 0; i <count; i++) {
				sReturn += padChar;
			}
			return sReturn;
		}


	}

	/**
	 * Inelegant way to initialize the tree data
	 * @param tree
	 * @return
	 */
	private static Node buildTestTree (HashMap tree) {
		Node root = new Node(null, "C", tree);
		Node tmpNode = root.addChild(new Node(root, "E", tree));
		tmpNode.addChild(new Node(tmpNode, "H", tree));
		tmpNode.addChild(new Node(tmpNode, "B", tree));
		root.addChild(new Node(root, "F", tree));
		tmpNode = root.addChild(new Node(root, "S", tree));
		tmpNode.addChild(new Node(tmpNode, "P", tree));
		tmpNode.addChild(new Node(tmpNode, "D", tree));
		return root;
	}

}
