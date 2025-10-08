package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class NegativeFilter implements ImageFilter {
   private BufferedImage originalImage;
   private BufferedImage filteredImage;

   @Override
   public void applyFilter() throws FilterException {
      try {
         this.filteredImage = new BufferedImage(this.originalImage.getWidth(), this.originalImage.getHeight(), this.originalImage.getType());

         for (int x = 0; x < this.originalImage.getWidth(); x++) {
            for (int y = 0; y < this.originalImage.getHeight(); y++) {
               int rgbOrig = this.originalImage.getRGB(x, y);
               Color c = new Color(rgbOrig);
               int r = 255 - c.getRed();
               int g = 255 - c.getGreen();
               int b = 255 - c.getBlue();
               Color nc = new Color(r, g, b);
               this.filteredImage.setRGB(x, y, nc.getRGB());
            }
         }
      } catch (Exception var9) {
         throw new FilterException(var9.getMessage());
      }
   }

   @Override
   public String getFilterName() {
      return "negative";
   }

   @Override
   public void setImage(BufferedImage image) {
      this.originalImage = image;
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage == null ? this.originalImage : this.filteredImage;
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }
}
