package com.loginlock.fallinginfog.graphics;

public final class Screen {
	private final int width;
	private final int height;
	
	public final int[] pixels;
	
	public Screen(final int width, final int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
	}
	
	public void __clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void __show(final int compensationX, final int compensationY) {
		for (int y = 0; y < height; y++) {
			int positionY = y + compensationY;
			
			if (positionY < 0 || positionY >= height) {
				continue;
			}
			
			for (int x = 0; x < width; x++) {
				int positionX = x + compensationX;
				
				if (positionX < 0 || positionX >= width) {
					continue;
				}
				
				//CODE TO REWRITE
			}
		}
	}
}
