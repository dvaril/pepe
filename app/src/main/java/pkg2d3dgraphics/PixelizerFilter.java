package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PixelizerFilter extends JDialog implements ImageFilter {
   BufferedImage filteredImage;
   BufferedImage originalImage;
   int factor = 1;
   boolean applyChanges = false;
   private JButton buttonCancel;
   private JButton buttonOK;
   private JPanel customJPanel;
   private JLabel labelFactor;
   private JSlider sliderFactor;

   public PixelizerFilter(Frame parent, boolean modal) {
      super(parent, modal);
      this.initComponents();
   }

   private void initComponents() {
      this.buttonOK = new JButton();
      this.buttonCancel = new JButton();
      this.customJPanel = new CustomJPanel();
      this.sliderFactor = new JSlider();
      this.labelFactor = new JLabel();
      this.setDefaultCloseOperation(2);
      this.buttonOK.setText("OK");
      this.buttonOK.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            PixelizerFilter.this.buttonOKActionPerformed(evt);
         }
      });
      this.buttonCancel.setText("Cancel");
      this.buttonCancel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            PixelizerFilter.this.buttonCancelActionPerformed(evt);
         }
      });
      GroupLayout customJPanelLayout = new GroupLayout(this.customJPanel);
      this.customJPanel.setLayout(customJPanelLayout);
      customJPanelLayout.setHorizontalGroup(customJPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
      customJPanelLayout.setVerticalGroup(customJPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 246, 32767));
      this.sliderFactor.setMinimum(1);
      this.sliderFactor.setValue(1);
      this.sliderFactor.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            PixelizerFilter.this.sliderFactorStateChanged(evt);
         }
      });
      this.labelFactor.setText("0");
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           layout.createSequentialGroup()
                              .addComponent(this.labelFactor, -2, 70, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.sliderFactor, -2, 182, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonCancel)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonOK)
                        )
                        .addComponent(this.customJPanel, -1, -1, 32767)
                  )
                  .addContainerGap()
            )
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.customJPanel, -1, -1, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                           layout.createParallelGroup(Alignment.BASELINE)
                              .addComponent(this.buttonOK, -1, -1, 32767)
                              .addComponent(this.buttonCancel, -1, -1, 32767)
                        )
                        .addComponent(this.sliderFactor, -2, 0, 32767)
                        .addComponent(this.labelFactor, -1, -1, 32767)
                  )
                  .addGap(14, 14, 14)
            )
      );
      this.pack();
   }

   private void buttonOKActionPerformed(ActionEvent evt) {
      this.closeAndApply();
   }

   private void buttonCancelActionPerformed(ActionEvent evt) {
      this.closeAndNotApply();
   }

   private void sliderFactorStateChanged(ChangeEvent evt) {
      this.setFactor(this.sliderFactor.getValue());
      this.labelFactor.setText("" + this.factor);

      try {
         this.applyFilter(this.originalImage);
      } catch (FilterException var3) {
      }
   }

   public static void main(String[] args) {
      try {
         for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var5) {
         Logger.getLogger(PixelizerFilter.class.getName()).log(Level.SEVERE, null, var5);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            PixelizerFilter dialog = new PixelizerFilter(new JFrame(), true);
            dialog.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosing(WindowEvent e) {
                  System.exit(0);
               }
            });
            dialog.setVisible(true);
         }
      });
   }

   public void setFactor(int fac) {
      this.factor = Math.max(fac, 1);
      this.factor = Math.min(255, this.factor);
   }

   @Override
   public void setImage(BufferedImage image) {
      this.originalImage = image;
      this.setFactor(1);

      try {
         this.applyFilter(this.originalImage);
      } catch (FilterException var4) {
         ExceptionDialog ed = new ExceptionDialog(null, true);
         ed.setText(var4.getMessage());
         ed.setVisible(true);
         this.closeAndNotApply();
      }
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage;
   }

   public void applyFilter(BufferedImage image) throws FilterException {
      try {
         this.filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

         for (int x = 0; x < image.getWidth(); x += this.factor * 2) {
            for (int y = 0; y < image.getHeight(); y += this.factor * 2) {
               for (int a = x - this.factor; a < x + this.factor; a++) {
                  for (int b = y - this.factor; b < y + this.factor; b++) {
                     int getA = Math.min(image.getWidth() - 1, Math.max(0, a));
                     int getB = Math.min(image.getHeight() - 1, Math.max(0, b));
                     Color c = new Color(image.getRGB(x, y));
                     this.filteredImage.setRGB(getA, getB, c.getRGB());
                  }
               }
            }
         }

         ((CustomJPanel)this.customJPanel).setImage(this.filteredImage);
         ((CustomJPanel)this.customJPanel).repaint();
      } catch (Exception var9) {
         throw new FilterException();
      }
   }

   @Override
   public void applyFilter() throws FilterException {
      this.applyFilter(this.originalImage);
   }

   @Override
   public String getFilterName() {
      return "Pixelizer";
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }

   @Override
   public void dispose() {
      if (!this.applyChanges) {
         this.filteredImage = this.originalImage;
      }

      super.dispose();
   }

   private void closeAndApply() {
      this.applyChanges = true;
      this.dispose();
   }

   private void closeAndNotApply() {
      this.applyChanges = false;
      this.dispose();
   }
}
