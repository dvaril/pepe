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

public class ColorFilter extends JDialog implements ImageFilter {
   boolean applyFilter = false;
   BufferedImage originalImage;
   BufferedImage filteredImage;
   private JButton buttonCancel;
   private JButton buttonOK;
   private JPanel customPanel;
   private JLabel jLabel1;
   private JLabel jLabel2;
   private JLabel jLabel3;
   private JSlider sliderBlue;
   private JSlider sliderGreen;
   private JSlider sliderRed;

   public ColorFilter(Frame parent, boolean modal) {
      super(parent, modal);
      this.initComponents();
   }

   private void initComponents() {
      this.sliderRed = new JSlider();
      this.jLabel1 = new JLabel();
      this.jLabel2 = new JLabel();
      this.sliderGreen = new JSlider();
      this.jLabel3 = new JLabel();
      this.sliderBlue = new JSlider();
      this.customPanel = new CustomJPanel();
      this.buttonOK = new JButton();
      this.buttonCancel = new JButton();
      this.setDefaultCloseOperation(2);
      this.sliderRed.setMaximum(255);
      this.sliderRed.setToolTipText("");
      this.sliderRed.setValue(128);
      this.sliderRed.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            ColorFilter.this.sliderRedStateChanged(evt);
         }
      });
      this.jLabel1.setForeground(new Color(255, 0, 0));
      this.jLabel1.setText("Red");
      this.jLabel2.setForeground(new Color(51, 255, 0));
      this.jLabel2.setText("Green");
      this.sliderGreen.setMaximum(255);
      this.sliderGreen.setValue(128);
      this.sliderGreen.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            ColorFilter.this.sliderGreenStateChanged(evt);
         }
      });
      this.jLabel3.setForeground(new Color(0, 102, 204));
      this.jLabel3.setText("Blue");
      this.sliderBlue.setMaximum(255);
      this.sliderBlue.setValue(128);
      this.sliderBlue.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            ColorFilter.this.sliderBlueStateChanged(evt);
         }
      });
      GroupLayout customPanelLayout = new GroupLayout(this.customPanel);
      this.customPanel.setLayout(customPanelLayout);
      customPanelLayout.setHorizontalGroup(customPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
      customPanelLayout.setVerticalGroup(customPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 238, 32767));
      this.buttonOK.setText("OK");
      this.buttonOK.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ColorFilter.this.buttonOKActionPerformed(evt);
         }
      });
      this.buttonCancel.setText("Cancel");
      this.buttonCancel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ColorFilter.this.buttonCancelActionPerformed(evt);
         }
      });
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.customPanel, Alignment.TRAILING, -1, -1, 32767)
                        .addComponent(this.sliderGreen, -1, 380, 32767)
                        .addComponent(this.sliderRed, -1, -1, 32767)
                        .addComponent(this.sliderBlue, -1, -1, 32767)
                        .addGroup(
                           layout.createSequentialGroup()
                              .addGroup(
                                 layout.createParallelGroup(Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3)
                              )
                              .addGap(0, 0, 32767)
                        )
                        .addGroup(
                           Alignment.TRAILING,
                           layout.createSequentialGroup()
                              .addGap(0, 0, 32767)
                              .addComponent(this.buttonCancel)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonOK)
                        )
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
                  .addComponent(this.customPanel, -1, -1, 32767)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.jLabel1)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.sliderRed, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.jLabel2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.sliderGreen, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.jLabel3)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.sliderBlue, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.buttonOK).addComponent(this.buttonCancel))
                  .addContainerGap()
            )
      );
      this.pack();
   }

   @Override
   public void dispose() {
      if (!this.applyFilter) {
         this.filteredImage = this.originalImage;
      }

      super.dispose();
   }

   private void buttonOKActionPerformed(ActionEvent evt) {
      this.saveAndExit();
   }

   private void buttonCancelActionPerformed(ActionEvent evt) {
      this.dontSaveAndExit();
   }

   private void sliderRedStateChanged(ChangeEvent evt) {
      this.newFilterApply();
   }

   private void sliderGreenStateChanged(ChangeEvent evt) {
      this.newFilterApply();
   }

   private void sliderBlueStateChanged(ChangeEvent evt) {
      this.newFilterApply();
   }

   private void newFilterApply() {
      try {
         this.applyFilter(this.originalImage);
      } catch (FilterException var2) {
      }
   }

   private void saveAndExit() {
      this.applyFilter = true;
      this.dispose();
   }

   private void dontSaveAndExit() {
      this.applyFilter = false;
      this.dispose();
   }

   public static void main(String[] args) {
      try {
         for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException var5) {
         Logger.getLogger(ColorFilter.class.getName()).log(Level.SEVERE, null, var5);
      } catch (InstantiationException var6) {
         Logger.getLogger(ColorFilter.class.getName()).log(Level.SEVERE, null, var6);
      } catch (IllegalAccessException var7) {
         Logger.getLogger(ColorFilter.class.getName()).log(Level.SEVERE, null, var7);
      } catch (UnsupportedLookAndFeelException var8) {
         Logger.getLogger(ColorFilter.class.getName()).log(Level.SEVERE, null, var8);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ColorFilter dialog = new ColorFilter(new JFrame(), true);
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

   @Override
   public void setImage(BufferedImage image) {
      this.originalImage = image;

      try {
         this.applyFilter(this.originalImage);
      } catch (FilterException var4) {
         ExceptionDialog ed = new ExceptionDialog(null, true);
         ed.setText(var4.getMessage());
         ed.setVisible(true);
         this.dontSaveAndExit();
      }
   }

   public void applyFilter(BufferedImage image) throws FilterException {
      if (image == null) {
         throw new FilterException("Unable to localize source image");
      } else {
         this.filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

         for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
               Color c = new Color(image.getRGB(x, y));
               int red = c.getRed() + (this.sliderRed.getValue() * 2 - 256);
               int green = c.getGreen() + (this.sliderGreen.getValue() * 2 - 256);
               int blue = c.getBlue() + (this.sliderBlue.getValue() * 2 - 256);
               red = Math.min(255, red);
               red = Math.max(0, red);
               green = Math.min(255, green);
               green = Math.max(0, green);
               blue = Math.min(255, blue);
               blue = Math.max(0, blue);
               Color o = new Color(Math.abs(red), green, blue);
               this.filteredImage.setRGB(x, y, o.getRGB());
            }
         }

         ((CustomJPanel)this.customPanel).setImage(this.filteredImage);
         ((CustomJPanel)this.customPanel).repaint();
      }
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage;
   }

   @Override
   public void applyFilter() throws FilterException {
   }

   @Override
   public String getFilterName() {
      return "Colorizer";
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }
}
