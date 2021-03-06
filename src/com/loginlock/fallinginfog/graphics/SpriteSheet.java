package com.loginlock.fallinginfog.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private final int width;
	private final int height;
	public final int[] pixels;
	
	public SpriteSheet(final String path, final int width, final int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		
		BufferedImage image;
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResource(path));
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getWidth() {
		return width;
	}
	
}
