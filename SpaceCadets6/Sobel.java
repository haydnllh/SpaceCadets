import java.awt.image.BufferedImage;

public class Sobel {
  private int[][] newImageMatrix;
  public Sobel(int[][] kernel, int[][] imageMatrix, BufferedImage image){
    newImageMatrix = new int[image.getWidth()][image.getHeight()];
    int newPixel;
    int i,j,x,y;
    for (i=0; i<imageMatrix.length; i++){
      for(j=0; j<imageMatrix[0].length; j++){
        newPixel = 0;
        for (x=0; x<3; x++){
          for (y=0; y<3; y++){
            try{
              newPixel += kernel[x][y] * imageMatrix[i+x][j+y];
            }
            catch (ArrayIndexOutOfBoundsException e){
              continue;
            }
          }
        }
        newImageMatrix[i][j] = newPixel;
      }
    }
  }
  public int[][] getNewImageMatrix(){
    return newImageMatrix;
  }
}
