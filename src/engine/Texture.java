package engine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Texture {
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	private int textureID;
	private String texturePath;
	
	public Texture(String path, int id){
		texturePath = path;
		textureID = id;
	}
	
	public String getPath(){
		return texturePath;
	}
	
	public void bindTexture(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public void releaseTexture(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public static ByteBuffer convert(BufferedImage image){
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte)((pixel >> 16) & 0xFF));
				buffer.put((byte)((pixel >> 8) & 0xFF));
				buffer.put((byte)(pixel & 0xFF));
				buffer.put((byte)((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();
		return buffer;
	}
	
	public static Texture getTexture(String path){
		if(!textures.containsKey(path)){
			try{
				BufferedImage image = ImageIO.read(Engine.class.getResourceAsStream(path));
				Texture tex = new Texture(path, loadTexture(image));
				textures.put(path, tex);
			}catch(IOException error){
				textures.put(path, textures.get("/img/error.png"));
			}
		}
		return textures.get(path);
	}
	
	private static int loadTexture(BufferedImage image){
		ByteBuffer data = convert(image);
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
		return id;
	}
}
