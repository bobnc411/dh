package engine;

public interface GameController {
	public abstract String gameTitle();
	public abstract void init();
	public abstract void input();
	public abstract void update();
	public abstract void render();
	public abstract void deinit();
}
