package pkg2d3dgraphics;

import java.awt.image.BufferedImage;

public class IdentityFilter implements ImageFilter {
   BufferedImage originalImage;

   @Override
   public void setImage(BufferedImage image) {
      this.originalImage = image;
   }

   @Override
   public BufferedImage getImage() {
      return this.originalImage;
   }

   @Override
   public void applyFilter() throws FilterException {
      if (this.originalImage == null) {
         throw new FilterException();
      }
   }

   @Override
   public String getFilterName() {
      return "identity";
   }

   @Override
   public String getAuthor() {
      return "Author name";
   }
}
