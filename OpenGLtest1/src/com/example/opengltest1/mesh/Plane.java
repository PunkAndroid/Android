package com.example.opengltest1.mesh;

public class Plane extends Mesh {
	//ƽ�棬���п����ߡ����
	public Plane(){
		this(1,1,1,1);
	}
	public Plane(float width,float height){
		this(width,height,1,1);
	}
	public Plane(float width, float height, int widthSegments, int heightSegments) {
		// ��������(widthSegments + 1) * (heightSegments + 1) ÿ������3��������ʾ
		// ����������widthSegments*heightSegments һ����������Ҫ�����������μ�6��λ��
		int verticesNum = (widthSegments + 1) * (heightSegments + 1);
		float[] vertices = new float[verticesNum * 3];
		short[] indices = new short[widthSegments*heightSegments * 6];
		//ʹͼ�ξ���
		float xOffset = width / -2;
		float yOffset = height / -2;
		float xWidth = width / (widthSegments);
		float yHeight = height /(heightSegments);
		int currentVertex = 0;
		int currentIndex = 0;
		short w = (short) (widthSegments +1);
		//���µ��ϣ������ҳ�ʼ���������꣬
		for(int y =0; y<heightSegments + 1;y++){
			for(int x = 0;x < widthSegments + 1;x++){
				vertices[currentVertex++] = xOffset+x*xWidth;
				vertices[currentVertex++] = yOffset+y*yHeight;
				vertices[currentVertex++] = 0;
				int n = y*(widthSegments + 1) + x;
				if(y<heightSegments&&x<widthSegments){
					// ���ұߺ����Ϸ��ĵ㲻����������Ϊ�������ĵ��Ƕ���һ�����������ε������Σ����������·��ĵ�λ������
					// ��һ�������� Face One
					indices[currentIndex] = (short)n;		//��ǰ��
					indices[currentIndex+1]=(short)(n+1);	//��ǰ���ҷ��ĵ�
					indices[currentIndex+2]=(short)(n+w);	//��ǰ���Ϸ��ĵ�
					// �ڶ��������� Face two
					indices[currentIndex+3]=(short)(n+1); 	//��ǰ���ҷ��ĵ�
					indices[currentIndex+4]=(short)(n+1+w);	//��ǰ�����Ϸ��ĵ�
					indices[currentIndex+5]=(short)(n+1+w-1);//��ǰ���Ϸ��ĵ�
					
					currentIndex+=6;
				}
			}
		}
		setIndices(indices);
		setVertices(vertices);
	}
}