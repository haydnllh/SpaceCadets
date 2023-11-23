import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Imageproc {
  BufferedImage img = null;

  public Imageproc(String path){
    File f = null;
    try {
      f = new File(path);
      img = ImageIO.read(f);
    } catch (IOException e) {
      System.out.println(e);
    }
    int width = img.getWidth();
    int height = img.getHeight();
    int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
    // convert to grayscale
    for (int i = 0; i < pixels.length; i++) {
      int p = pixels[i];

      int a = (p >> 24) & 0xff;
      int r = (p >> 16) & 0xff;
      int g = (p >> 8) & 0xff;
      int b = p & 0xff;
      int avg = (r + g + b) / 3;
      p = (a << 24) | (avg << 16) | (avg << 8) | avg;

      pixels[i] = p;
    }
    img.setRGB(0, 0, width, height, pixels, 0, width);
  }

  public BufferedImage getImg(){
    return img;
  }
}
