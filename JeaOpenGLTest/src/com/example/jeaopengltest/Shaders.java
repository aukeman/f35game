package com.example.jeaopengltest;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class Shaders {

	public static final String ourVertexShader = 
		"attribute vec4 vPosition; " +
	    "uniform mat4 uMVPMatrix;" +
	    "void main() { " +
	    "    gl_Position = uMVPMatrix * vPosition; " +
        "}";
	
	public static final String ourFragmentShader = 
		"precision mediump float;" +
		"uniform vec4 vColor;" +
		"void main() {" +
		"    gl_FragColor = vColor;" +
		"}";
	
	private static int ourVertexShaderId;
	
	private static int ourFragmentShaderId;
	
	private static int ourProgramId;
	
	private static int ourPositionHandle;
	
	private static int ourMVPMatrixHandle;
	
	private static int ourColorHandle;
	

	static {
		clearIdsAndHandles();
	}
	
	public static void initialize(){

		deleteProgramAndShaders();
		
		getVertexShaderId();
		getFragmentShaderId();
		
		getProgramId();
		
		getPositionHandle();
		getColorHandle();
	}
	
	public static int getVertexShaderId(){

		if ( ourVertexShaderId < 0 ){
			ourVertexShaderId = loadShader(GLES20.GL_VERTEX_SHADER, ourVertexShader);
		}
		
		return ourVertexShaderId;
	}
	
	public static int getFragmentShaderId(){
		
		if ( ourFragmentShaderId < 0 ){
			ourFragmentShaderId = loadShader(GLES20.GL_FRAGMENT_SHADER, ourFragmentShader);
		}
		
		return ourFragmentShaderId;
	}
	
	public static int getProgramId(){
		if ( ourProgramId < 0 ){
			ourProgramId = GLES20.glCreateProgram();
			
			GLES20.glAttachShader(ourProgramId, getVertexShaderId());   // add the vertex shader to program
		    GLES20.glAttachShader(ourProgramId, getFragmentShaderId()); // add the fragment shader to program
		    GLES20.glLinkProgram(ourProgramId);
		}
		
		return ourProgramId;
	}
	
	public static int getPositionHandle(){
		if ( ourPositionHandle < 0 ){
			ourPositionHandle = GLES20.glGetAttribLocation(getProgramId(), "vPosition");
		}
		
		return ourPositionHandle;
	}
	
	public static int getMVPMatrixHandle(){
		if ( ourMVPMatrixHandle < 0 ){
			ourMVPMatrixHandle = GLES20.glGetUniformLocation(getProgramId(), "uMVPMatrix");
		}
		
		return ourMVPMatrixHandle;
	}
	
	public static int getColorHandle(){
		if ( ourColorHandle < 0 ){
			ourColorHandle = GLES20.glGetUniformLocation(getProgramId(), "vColor");
		}
		
		return ourColorHandle;
	}
	
	public static void render(int numberOfVertices, FloatBuffer vertices, ShortBuffer drawOrder, float[] color, float[] mvpMatrix){
		
		GLES20.glUseProgram(getProgramId());
		
		GLES20.glEnableVertexAttribArray(getPositionHandle());
		
		GLES20.glVertexAttribPointer(getPositionHandle(), 3, GLES20.GL_FLOAT, false, 0, vertices);
		
		GLES20.glUniform4fv(getColorHandle(), 1, color, 0);
		
		GLES20.glUniformMatrix4fv(getMVPMatrixHandle(), 1, false, mvpMatrix, 0);
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numberOfVertices, GLES20.GL_UNSIGNED_SHORT, drawOrder);
		
		GLES20.glDisableVertexAttribArray(getPositionHandle());
	}
	
	private static int loadShader(int type, String shaderCode){
	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);

	    // add the source code to the shader and compile it
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);

	    return shader;
	} 

	private static void deleteProgramAndShaders(){

		if ( 0 < ourVertexShaderId ){
			GLES20.glDeleteShader(ourVertexShaderId);
			GLES20.glDetachShader(ourProgramId, ourVertexShaderId);
		}
		
		if ( 0 < ourFragmentShaderId ){
			GLES20.glDeleteShader(ourFragmentShaderId);
			GLES20.glDetachShader(ourProgramId, ourFragmentShaderId);
		}
		
		if ( 0 < ourProgramId ){
			GLES20.glDeleteProgram(ourProgramId);
		}
		
		clearIdsAndHandles();
	}
	
	
	private static void clearIdsAndHandles(){
		
		ourVertexShaderId = -1;
		ourFragmentShaderId = -1;
		ourProgramId = -1;
		ourPositionHandle = -1;
		ourMVPMatrixHandle = -1;
		ourColorHandle = -1;
	}
	
}
