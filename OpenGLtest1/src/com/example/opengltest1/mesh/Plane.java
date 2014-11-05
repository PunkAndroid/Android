package com.example.opengltest1.mesh;

public class Plane extends Mesh {
	//平面，可有宽、高、深度
	public Plane(){
		this(1,1,1,1);
	}
	public Plane(float width,float height){
		this(width,height,1,1);
	}
	public Plane(float width, float height, int widthSegments, int heightSegments) {
		// 顶点数：(widthSegments + 1) * (heightSegments + 1) 每个顶点3个坐标点表示
		// 正方形数：widthSegments*heightSegments 一块正方形需要画两个三角形即6个位置
		int verticesNum = (widthSegments + 1) * (heightSegments + 1);
		float[] vertices = new float[verticesNum * 3];
		short[] indices = new short[widthSegments*heightSegments * 6];
		//使图形居中
		float xOffset = width / -2;
		float yOffset = height / -2;
		float xWidth = width / (widthSegments);
		float yHeight = height /(heightSegments);
		int currentVertex = 0;
		int currentIndex = 0;
		short w = (short) (widthSegments +1);
		//从下到上，从左到右初始化顶点坐标，
		for(int y =0; y<heightSegments + 1;y++){
			for(int x = 0;x < widthSegments + 1;x++){
				vertices[currentVertex++] = xOffset+x*xWidth;
				vertices[currentVertex++] = yOffset+y*yHeight;
				vertices[currentVertex++] = 0;
				int n = y*(widthSegments + 1) + x;
				if(y<heightSegments&&x<widthSegments){
					// 最右边和最上方的点不做处理，因为做处理的点是定义一个两个三角形的正方形，正方形左下方的点位处理点
					// 第一个三角形 Face One
					indices[currentIndex] = (short)n;		//当前点
					indices[currentIndex+1]=(short)(n+1);	//当前点右方的点
					indices[currentIndex+2]=(short)(n+w);	//当前点上方的点
					// 第二个三角形 Face two
					indices[currentIndex+3]=(short)(n+1); 	//当前点右方的点
					indices[currentIndex+4]=(short)(n+1+w);	//当前点右上方的点
					indices[currentIndex+5]=(short)(n+1+w-1);//当前点上方的点
					
					currentIndex+=6;
				}
			}
		}
		setIndices(indices);
		setVertices(vertices);
	}
}
