package edu.union;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;

/**
 * http://www.zeuscmd.com/tutorials/opengles/08-ColorAndShading.php
 * @author bburns
 */
public class GLTutorialThree extends GLTutorialBase {	
	// Vertices (x,y,z) for a 2D triangle
	float[] triangle = new float[] { -0.25f, -0.25f, 0.0f,
									 0.25f, -0.25f, 0.0f,
									 -0.25f, 0.25f, 0.0f };
	
	// Colors (r,g,b,a) for each vertex
	float[] colors = new float[] { 	1, 0, 0, 1,
									0, 1, 0, 1,
									0, 0, 1, 1 };
	
	// NIO Buffers for each.
	FloatBuffer triangleBuff;
	FloatBuffer colorBuff;
	
	public GLTutorialThree(Context c) {
		super(c);
	
		triangleBuff = makeFloatBuffer(triangle);
		colorBuff = makeFloatBuffer(colors);
		
	}
	
	protected void init(GL10 gl) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);//清除屏幕，设置背景，4个参数为红、绿、兰、混合系数
		gl.glMatrixMode(GL10.GL_PROJECTION);//设置当前的矩阵模式；这里的参数为投影矩阵表明下面的变化
//		将影响投影矩阵（投影矩阵负责为场景增加透视，使越远的东西看起来越小）
		gl.glLoadIdentity();//矩阵状态恢复成其原始状态，并且当前点被移到了屏幕中心
		GLU.gluOrtho2D(gl, 0.0f,1.3f,0.0f,1.0f);//定义二维正视投影矩阵。left，right分别设置左右
//		垂直切平面的坐标，bottom，top分别设置上下垂直切平面的坐标。 
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);//清除屏幕及深度缓存


		gl.glShadeModel(GL10.GL_SMOOTH);//启用阴影模式，这里的参数为“平滑模式”
	}
	
	protected void drawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除屏幕及深度缓存
		gl.glMatrixMode(GL10.GL_MODELVIEW);//指明任何新的变换将会影响 modelview matrix(模型观察矩阵)。
//		模型观察矩阵中存放了我们的物体讯息。
		gl.glLoadIdentity();//矩阵状态恢复成其原始状态，并且当前点被移到了屏幕中心
		gl.glTranslatef(0,0,-1);//从当前点的位置沿xyz进行平移并移出屏幕 1.0
		
		// Send the vertices to the renderer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleBuff);//设置顶点数组；第一参数表示两个元素
//		构成一个点，第二个参数表明每个元素的数据类型，第三个参数为stride参数，它表示“从一个数据的开始到下一个
//		数据的开始，所相隔的字节数，第四个参数为顶点数据数组的实际位置
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//指示OpenGL状态机打开对Vertex/Normal/UV Array
//		的支持，使得批量发送模型数据成为可能
	
		// Send the colors to the renderer
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuff);//glVertexPointer,glNormalPointer, 
//		glTexCoordPoint等Pointer则是实际用来发送模型数据的。
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);//glDrawElements, glDrawArray则是用利用已
//		发送数据来绘制实际图形的，前者通过额外的顶点索引数组来定义面，后者直接利用已经发送的模型数据数组绘制
	}
}