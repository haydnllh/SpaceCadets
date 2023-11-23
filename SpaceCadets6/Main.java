import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.*;
import javax.imageio.ImageIO;

public class Main{
  public static void main(String[] args) throws IOException {
    int i,j,x,y;
    String path = null;//enter file path
    Imageproc imageProc = new Imageproc(path);
    BufferedImage image = imageProc.getImg();
    BufferedImage originalImage = ImageIO.read(new File(path));
    int width = image.getWidth();
    int height = image.getHeight();
    int[][] imageMatrix = new int[width][height];
    for (i=0; i<width; i++){
      for (j=0; j<height; j++){
        imageMatrix[i][j] = image.getRGB(i,j);
      }
    }
    int[][] kernel1 = {
        {1,0,-1},
        {2,0,-2},
        {1,0,-1}
    };
    int[][] kernel2 = {
        {1,2,1},
        {0,0,0},
        {-1,-2,-1}
    };

    Sobel vertical = new Sobel(kernel1, imageMatrix, image);
    Sobel horizontal = new Sobel(kernel2, imageMatrix, image);
    int[][] verMat = vertical.getNewImageMatrix();
    int[][] horMat = horizontal.getNewImageMatrix();
    int[][] newMatrix = new int[image.getWidth()][image.getHeight()];
    int a = verMat.length;
    int ah = verMat[0].length;
    int newPixel = 0;
    for (x=0; x<a; x++){
      for (y=0; y<ah; y++){
        try{
          newPixel = (int) Math.sqrt(Math.pow(verMat[x][y],2) + Math.pow(horMat[x][y],2));
          if (newPixel >10000000){
            image.setRGB(x, y, 16777215);
            newMatrix[x][y]=1;
          }
          else {
            image.setRGB(x,y, 0);
            newMatrix[x][y]=0;
          }
        }
        catch(ArrayIndexOutOfBoundsException e){
          continue;
        }
      }
    }
    Hough hough = new Hough();
    int[] abr = hough.transform(image, newMatrix);
    int circleA = abr[0], circleB = abr[1], circleR = abr[2];
    double verifier =0;
    for (int circleX=0; circleX<image.getWidth(); circleX++){
      for (int circleY=0; circleY<image.getHeight(); circleY++){
        verifier=Math.pow((circleX - circleA),2) + Math.pow((circleY - circleB),2);
        if (verifier >= Math.pow(circleR,2)-50 && verifier <= Math.pow(circleR,2)+50){
          for (int o = -2; o<3; o++){
            originalImage.setRGB(circleX+o,circleY,32768);
          }
          for (int p = -2; p<3; p++){
            originalImage.setRGB(circleX,circleY+p,32768);
          }
          originalImage.setRGB(circleX,circleY,32768);
        }
      }
    }
    DisplayImage displayer = new DisplayImage();
    displayer.display(originalImage);
  }
}