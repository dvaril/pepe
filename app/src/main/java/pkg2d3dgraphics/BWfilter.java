package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BWfilter implements ImageFilter {
   BufferedImage originalImage;
   BufferedImage filteredImage;

   @Override
   public void setImage(BufferedImage image) {
      this.originalImage = image;
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage;
   }

   @Override
   public void applyFilter() throws FilterException {
      if (this.originalImage == null) {
         throw new FilterException();
      } else {
         this.filteredImage = new BufferedImage(this.originalImage.getWidth(), this.originalImage.getHeight(), this.originalImage.getType());

         for (int x = 0; x < this.originalImage.getWidth(); x++) {
            for (int y = 0; y < this.originalImage.getHeight(); y++) {
               Color c = new Color(this.originalImage.getRGB(x, y));
               int col = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
               Color color = new Color(col, col, col);
               this.filteredImage.setRGB(x, y, color.getRGB());
            }
         }
      }
   }

   @Override
   public String getFilterName() {
      return "BW filter";
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }
}
