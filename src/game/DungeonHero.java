package game;

import engine.Engine;
import engine.GameController;
import engine.RenderHelper;
import engine.Texture;

public class DungeonHero implements GameController {

	int[][] background;
	
	@Override
	public String gameTitle() {
		return "Dungeon Hero";
	}

	@Override
	public void init() {
		
	}

	@Override
	public void input() {
		
	}

	@Override
	public void update() {
	}

	@Override
	public void render() {
		RenderHelper.color(1, 1, 1);
		Texture.getTexture("/img/tiles.png").bindTexture();
		RenderHelper.enableTextures();
		RenderHelper.drawTexturedQuad(0, 0, 32, 32, 48, 0, 16, 16);
		RenderHelper.disableTextures();
		Texture.getTexture("/img/tiles.png").releaseTexture();
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		Engine.instance.setupGameController(new DungeonHero());
	}

}
