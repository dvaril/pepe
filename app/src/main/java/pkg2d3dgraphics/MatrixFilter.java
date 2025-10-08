package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MatrixFilter implements ImageFilter {
   BufferedImage originalImage;
   BufferedImage filteredImage;
   ImageMatrix matrix;

   @Override
   public void setImage(BufferedImage img) {
      this.originalImage = img;
   }

   public void setMatrix(ImageMatrix m) {
      this.matrix = m.getMatrixCopy();
   }

   @Override
   public void applyFilter() throws FilterException {
      if (this.originalImage != null && this.matrix != null) {
         this.filteredImage = new BufferedImage(this.originalImage.getWidth(), this.originalImage.getHeight(), this.originalImage.getType());

         for (int x = 0; x < this.originalImage.getWidth(); x++) {
            for (int y = 0; y < this.originalImage.getHeight(); y++) {
               int r = 0;
               int g = 0;
               int b = 0;

               for (int mX = 0; mX < this.matrix.length(0); mX++) {
                  for (int mY = 0; mY < this.matrix.length(1); mY++) {
                     if (this.matrix.get(mX, mY) != 0) {
                        int origXget = x + mX - this.matrix.length(0) / 2;
                        int origYget = y + mY - this.matrix.length(1) / 2;
                        if (origXget < 0) {
                           origXget = 0;
                        }

                        if (origYget < 0) {
                           origYget = 0;
                        }

                        if (origXget > this.originalImage.getWidth() - 1) {
                           origXget = this.originalImage.getWidth() - 1;
                        }

                        if (origYget > this.originalImage.getHeight() - 1) {
                           origYget = this.originalImage.getHeight() - 1;
                        }

                        Color c = new Color(this.originalImage.getRGB(origXget, origYget), false);
                        r += c.getRed() * this.matrix.get(mX, mY);
                        g += c.getGreen() * this.matrix.get(mX, mY);
                        b += c.getBlue() * this.matrix.get(mX, mY);
                     }
                  }
               }

               if (this.matrix.normalised()) {
                  r = Math.round(r * this.matrix.getCoefficient());
                  g = Math.round(g * this.matrix.getCoefficient());
                  b = Math.round(b * this.matrix.getCoefficient());
               }

               if (r > 255) {
                  r = 255;
               }

               if (g > 255) {
                  g = 255;
               }

               if (b > 255) {
                  b = 255;
               }

               if (r < 0) {
                  r = 0;
               }

               if (g < 0) {
                  g = 0;
               }

               if (b < 0) {
                  b = 0;
               }

               Color col = new Color(r, g, b);
               this.filteredImage.setRGB(x, y, col.getRGB());
            }
         }
      } else {
         throw new FilterException();
      }
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage;
   }

   @Override
   public String getFilterName() {
      return "matrix";
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }
}
