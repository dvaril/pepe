package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

public class VienetteFilter extends JDialog implements ImageFilter {
   BufferedImage originalImage;
   BufferedImage filteredImage;
   Boolean applyChanges;
   int percentageToApply;
   int percentOfDistanceToIgnore;
   private JButton cancelButton;
   private JLabel distanceStartLabel;
   private JSlider distanceStartSlider;
   private JLabel jLabel1;
   private JLabel jLabel2;
   private JLabel labelPercentage;
   private JButton okButton;
   private JSlider percentageSlider;
   private JPanel showImagePanel;

   public VienetteFilter(Frame parent, boolean modal) {
      super(parent, modal);
      this.initComponents();
      this.applyChanges = false;
      this.percentageToApply = 50;
      this.percentOfDistanceToIgnore = 50;
   }

   private void initComponents() {
      this.showImagePanel = new CustomJPanel();
      this.okButton = new JButton();
      this.cancelButton = new JButton();
      this.percentageSlider = new JSlider();
      this.jLabel1 = new JLabel();
      this.labelPercentage = new JLabel();
      this.distanceStartSlider = new JSlider();
      this.jLabel2 = new JLabel();
      this.distanceStartLabel = new JLabel();
      this.setDefaultCloseOperation(2);
      this.showImagePanel.setMinimumSize(new Dimension(0, 0));
      this.showImagePanel.setPreferredSize(new Dimension(0, 0));
      GroupLayout showImagePanelLayout = new GroupLayout(this.showImagePanel);
      this.showImagePanel.setLayout(showImagePanelLayout);
      showImagePanelLayout.setHorizontalGroup(showImagePanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
      showImagePanelLayout.setVerticalGroup(showImagePanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 362, 32767));
      this.okButton.setText("OK");
      this.okButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            VienetteFilter.this.okButtonActionPerformed(evt);
         }
      });
      this.cancelButton.setText("Cancel");
      this.cancelButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            VienetteFilter.this.cancelButtonActionPerformed(evt);
         }
      });
      this.percentageSlider.setMinorTickSpacing(1);
      this.percentageSlider.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            VienetteFilter.this.percentageSliderStateChanged(evt);
         }
      });
      this.percentageSlider.addPropertyChangeListener(new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            VienetteFilter.this.percentageSliderPropertyChange(evt);
         }
      });
      this.jLabel1.setText("Amount of Vinette:");
      this.labelPercentage.setText("50%");
      this.distanceStartSlider.setMinorTickSpacing(1);
      this.distanceStartSlider.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            VienetteFilter.this.distanceStartSliderStateChanged(evt);
         }
      });
      this.jLabel2.setText("Start at distance:");
      this.distanceStartLabel.setText("50 %");
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
                           Alignment.TRAILING,
                           layout.createSequentialGroup()
                              .addGap(0, 0, 32767)
                              .addComponent(this.cancelButton)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.okButton)
                        )
                        .addComponent(this.percentageSlider, Alignment.TRAILING, -1, 520, 32767)
                        .addComponent(this.showImagePanel, -1, 520, 32767)
                        .addComponent(this.distanceStartSlider, -1, -1, 32767)
                        .addGroup(
                           layout.createSequentialGroup()
                              .addGroup(
                                 layout.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       layout.createSequentialGroup()
                                          .addComponent(this.jLabel1)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.labelPercentage)
                                    )
                                    .addGroup(
                                       layout.createSequentialGroup()
                                          .addComponent(this.jLabel2)
                                          .addPreferredGap(ComponentPlacement.UNRELATED)
                                          .addComponent(this.distanceStartLabel)
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
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.showImagePanel, -1, 362, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED, 66, 32767)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.distanceStartLabel))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.distanceStartSlider, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.labelPercentage))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.percentageSlider, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.okButton).addComponent(this.cancelButton))
                  .addContainerGap()
            )
      );
      this.pack();
   }

   private void percentageSliderPropertyChange(PropertyChangeEvent evt) {
      this.refreshGUI();
   }

   private void okButtonActionPerformed(ActionEvent evt) {
      this.exitAndSave();
   }

   private void cancelButtonActionPerformed(ActionEvent evt) {
      this.exitAndDontSave();
   }

   private void percentageSliderStateChanged(ChangeEvent evt) {
      this.refreshGUI();
   }

   private void distanceStartSliderStateChanged(ChangeEvent evt) {
      this.refreshGUI();
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
         Logger.getLogger(VienetteFilter.class.getName()).log(Level.SEVERE, null, var5);
      } catch (InstantiationException var6) {
         Logger.getLogger(VienetteFilter.class.getName()).log(Level.SEVERE, null, var6);
      } catch (IllegalAccessException var7) {
         Logger.getLogger(VienetteFilter.class.getName()).log(Level.SEVERE, null, var7);
      } catch (UnsupportedLookAndFeelException var8) {
         Logger.getLogger(VienetteFilter.class.getName()).log(Level.SEVERE, null, var8);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            VienetteFilter dialog = new VienetteFilter(new JFrame(), true);
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
      this.refreshGUI();
   }

   private void refreshGUI() {
      this.percentageToApply = this.percentageSlider.getValue();
      this.labelPercentage.setText(this.percentageToApply + " %");
      this.percentOfDistanceToIgnore = this.distanceStartSlider.getValue();
      this.distanceStartLabel.setText(this.percentOfDistanceToIgnore + "%");

      try {
         if (this.originalImage != null) {
            this.applyFilter(this.originalImage);
         }
      } catch (FilterException var3) {
         ExceptionDialog e = new ExceptionDialog(null, true);
         e.setText("System error:" + var3.getMessage());
         e.setVisible(true);
         this.exitAndDontSave();
      }

      ((CustomJPanel)this.showImagePanel).setImage(this.filteredImage);
      ((CustomJPanel)this.showImagePanel).repaint();
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage;
   }

   @Override
   public void applyFilter() throws FilterException {
      this.applyFilter(this.originalImage);
   }

   @Override
   public String getFilterName() {
      return "Vinette";
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }

   private void exitAndSave() {
      this.applyChanges = true;
      this.dispose();
   }

   private void exitAndDontSave() {
      this.applyChanges = false;
      this.dispose();
   }

   @Override
   public void dispose() {
      if (!this.applyChanges) {
         this.filteredImage = this.originalImage;
      }

      this.setVisible(false);
   }

   private void applyFilter(BufferedImage img) throws FilterException {
      if (img == null) {
         throw new FilterException();
      } else {
         this.filteredImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
         int centerX = img.getWidth() / 2;
         int centerY = img.getHeight() / 2;
         double maxDistance = Math.sqrt(centerX * centerX + centerY * centerY);
         double ignoreDistance = maxDistance * (this.percentOfDistanceToIgnore / 100.0F);

         for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
               int distX = Math.abs(centerX - x);
               int distY = Math.abs(centerY - y);
               double distXY = Math.max(Math.sqrt(distX * distX + distY * distY) - ignoreDistance, 0.001);
               double distPercent = distXY / maxDistance * (this.percentageToApply / 100.0F);
               Color c = new Color(img.getRGB(x, y));
               int r = (int)(c.getRed() * (1.0 - distPercent));
               int g = (int)(c.getGreen() * (1.0 - distPercent));
               int b = (int)(c.getBlue() * (1.0 - distPercent));
               Color nc = new Color(r, g, b);
               this.filteredImage.setRGB(x, y, nc.getRGB());
            }
         }
      }
   }
}
