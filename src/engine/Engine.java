package engine;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.MemoryUtil;

public class Engine{
	
	private class EngineThread extends Thread{
		public void run(){
			instance.run();
		}
	}
	
	public static final Engine instance = new Engine();
	
	private Engine(){};
	
	private boolean running = true;
	private boolean needsUpdate = true;
	
	private GameController game;
	
	private int width = 1200;
	private int height = 700;
	
	private long windowID;
	
	private void run(){
		init();
		loop();
		deinit();
	}
	
	private void init(){
		if(GLFW.glfwInit() != GL11.GL_TRUE){
			throw new RuntimeException("GL failed to initialize!");
		}
		windowID = GLFW.glfwCreateWindow(width, height, game.gameTitle(), MemoryUtil.NULL, MemoryUtil.NULL);
		GLFW.glfwMakeContextCurrent(windowID);
		GLFW.glfwSwapInterval(1);
		GLContext.createFromCurrent();
		GL11.glClearColor(0F, 0F, 0F, 0F);
		game.init();
	}
	
	private void loop(){
		while(running){
			if(GLFW.glfwWindowShouldClose(windowID) == GL11.GL_TRUE){
				running = false;
			}
			ByteBuffer widthBuff = BufferUtils.createByteBuffer(4);
			ByteBuffer heightBuff = BufferUtils.createByteBuffer(4);
			GLFW.glfwGetWindowSize(windowID, widthBuff, heightBuff);
			int tempWidth = widthBuff.getInt();
			int tempHeight = heightBuff.getInt();
			if(tempWidth != width || tempHeight != height){
				width = tempWidth;
				height = tempHeight;
				needsUpdate = true;
			}
			game.input();
			game.update();
			if(needsUpdate){
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, 1216, 704, 0, 1000, -1000);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				needsUpdate = false;
			}
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GL11.glViewport(0, 0, width, height);
			GL11.glPushMatrix();
			game.render();
			GL11.glPopMatrix();
			GLFW.glfwSwapBuffers(windowID);
			GLFW.glfwPollEvents();
		}
	}
	
	private void deinit(){
		game.deinit();
		GLFW.glfwDestroyWindow(windowID);
		GLFW.glfwTerminate();
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void setupGameController(GameController controller){
		if(game != null){
			throw new RuntimeException("Game Controller already has been setup!");
		}else if (controller == null){
			throw new IllegalArgumentException("Supplied Game Controller is null!");
		}
		game = controller;
		new EngineThread().start();
	}
}
