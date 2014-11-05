package com.example.opengltest1.mesh;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class Group extends Mesh {
	private Vector<Mesh> children = new Vector<Mesh>();

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		int size = children.size();
		for (int i = 0; i < size; i++) {
			children.get(i).draw(gl);
		}
	}
	public void add(int location,Mesh object){
		children.add(location,object);
	}
	public void add(Mesh object){
		children.add(object);
	}
	public void clean(){
		children.clear();
	}
	public Mesh get(int location){
		return children.get(location);
	}
	public Mesh remove(int location){
		return children.remove(location);
	}
	public boolean remove(Object object){
		return children.remove(object);
	}
	public int size(){
		return children.size();
	}
}
