package engine;

import static org.lwjgl.opengl.GL11.*;

public class RenderHelper {
	
	
	public static void color(float red, float green, float blue){
		color(red, green, blue, 1F);
	}
	
	public static void color(float red, float green, float blue, float alpha){
		glColor4f(red, green, blue, alpha);
	}
	
	public static void disableAlpha(){
		glDisable(GL_BLEND);
	}
	
	public static void disableDepthTest(){
		glDisable(GL_DEPTH_TEST);
	}
	
	public static void disableTextures(){
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void drawTexturedQuad(int posX, int posY, int width, int height, int texS, int texT, int texU, int texV){
		float s = ((float)texS) / 256F;
		float t = ((float)texT) / 256F;
		float u = ((float)texU) / 256F;
		float v = ((float)texV) / 256F;
		glBegin(GL_QUADS);
		glTexCoord2f(s, t);
		glVertex2i(posX, posY);
		glTexCoord2f(s, t + v);
		glVertex2i(posX, posY + height);
		glTexCoord2f(s  +  u, t + v);
		glVertex2i(posX + width, posY + height);
		glTexCoord2f(s + u, t);
		glVertex2i(posX + width, posY);
		glEnd();
	}
	
	public static final void drawQuad(int posX, int posY, int width, int height){
		glBegin(GL_QUADS);
		glVertex2i(posX, posY);
		glVertex2i(posX, posY + height);
		glVertex2i(posX + width, posY + height);
		glVertex2i(posX + width, posY);
		glEnd();
	}
	
	public static void enableAlpha(){
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
	}
	
	public static void enableDepthTest(){
		glEnable(GL_DEPTH_TEST);
	}
	
	public static void enableTextures(){
		glEnable(GL_TEXTURE_2D);
	}
	
}
