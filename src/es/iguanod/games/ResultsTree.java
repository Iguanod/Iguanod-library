package es.iguanod.games;

import es.iguanod.base.StringFormatException;
import es.iguanod.collect.AbstractTree;
import es.iguanod.collect.AscendingTree;
import es.iguanod.collect.DoubleTreeCounter;
import es.iguanod.collect.DoubleTreeCounter.DoubleTreeCounterBuilder;
import es.iguanod.collect.IntHashCounter;
import es.iguanod.collect.IntHashCounter.IntHashCounterBuilder;
import es.iguanod.collect.Tree.TreeNode;
import es.iguanod.util.Maybe;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class ResultsTree extends AscendingTree<String[]>{

	private static final long serialVersionUID=1816100213659613130L;
	//************
	private IntHashCounter<Integer> spaces=new IntHashCounterBuilder<Integer>().build();
	private DoubleTreeCounter<String> sons_results=new DoubleTreeCounterBuilder<String>().reverse(true).build();
	private int prune_lvls;

	protected static class ResultsTNode extends LinkedTNode<String[]>{

		private static final long serialVersionUID=-2184813009761211777L;
		//************
		public ArrayList<LinkedTNode<String[]>> rsons=null;
		private static final ArrayList EMPTY_SONS=new ArrayList(0);

		public ResultsTNode(Maybe<? extends String[]> value, LinkedTNode<String[]> parent, AbstractTree<String[]> tree){
			super(value, parent, tree);
		}

		private void fill(List<ResultsTNode> nodes){
			this.sons.addAll(nodes);
		}

		private boolean prune(){

			if(sons.isEmpty()){
				return true;
			}else{
				if(((ResultsTNode)sons.get(0)).prune()){
					rsons=sons;
					sons=EMPTY_SONS;
				}else{
					for(int i=1; i < sons.size(); i++){
						((ResultsTNode)sons.get(i)).prune();
					}
				}
				return false;
			}
		}
	}

	private Iterable<? extends TreeNode> getChildren(TreeNode node){
		if(((ResultsTNode)node).rsons == null){
			return this.children(node);
		}else{
			return ((ResultsTNode)node).rsons;
		}
	}

	private List<? extends TreeNode> getChildrenCopy(TreeNode node){
		if(((ResultsTNode)node).rsons == null){
			return this.childrenCopy(node);
		}else{
			return ((ResultsTNode)node).rsons;
		}
	}

	private int pvtHeight(TreeNode node){
		if(!this.getChildren(node).iterator().hasNext()){
			return 1;
		}
		int acc=0;
		for(TreeNode son:this.getChildren(node)){
			acc=Math.max(acc, this.pvtHeight(son));
			if(acc < 0){
				return Integer.MAX_VALUE;
			}
		}
		return acc + 1;
	}

	public ResultsTree(int num_sons, int prune_lvls){
		super(num_sons);
		this.prune_lvls=prune_lvls;
	}

	@Override
	protected ResultsTNode provideNode(Maybe<? extends String[]> value, LinkedTNode<String[]> parent){
		return new ResultsTNode(value, parent, this);
	}

	@Override
	public TreeNode push(String[] results){

		for(String result:results){
			if(result.contains("-")){
				throw new StringFormatException("Hyphens not allowed");
			}
		}

		if(prune_lvls > 0 && root != null && this.isFull(root) && this.height() == prune_lvls + 1){
			((ResultsTNode)this.root).prune();
		}

		TreeNode node=super.push(results);

		if(this.parent(node).isAbsent()){
			return node;
		}

		TreeNode parent=this.parent(node).get();

		sons_results.clear();
		for(TreeNode son:getChildren(parent)){
			sons_results.balancedSum(getValue(son).get());
		}
		Entry<String, Double>[] sorted=sons_results.entrySet().toArray(new Entry[]{});

		if((sorted.length == 1 && sorted[0].getValue() > this.maxSons() / 2.0)
		|| (sorted.length > 1 && sorted[0].getValue() > sorted[1].getValue() + this.maxSons() - childrenSize(parent))){
			ArrayList<ResultsTNode> list=new ArrayList<>();
			for(int i=childrenSize(parent); i < this.maxSons(); i++){
				list.add(provideNode(Maybe.from(new String[]{"-"}), (ResultsTNode)parent));
			}
			((ResultsTNode)parent).fill(list);
			this.push(new String[]{sorted[0].getKey()});
		}else if(isFull(parent)){
			ArrayList<String> tmp=new ArrayList<>();
			double highest=sorted[0].getValue();
			for(Entry<String, Double> ch:sorted){
				if(ch.getValue() == highest){
					tmp.add(ch.getKey());
				}else{
					break;
				}
			}
			this.push(tmp.toArray(new String[]{}));
		}

		return node;
	}

	@Override
	public String toString(){
		if(root == null){
			return "";
		}
		calculateSpaces();
		return pvtToString();
	}

	private void calculateSpaces(){

		calculateSpaces(this.root, 0);
	}

	private void calculateSpaces(TreeNode node, int depth){

		int acc=0;
		Maybe<String[]> value=getValue(node);
		if(value.isPresent()){
			for(String str:value.get()){
				acc+=str.length();
			}
			acc+=value.get().length;
		}else{
			acc=1;
		}
		spaces.putMax(depth, acc);
		for(TreeNode son:getChildren(node)){
			calculateSpaces(son, depth + 1);
		}
	}

	//TODO: generic for all trees?
	private String pvtToString(){
		return pvtToString(this.root, 0, "", "", "");
	}

	private String pvtToString(TreeNode node, int depth, String carry_top, String carry_mid, String carry_bot){

		String str="";

		String spaces_str="";
		for(int i=0; i < spaces.get(depth); i++){
			spaces_str+=" ";
		}

		List<TreeNode> sons=(List<TreeNode>)getChildrenCopy(node);
		if(getValue(node).isAbsent()){
			for(int i=sons.size() - 1; i >= (this.maxSons() / 2) + 1; i--){
				if(i != 0){
					str+="\n";
				}
				str+=pvtToString(sons.get(i), depth + 1, carry_top + spaces_str + "   ", carry_top + spaces_str + "   ", carry_top + spaces_str + "   ");
			}
			if((this.maxSons() / 2) < sons.size()){
				str+="\n";
				str+=pvtToString(sons.get(this.maxSons() / 2), depth + 1, carry_top + spaces_str + "   ", carry_mid + spaces_str + "   ", carry_bot + spaces_str + "   ");
			}
			for(int i=Math.min(sons.size() - 1, (this.maxSons() / 2) - 1); i >= 0; i--){
				str+="\n";
				str+=pvtToString(sons.get(i), depth + 1, carry_bot + spaces_str + "   ", carry_bot + spaces_str + "   ", carry_bot + spaces_str + "   ");
			}
		}else{
			int height=this.pvtHeight(node);

			String results_str="";
			String[] results=getValue(node).get();
			for(String result:results){
				results_str+=result + " ";
			}

			if(!this.getChildren(node).iterator().hasNext()){
				return carry_mid + results_str + spaces_str.substring(0, spaces_str.length() - results_str.length()) + "\n";
			}

			str+=pvtToString(sons.get(this.maxSons() - 1), depth + 1, carry_top + spaces_str + "   ", carry_top + spaces_str + " ┌ ", carry_top + spaces_str + " │ ");
			if(this.maxSons() > 2){
				for(int i=this.maxSons() - 2; i >= (int)Math.ceil(this.maxSons() / 2.0); i--){
					if(height != 2){
						str+=carry_top + spaces_str + " │\n";
					}
					str+=pvtToString(sons.get(i), depth + 1, carry_top + spaces_str + " │ ", carry_top + spaces_str + " │ ", carry_top + spaces_str + " │ ");
				}
				if(height != 2){
					str+=carry_top + spaces_str + " │\n";
				}

				if(this.maxSons() % 2 != 0){
					str+=pvtToString(sons.get(this.maxSons() / 2), depth + 1, carry_top + spaces_str + " │ ", carry_mid + spaces_str.substring(0, spaces_str.length() - results_str.length()) + results_str + "─┤ ", carry_bot + spaces_str + " │ ");
				}else{
					str+=carry_mid + spaces_str.substring(0, spaces_str.length() - results_str.length()) + results_str + "─┤\n";
				}

				for(int i=(this.maxSons() / 2) - 1; i >= 1; i--){
					if(height != 2){
						str+=carry_bot + spaces_str + " │\n";
					}
					str+=pvtToString(sons.get(i), depth + 1, carry_bot + spaces_str + " │ ", carry_bot + spaces_str + " │ ", carry_bot + spaces_str + " │ ");
				}
				if(height != 2){
					str+=carry_bot + spaces_str + " │\n";
				}
			}else{
				str+=carry_mid + spaces_str.substring(0, spaces_str.length() - results_str.length()) + results_str + "─┤\n";
			}
			str+=pvtToString(sons.get(0), depth + 1, carry_bot + spaces_str + " │ ", carry_bot + spaces_str + " └ ", carry_bot + spaces_str + "   ");
		}

		return str;
	}
}
