import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class DisplayImage {
  public static void display(BufferedImage image){
    JFrame frame = null;
    JLabel label = null;
    if(frame==null){
      frame=new JFrame();
      frame.setTitle("stained_image");
      frame.setSize(image.getWidth(), image.getHeight());
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      label=new JLabel();
      label.setIcon(new ImageIcon(image));
      frame.getContentPane().add(label, BorderLayout.CENTER);
      frame.setLocationRelativeTo(null);
      frame.pack();
      frame.setVisible(true);
    }
    else {
      label.setIcon(new ImageIcon(image));
    }
  }
}
