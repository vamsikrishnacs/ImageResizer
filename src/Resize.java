import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Resize {

	private static final int IMG_WIDTH = 640;
	private static final int IMG_HEIGHT = 480;
	Image img;

	int width=640;
	int height=480;

	Resize(Image i)
	{
		this.img=i;
	}


	BufferedImage resizeImage()
	{
		BufferedImage resizedImage=null;
		try{
			resizedImage= new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = resizedImage.createGraphics();

			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawImage(img, 0, 0, width, height, null);
			g.dispose();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return resizedImage;

	}

}
