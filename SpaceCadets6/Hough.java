import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Hough {
  int[][][] accuArray;
  public int[] transform(BufferedImage image, int[][] imageMatrix) {
    //(a-x)^2 + (b-y)^2 = r^2
    //x=rcostheta
    //y=rsintheta
    int width = image.getWidth();
    int height = image.getHeight();
    accuArray = new int[width][height][500];
    for (int i=0; i<width; i++) {
      for (int j = 0; j < height; j++) {
        for (int k = 0; k < 500; k++) {
          accuArray[i][j][k] = 0;
        }
      }
    }
    int a,b,r;
    double x,y,lastx = 0,lasty = 0;
    for (a=0; a<imageMatrix.length; a+=3) {
      for (b = 0; b < imageMatrix[0].length; b += 3) {
        for (r = 1; r < 100; r++) {
          for (double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/36) {
            x = Math.round(r * Math.cos(theta) + a);
            y = Math.round(r * Math.sin(theta) + b);
            try {
              if (imageMatrix[(int) x][(int) y] == 1 && lastx != x && lasty != y) {
                accuArray[a][b][r] += 1;
                lastx = x; lasty = y;
              }
            } catch (ArrayIndexOutOfBoundsException e) {
              continue;
            }
          }
        }
      }
    }
    int max = 0;
    int aa=0,bb=0,rr=0;
    for (int i=0; i<width; i++){
      for (int j=0; j<height; j++){
        for (int k=0; k<200; k++){
          if (accuArray[i][j][k] > max){
            max = accuArray[i][j][k];
            aa =i; bb=j; rr=k;
          }
        }
      }
    }
    return new int[]{aa,bb,rr};
  }
}
