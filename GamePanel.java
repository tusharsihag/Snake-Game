import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener
{
	static final int width = 1300;
	static final int height = 750;
	static final int size = 50;
	static final int gameunits = (width*height)/(size*size);
	static final int delay = 175;
	final int x[] = new int[gameunits];
	final int y[] = new int[gameunits];
	int bodyParts = 6;
	int foodEaten;
	int foodX;
	int foodY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(new Color(0,100,0));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() 
	{
		newfood();
		running = true;
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) 
	{
		if(running) 
		{
			g.setColor(Color.black);
			g.fillRect(foodX, foodY, size, size);
		
			for(int i = 0; i< bodyParts;i++) 
			{
				g.setColor(Color.black);
				g.fillRect(x[i], y[i], size, size);
			}
			g.setColor(Color.green);
			g.setFont( new Font("Arial",Font.BOLD, 50));
			g.drawString("Score: "+foodEaten, 600,50);
		}
		else 
		{
			gameOver(g);
		}
		}
	public void newfood()
	{
		foodX = random.nextInt((int)(width/size))*size;
		foodY = random.nextInt((int)(height/size))*size;
	}
	public void move()
	{
		for(int i = bodyParts;i>0;i--) 
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) 
		{
		case 'U':
			y[0] = y[0] - size;
			break;
		case 'D':
			y[0] = y[0] + size;
			break;
		case 'L':
			x[0] = x[0] - size;
			break;
		case 'R':
			x[0] = x[0] + size;
			break;
		}
	}
	public void checkfood() 
	{
		if((x[0] == foodX) && (y[0] == foodY)) 
		{
			bodyParts++;
			foodEaten++;
			newfood();
		}
	}
	public void checkCollisions() 
	{
		for(int i = bodyParts;i>0;i--) 
		{
			if((x[0] == x[i])&& (y[0] == y[i])) 
			{
				running = false;
			}
		}
		if(x[0] < 0) 
		{
			running = false;
		}
		if(x[0] > width)
		{
			running = false;
		}
		if(y[0] < 0) 
		{
			running = false;
		}
		if(y[0] > height) 
		{
			running = false;
		}
		if(!running) 
		{
			timer.stop();
		}
	}
	public void gameOver(Graphics g) 
	{
		g.setColor(Color.green);
		g.setFont( new Font("Arial",Font.BOLD, 40));
		g.drawString("Score: "+foodEaten, 600,50);
		g.setColor(Color.green);
		g.setFont( new Font("Arial",Font.BOLD, 75));
		g.drawString("Game Over",450, 400);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(running) 
		{
			move();
			checkfood();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			switch(e.getKeyCode()) 
			{
			case KeyEvent.VK_LEFT:
				if(direction != 'R') 
				{
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
				{
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') 
				{
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') 
				{
					direction = 'D';
				}
				break;
				}
		}
	}
}






