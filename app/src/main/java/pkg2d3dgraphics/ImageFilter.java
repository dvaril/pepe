package pkg2d3dgraphics;

import java.awt.image.BufferedImage;

public interface ImageFilter {
   void setImage(BufferedImage var1);

   BufferedImage getImage();

   void applyFilter() throws FilterException;

   String getFilterName();

   String getAuthor();
}
