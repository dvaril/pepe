package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MatrixJPanel extends JPanel {
   private ImageMatrix matrix;

   public void setMatrix(ImageMatrix m) {
      this.matrix = m.getMatrixCopy();
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (this.matrix != null) {
         int height = super.getHeight();
         int width = super.getWidth();
         int pixelPerBlockHeight = Math.round((float)(height / this.matrix.length(0)));
         int pixelPerBlockWidth = Math.round((float)(width / this.matrix.length(1)));
         int mMax = 0;
         int mMin = 0;

         for (int x = 0; x < this.matrix.length(0); x++) {
            for (int y = 0; y < this.matrix.length(1); y++) {
               mMax = Math.max(mMax, this.matrix.get(x, y));
               mMin = Math.min(mMin, this.matrix.get(x, y));
            }
         }

         int div = Math.max(mMax, Math.abs(mMin));
         int step = 0;
         if (div != 0) {
            step = 127 / (div + 1);
         }

         int sizeX = 1;
         int sizeY = 2;

         for (int x = 0; x < this.matrix.length(0); x++) {
            for (int y = 0; y < this.matrix.length(1); y++) {
               Color c;
               if (this.matrix.get(x, y) == 0) {
                  c = new Color(128, 0, 0);
               } else {
                  c = new Color(128 + this.matrix.get(x, y) * step, 0, 0);
               }

               g.setColor(c);
               g.fillRect(x * pixelPerBlockWidth, y * pixelPerBlockHeight, pixelPerBlockWidth, pixelPerBlockHeight);
            }
         }
      } else {
         new Color(128, 0, 0);
         g.fillRect(0, 0, super.getWidth(), super.getHeight());
      }
   }
}
