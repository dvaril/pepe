package pkg2d3dgraphics;

public class NoFilterException extends Exception {
   public NoFilterException() {
   }

   public NoFilterException(String msg) {
      super("NoFilter Found:" + msg);
   }
}
