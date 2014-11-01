package com.example.opengltest1;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;


public class OpenGLRender implements Renderer{
	   
//	Square square = new Square();
//	FlatColorSquare square = new FlatColorSquare();
	private Square square = new SmoothColorSquare();
	private float angle ;
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.5f, 0.2f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect radio of the window
		GLU.gluPerspective(	gl, 45.0f, (float)width/(float)height, 0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
//		gl.glClear(GLES30.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -10);
		
		gl.glPushMatrix();
//		gl.glTranslatef(1.0f, 1.0f, 0.0f);
//		gl.glRotatef(45f, 0.0f, 0.0f, 1.0f);
//		gl.glTranslatef(-1.0f, -1.0f, 0.0f);
		gl.glRotatef(angle , 0, 0, 1);
		square.draw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glTranslatef(2, 0, 0);
		gl.glScalef(.5f, .5f, .5f);
		square.draw(gl);
		
		gl.glPushMatrix();
		gl.glRotatef(-angle,0, 0, 1);
		gl.glTranslatef(2, 0, 0);
		gl.glScalef(.5f, .5f, .5f);
		gl.glRotatef(angle*10, 0, 0, 1);
		square.draw(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();
		angle ++;
	}
//	public static int loadShader(int type, String shaderCode){
//
//	    // 创建一个vertex shader类型(GLES20.GL_VERTEX_SHADER)
//	    // 或fragment shader类型(GLES20.GL_FRAGMENT_SHADER)
//	    int shader = GLES20.glCreateShader(type);
//
//	    // 将源码添加到shader并编译之
//	    GLES20.glShaderSource(shader, shaderCode);
//	    GLES20.glCompileShader(shader);
//
//	    return shader;
//	}
	
}   
