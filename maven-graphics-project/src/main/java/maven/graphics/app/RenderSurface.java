package maven.graphics.app;

import java.awt.image.BufferedImage;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import maven.graphics.coreTypes.ColorT;

public class RenderSurface extends WritableImage {

	public static int defValue = -1;

	private ColorT surface[][];

	public RenderSurface(int width, int height)
	{
		super(width, height);
		surface = new ColorT[height][width];
		for (int i = 0; i < surface.length; ++i) {
			for (int j = 0; j < surface[i].length; ++j) {
				surface[i][j] = new ColorT();
			}
		}
		insertArray();
	}
	
	public ColorT[][] getSurface()
	{
		return surface;
	}
	
    public void insertArray()
    {
        //Creating a writable image 
    	int height = surface.length;
    	int width = surface[0].length;
        //getting the pixel writer 
        PixelWriter writer = this.getPixelWriter();
        for(int y = 0; y < height; y++) { 
           for(int x = 0; x < width; x++) { 
        	   double pixelr = surface[y][x].r / 256.0;
        	   double pixelg = surface[y][x].g / 256.0;
        	   double pixelb = surface[y][x].b / 256.0;
              	// Setting the color to the writable image
			   if(!surface[y][x].isDef())
				   writer.setColor(x, y, Color.color(pixelr, pixelg, pixelb));
			   else
				   writer.setColor(x, y, Color.color(0, 0, 0));
			   }
           }
    }
    
	public BufferedImage toImage() 
	{
		BufferedImage bi = new BufferedImage(surface[0].length, surface.length, BufferedImage.TYPE_INT_RGB);
    	// -- prepare output image
    	for (int i = 0; i < bi.getHeight(); ++i) {
    	    for (int j = 0; j < bi.getWidth(); ++j) {
				int pixel;
    	    	if (surface[i][j].isDef())
    				pixel =	(0 << 16) | (0 << 8) | (0);
    	    	else
					pixel =	(surface[i][j].r << 16) | (surface[i][j].g << 8) | (surface[i][j].b);
    			bi.setRGB(j, i, pixel);
    		}
    	}
    	return bi;
	}

	public void clear()
	{
		surface = new ColorT[surface.length][surface[0].length];
		for (int i = 0; i < surface.length; ++i) {
			for (int j = 0; j < surface[i].length; ++j) {
				surface[i][j] = new ColorT();
			}
		}
	}

	public void merge(ColorT[][] addOn)
	{
		for (int i = 0; i < surface.length; ++i) {
			for (int j = 0; j < surface[i].length; ++j) {
				if(!addOn[i][j].isDef())
					surface[i][j] = addOn[i][j];
			}
		}
	}

	public ColorT[][] empty()
	{
		ColorT[][] result = new ColorT[surface.length][surface[0].length];
		for (int i = 0; i < surface.length; ++i) {
			for (int j = 0; j < surface[i].length; ++j) {
				result[i][j] = new ColorT();
			}
		}
		return result;
	}
}
