/*
 * -------------------- DO NOT REMOVE OR MODIFY THIS HEADER --------------------
 * 
 * Copyright (C) 2014 The Iguanod Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License should have been provided along with this file, usually
 * under the name "LICENSE.txt". If that is not the case you may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.iguanod.collect;

import es.iguanod.collect.Tree.TreeNode;
import es.iguanod.util.Maybe;
import java.util.LinkedList;

/**
 *
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class LinkedTree<T> extends AbstractLinkedTree<T>{

	private static final long serialVersionUID=1961696201478523697L;

	public LinkedTree(){
		super();
	}

	public LinkedTree(int max_sons){
		super(max_sons);
	}

	public LinkedTree(Tree<? extends T> tree){
		super(tree);
	}

	public LinkedTree(Tree<? extends T> tree, int max_sons){
		super(tree, max_sons);
	}

	@Override
	protected final boolean nullsAllowed(){
		return true;
	}

	@Override
	protected final boolean structureModifiable(){
		return true;
	}

	@Override
	protected final boolean nodeValueModifiable(){
		return true;
	}

	@Override
	public TreeNode push(T value){
		if(root != null && this.maxSons() <= 0){
			throw new UnsupportedOperationException("If maximum of sons per node is not specified push is only supported to set the root");
		}

		if(!this.nullsAllowed() && value == null){
			throw new NullPointerException("The tree doesn't accept null values");
		}

		if(this.isEmpty()){
			root=provideNode(Maybe.from(value), null);
			return root;
		}

		LinkedList<LinkedTNode<T>> queue=new LinkedList<>();
		queue.push(this.root);
		LinkedTNode<T> first=null;

		while(!queue.isEmpty()){
			LinkedTNode<T> node=queue.pop();
			if(!this.isFull(node)){
				LinkedTNode<T> ret=provideNode(Maybe.from(value), node);
				node.sons.add(ret);
				return ret;
			}else if(!this.hasChildren(node) && first == null){
				first=node;
			}
			queue.addAll(((LinkedTNode<T>)node).sons);
		}

		return pvtAdd(first, value);
	}

	@Override
	public Maybe<T> pop(){

		if(this.isEmpty()){
			return Maybe.ABSENT;
		}

		LinkedList<LinkedTNode<T>> queue=new LinkedList<>();
		queue.push(this.root);
		LinkedTNode<T> last=null;

		while(!queue.isEmpty()){
			last=queue.pop();
			queue.addAll(((LinkedTNode<T>)last).sons);
		}

		Maybe<T> ret=this.getValue(last);
		last.invalidate(this);

		if(last == root){
			root=null;
		}else{
			this.pvtRemove(last.parent, this.childrenSize(last.parent) - 1);
		}

		return ret;
	}
}
