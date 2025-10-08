package pkg2d3dgraphics;

public class ImageMatrix {
   private int[][] matrix;
   private boolean normalized;
   private int maxX;
   private int maxY;
   private float coefficient;

   public ImageMatrix() {
      this.maxX = 8;
      this.maxY = 8;
      this.coefficient = 0.0F;
      this.matrix = new int[this.maxX + 1][this.maxY + 1];
      this.clearMatrix();
      this.normalized = false;
   }

   public ImageMatrix(int sizeX, int sizeY, boolean norm) {
      this.coefficient = 0.0F;
      this.maxX = sizeX - 1;
      this.maxY = sizeY - 1;
      this.matrix = new int[this.maxX + 1][this.maxY + 1];
      this.clearMatrix();
      this.normalized = norm;
   }

   public ImageMatrix(int[][] m, boolean n) {
      this.coefficient = 0.0F;
      this.maxX = m.length - 1;
      this.maxY = 0;
      if (this.maxX > 0) {
         this.maxY = m[0].length - 1;
      }

      this.matrix = new int[this.maxX + 1][this.maxY + 1];

      for (int a = 0; a <= this.maxX; a++) {
         for (int b = 0; b <= this.maxY; b++) {
            this.matrix[a][b] = m[a][b];
         }
      }

      this.normalized = n;
   }

   public void clearMatrix() {
      for (int x = 0; x <= this.maxX; x++) {
         for (int y = 0; y <= this.maxY; y++) {
            this.matrix[x][y] = 0;
         }
      }

      this.coefficient = 0.0F;
      this.normalized = false;
   }

   public int get(int x, int y) {
      return this.maxX >= x && this.maxY >= y ? this.matrix[x][y] : 0;
   }

   public ImageMatrix getMatrixCopy() {
      ImageMatrix mOut = new ImageMatrix(this.maxX + 1, this.maxY + 1, this.normalized);

      for (int x = 0; x <= this.maxX; x++) {
         for (int y = 0; y <= this.maxY; y++) {
            mOut.set(x, y, this.matrix[x][y]);
         }
      }

      mOut.setCoefficient(this.coefficient);
      mOut.setNormalization(this.normalized);
      return mOut;
   }

   public void setNormalization(boolean norm) {
      this.normalized = norm;
   }

   public float getCoefficient() {
      return this.coefficient;
   }

   public void setCoefficient(float f) {
      this.coefficient = f;
   }

   public void set(int x, int y, int value) {
      this.matrix[x][y] = value;
   }

   public int length(int xy) {
      return xy == 0 ? this.matrix.length : this.matrix[0].length;
   }

   public boolean normalised() {
      return this.normalized;
   }

   void printOut() {
      for (int x = 0; x <= this.maxX; x++) {
         for (int y = 0; y <= this.maxY; y++) {
            System.out.print(this.get(x, y) + " ");
         }

         System.out.print("\n");
      }

      System.out.println("Normalisation:" + this.normalized);
      System.out.println("Coefficient:" + this.coefficient);
   }
}
