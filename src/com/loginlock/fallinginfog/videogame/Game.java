package com.loginlock.fallinginfog.videogame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import com.loginlock.fallinginfog.control.Keyboard;

public class Game extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH_DIMENSION = 800;
	private static final int HEIGHT_DIMENSION = 600;
	private static final String NAME = "Game";
	private static JFrame window;
	
	public static volatile boolean runningUp = false;	//init game
	private static Thread thread;
	private Keyboard keyboard;
	
	private static int ups = 0;
	private static int fps = 0;
	
	/**
	 * 
	 */
	private Game() {
		setPreferredSize(new Dimension(WIDTH_DIMENSION, HEIGHT_DIMENSION));
		
		keyboard = new Keyboard();
		addKeyListener(keyboard);
		
		window = new JFrame(NAME);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.add(this, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.__init();
	}
	
	private synchronized void __init() {
		runningUp = true;

		thread = new Thread(this, "Graphics");
		thread.start();
	}
	
	private synchronized void __stop() {
		runningUp = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void __update() {
		keyboard.__update();
		
		if (keyboard.up) {
			System.out.println("UP");
		}
		if (keyboard.down) {
			System.out.println("DOWN");
		}
		if (keyboard.left) {
			System.out.println("LEFT");
		}
		if (keyboard.right) {
			System.out.println("RIGHT");
		}
		
		ups++;
	}
	
	private void __show() {
		fps++;
	}

	public void run() {
		
		final int NS_PER_SECOND = 1000000000;  //NANOSECONDS IN A SECOND
		final byte UPS_OBJECTIVE = 60; //UPDATES PER SECOND
		final double NS_PER_UPDATE = NS_PER_SECOND / UPS_OBJECTIVE; //NANOSECOND PER UPDATE
		
		long updateReference = System.nanoTime(); //TIME ON NANOSECONDS IN THIS MOMENT
		long countReference = System.nanoTime(); //SAW FPS

		double timeElapsed;  //TIME ELAPSED
		double delta = 0;  //TIME ELAPSED UNTIL UPDATE
		
		requestFocus(); //SET AVAIBLE WRITE KEYS OF KEYBOARD

		// INIT GAME
		while (runningUp) {
			
			//CALCULATING UPDATE OF FRAMES (MEDITION OF TIME ELAPSED)
			final long initBucle = System.nanoTime(); //DIFERENT TIME THAN UPDATE REFERENCE
			timeElapsed = initBucle - updateReference; //TIME ELAPSE SINCE INIT GAME
			updateReference = initBucle; //UPDATE TIME
			
			delta += timeElapsed / NS_PER_UPDATE;
			
			//WANT TO UPDATE 60 FPS
			while (delta >= 1) {
				__update();
				delta--;
			}

			__show();
			
			if (System.nanoTime() - countReference > NS_PER_SECOND) {
				window.setTitle(NAME + " || UPS: " + ups + " || FPS: " + fps);
				ups = 0;
				fps = 0;
				
				countReference = System.nanoTime();
			}
		}
	}
}
