package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomJPanel extends JPanel {
   private BufferedImage image;
   private BufferedImage smallImage;
   private JLabel warningLabel;
   private int coef = 0;
   private int lastWidth = 0;
   private int lastHeight = 0;
   private boolean generateNewImage = true;

   public CustomJPanel() {
      this.warningLabel = new JLabel(" ");
      this.warningLabel.setSize(150, 20);
      this.warningLabel.setLocation(10, 10);
      this.add(this.warningLabel);
   }

   public void setImage(BufferedImage img) {
      this.image = img;
      this.generateNewImage = true;
      this.generateSmallImage();
   }

   private void generateSmallImage() {
      int height = super.getHeight();
      int width = super.getWidth();
      int coefF = 1;
      if (this.image != null) {
         while (height < this.image.getHeight() / coefF) {
            coefF++;
         }

         while (width < this.image.getWidth() / coefF) {
            coefF++;
         }

         if (this.coef != coefF || this.generateNewImage) {
            this.coef = coefF;
            this.resizeImage(this.coef, this.image);
            this.generateNewImage = false;
         }
      }
   }

   private void resizeImage(int coefficient, BufferedImage img) {
      if (coefficient < 1) {
         coefficient = 1;
      }

      int height = (int)Math.floor(img.getHeight() / coefficient);
      int width = (int)Math.floor(img.getWidth() / coefficient);
      this.smallImage = new BufferedImage(width, height, img.getType());

      for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
            this.smallImage.setRGB(x, y, img.getRGB(x * coefficient, y * coefficient));
         }
      }
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (this.lastHeight != super.getHeight() || this.lastWidth != super.getWidth()) {
         this.generateSmallImage();
      }

      g.drawImage(this.smallImage, 0, 0, this);
      this.lastHeight = super.getHeight();
      this.lastWidth = super.getHeight();
      if (this.coef > 1) {
         this.warningLabel.setText("WARNING: scale:" + this.coef);
         this.warningLabel.setForeground(Color.red);
      } else {
         this.warningLabel.setText(" ");
      }
   }
}
