package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilz.HelpMethods.CanMoveHere;
public class GamePanel extends JPanel {

	
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img1,img2,img3,img4,img5,img6,bg1;
	private BufferedImage[][] animacije;
	private int aniTck, aniIndex, aniSpeed = 25;
	private int playerAction;
	private int playerDir = -1;
	private boolean moving = false;
	private Rectangle hitbox;
	private float gravity = 1f;
	public GamePanel() {

		mouseInputs = new MouseInputs(this);

		importImg();
		loadAnimations();
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}

	private void loadAnimations() {
		animacije = new BufferedImage[3][2];  //shranimo skupaj skupine slik za animacije
		animacije[0][0] = img1;
		animacije[0][1] = img2;
		animacije[1][0] = img3;	
		animacije[1][1] = img4;
		animacije[2][0] = img5;
		animacije[2][1] = img6;
	
	}
	
	private void importImg() {
	   //importamo sliko
		InputStream is1 = getClass().getResourceAsStream("/sprite_1.png");
		InputStream is2 = getClass().getResourceAsStream("/sprite_2.png");
		InputStream is3 = getClass().getResourceAsStream("/walk1.png");
		InputStream is4 = getClass().getResourceAsStream("/walk2.png");
		InputStream is5 = getClass().getResourceAsStream("/sprite_5.png");
		InputStream is6 = getClass().getResourceAsStream("/sprite_6.png");
		InputStream is7 = getClass().getResourceAsStream("/background.png");
		
		
		try {
			img1 = ImageIO.read(is1);
			img2 = ImageIO.read(is2);
			img3 = ImageIO.read(is3);
			img4 = ImageIO.read(is4);
			img5 = ImageIO.read(is5);
			img6 = ImageIO.read(is6);
			bg1 = ImageIO.read(is7);
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		setPreferredSize(size);
		System.out.println("size: "+GAME_WIDTH +" : "+GAME_HEIGHT);
		
		

	}
	
	

	public void setDirection(int direction) {
		this.playerDir = direction;
		moving = true;
		
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	private void updateAnimationTick() {//gremo skozi tabelo sprožene animacije
		aniTck++;
		if(aniTck >= aniSpeed) {
			aniTck = 0;
			aniIndex++;
			if(aniIndex >= 2)
				aniIndex = 0;
		}
		}
	private void setAnimacija() { //pogoji za različne animacije
		if(moving && yDelta > GAME_HEIGHT-28-90)
			playerAction = WALK;
		else if (yDelta <= GAME_HEIGHT-28-90)
			playerAction = JUMP;
		else if (yDelta <= GAME_HEIGHT-28-90 && moving)
			playerAction = JUMP;
		else
			playerAction = IDLE;
		
	}
	private void updatePos() { //premik strička
		if (moving) {
			if (playerDir ==LEFT && CanMoveHere(xDelta-5,1, 128, 80)) 
				xDelta -= 5;
			else if(playerDir == UP && CanMoveHere(1,yDelta-5, 128, 80)) 
				yDelta -= 5;
			else if (playerDir == RIGHT  && CanMoveHere(xDelta+5,1, 128, 80)) 
				xDelta += 5;
			else if (playerDir == DOWN && CanMoveHere(1,yDelta+5, 128, 80)) 
				yDelta += 5;
			else if (playerDir == JUMP_up && CanMoveHere(xDelta,yDelta-10, 128, 80)) 
				yDelta -= 10;
			}
		
		if (CanMoveHere(xDelta,yDelta, 128, 80)) //"gravitacija"
			yDelta += gravity;
		}
	
	public void paintComponent(Graphics g) { //izriše vse 
		super.paintComponent(g);
		hitbox = new Rectangle((int)xDelta,(int)yDelta,128, 80); //hitbox strička 
		updateAnimationTick();
		setAnimacija();
		updatePos();
		
		g.drawImage(bg1,0,0,null);
		g.setColor(Color.PINK);
		g.drawRect( hitbox.x,hitbox.y,128, 80);
		g.drawImage(animacije[playerAction][aniIndex], (int) xDelta, (int) yDelta, 128, 80, null);

		
	}



		
	

}