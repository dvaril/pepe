package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Random;
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

public class OldFilmFilter extends JDialog implements ImageFilter {
   private boolean applyChanges;
   private BufferedImage originalImage;
   private BufferedImage filteredImage;
   private int noisePercentage;
   private int noiseIntesity;
   private JButton cancelButton;
   private JPanel imagePanel;
   private JLabel jLabel1;
   private JLabel jLabel2;
   private JLabel noiseCoverageLabel;
   private JSlider noiseCoverageSlider;
   private JSlider noiseIntensitySlider;
   private JLabel noiseIntesityLabel;
   private JButton okButton;

   public OldFilmFilter(Frame parent, boolean modal) {
      super(parent, modal);
      this.initComponents();
      this.applyChanges = false;
      this.noisePercentage = 50;
      this.noiseIntesity = 50;
   }

   public void saveAndClose() {
      this.applyChanges = true;
      this.dispose();
   }

   public void close() {
      this.applyChanges = false;
      this.dispose();
   }

   @Override
   public void dispose() {
      if (!this.applyChanges) {
         this.filteredImage = this.originalImage;
      }

      super.dispose();
   }

   private void initComponents() {
      this.imagePanel = new CustomJPanel();
      this.noiseCoverageSlider = new JSlider();
      this.okButton = new JButton();
      this.cancelButton = new JButton();
      this.jLabel1 = new JLabel();
      this.noiseCoverageLabel = new JLabel();
      this.noiseIntensitySlider = new JSlider();
      this.jLabel2 = new JLabel();
      this.noiseIntesityLabel = new JLabel();
      this.setDefaultCloseOperation(2);
      GroupLayout imagePanelLayout = new GroupLayout(this.imagePanel);
      this.imagePanel.setLayout(imagePanelLayout);
      imagePanelLayout.setHorizontalGroup(imagePanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
      imagePanelLayout.setVerticalGroup(imagePanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 306, 32767));
      this.noiseCoverageSlider.setMinorTickSpacing(1);
      this.noiseCoverageSlider.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            OldFilmFilter.this.noiseCoverageSliderStateChanged(evt);
         }
      });
      this.okButton.setText("OK");
      this.okButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            OldFilmFilter.this.okButtonActionPerformed(evt);
         }
      });
      this.cancelButton.setText("Cancel");
      this.cancelButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            OldFilmFilter.this.cancelButtonActionPerformed(evt);
         }
      });
      this.jLabel1.setText("noise:");
      this.noiseCoverageLabel.setText("50 %");
      this.noiseIntensitySlider.setMinorTickSpacing(1);
      this.noiseIntensitySlider.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            OldFilmFilter.this.noiseIntensitySliderStateChanged(evt);
         }
      });
      this.jLabel2.setText("noise intensity:");
      this.noiseIntesityLabel.setText("50 %");
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.imagePanel, -1, -1, 32767)
                        .addGroup(
                           Alignment.TRAILING,
                           layout.createSequentialGroup()
                              .addGroup(
                                 layout.createParallelGroup(Alignment.TRAILING)
                                    .addComponent(this.noiseIntensitySlider, Alignment.LEADING, -1, 256, 32767)
                                    .addComponent(this.noiseCoverageSlider, -1, -1, 32767)
                              )
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.cancelButton)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.okButton)
                        )
                        .addGroup(
                           layout.createSequentialGroup()
                              .addGroup(
                                 layout.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       layout.createSequentialGroup()
                                          .addGap(4, 4, 4)
                                          .addComponent(this.jLabel1)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.noiseCoverageLabel)
                                    )
                                    .addGroup(
                                       layout.createSequentialGroup()
                                          .addComponent(this.jLabel2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.noiseIntesityLabel)
                                    )
                              )
                              .addGap(0, 0, 32767)
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
                  .addComponent(this.imagePanel, -1, -1, 32767)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.noiseIntesityLabel))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.noiseIntensitySlider, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     layout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.okButton).addComponent(this.cancelButton))
                        .addGroup(
                           layout.createSequentialGroup()
                              .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.noiseCoverageLabel))
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.noiseCoverageSlider, -2, -1, -2)
                        )
                  )
                  .addContainerGap()
            )
      );
      this.pack();
   }

   private void noiseCoverageSliderStateChanged(ChangeEvent evt) {
      this.updateGUI();
   }

   private void noiseIntensitySliderStateChanged(ChangeEvent evt) {
      this.updateGUI();
   }

   private void okButtonActionPerformed(ActionEvent evt) {
      this.saveAndClose();
   }

   private void cancelButtonActionPerformed(ActionEvent evt) {
      this.close();
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
         Logger.getLogger(OldFilmFilter.class.getName()).log(Level.SEVERE, null, var5);
      } catch (InstantiationException var6) {
         Logger.getLogger(OldFilmFilter.class.getName()).log(Level.SEVERE, null, var6);
      } catch (IllegalAccessException var7) {
         Logger.getLogger(OldFilmFilter.class.getName()).log(Level.SEVERE, null, var7);
      } catch (UnsupportedLookAndFeelException var8) {
         Logger.getLogger(OldFilmFilter.class.getName()).log(Level.SEVERE, null, var8);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            OldFilmFilter dialog = new OldFilmFilter(new JFrame(), true);
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

   private void updateGUI() {
      this.noiseIntesity = this.noiseIntensitySlider.getValue();
      this.noiseIntesityLabel.setText(this.noiseIntesity + " %");
      this.noisePercentage = this.noiseCoverageSlider.getValue();
      this.noiseCoverageLabel.setText(this.noisePercentage + " %");
      this.applyFilter(this.originalImage);
      ((CustomJPanel)this.imagePanel).setImage(this.filteredImage);
      ((CustomJPanel)this.imagePanel).repaint();
   }

   @Override
   public void setImage(BufferedImage image) {
      this.originalImage = image;
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage == null ? this.originalImage : this.filteredImage;
   }

   static BufferedImage deepCopy(BufferedImage bi) {
      ColorModel cm = bi.getColorModel();
      boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
      WritableRaster raster = bi.copyData(null);
      return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
   }

   private void applyFilter(BufferedImage img) {
      if (img != null) {
         this.filteredImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
         this.filteredImage = deepCopy(img);
         int pixels = img.getWidth() * img.getHeight();
         int pixelsToRunOn = (int)(pixels * (this.noisePercentage / 100.0F));

         for (int a = 0; a < pixelsToRunOn; a++) {
            Random rand = new Random();
            int x = rand.nextInt(img.getWidth());
            int y = rand.nextInt(img.getHeight());
            int moveR = (int)((rand.nextInt(512) - 255) * (this.noiseIntesity / 100.0F));
            int moveG = (int)((rand.nextInt(512) - 255) * (this.noiseIntesity / 100.0F));
            int moveB = (int)((rand.nextInt(512) - 255) * (this.noiseIntesity / 100.0F));
            Color c = new Color(img.getRGB(x, y));
            int r = c.getRed() + moveR;
            int g = c.getGreen() + moveG;
            int b = c.getBlue() + moveB;
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

            Color nc = new Color(r, g, b);
            this.filteredImage.setRGB(x, y, nc.getRGB());
         }
      }
   }

   @Override
   public void applyFilter() throws FilterException {
      this.applyFilter(this.originalImage);
   }

   @Override
   public String getFilterName() {
      return "OldStyleFilter";
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }
}
