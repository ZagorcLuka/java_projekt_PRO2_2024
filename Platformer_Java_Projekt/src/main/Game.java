package main;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;//omejitev za FPS
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE*SCALE);
	public final static int GAME_WIDTH = TILES_SIZE*TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE*TILES_IN_HEIGHT;
	
	
	public Game() {

		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();

	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() { 

		double timePerFrame = 1000000000.0 / FPS_SET; //pretvorimo v nano sekunde
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();

		int frames = 0; //FPS counter
		long lastCheck = System.currentTimeMillis();

		while (true) {

			now = System.nanoTime();
			if (now - lastFrame >= timePerFrame) {
				gamePanel.repaint();
				lastFrame = now;
				frames++;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

	}

}