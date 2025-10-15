package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class ThresholdFilter extends JDialog implements ImageFilter {
   private BufferedImage originalImage;
   private BufferedImage filteredImage;
   private int currentTreshold = 128;
   private boolean applyChanges = false;
   private JButton buttonApply;
   private JButton buttonCancel;
   private JCheckBox checkBoxAutomatic;
   private JLabel labelValue;
   private JPanel panelImage;
   private JSlider sliderValue;

   public ThresholdFilter(Frame parent, boolean modal) {
      super(parent, modal);
      this.initComponents();
   }

   private void initComponents() {
      this.panelImage = new CustomJPanel();
      this.sliderValue = new JSlider();
      this.checkBoxAutomatic = new JCheckBox();
      this.labelValue = new JLabel();
      this.buttonApply = new JButton();
      this.buttonCancel = new JButton();
      this.setDefaultCloseOperation(2);
      this.setIconImage(null);
      this.setModal(true);
      this.setModalityType(ModalityType.TOOLKIT_MODAL);
      this.sliderValue.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            ThresholdFilter.this.sliderValueStateChanged(evt);
         }
      });
      GroupLayout panelImageLayout = new GroupLayout(this.panelImage);
      this.panelImage.setLayout(panelImageLayout);
      panelImageLayout.setHorizontalGroup(
         panelImageLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING, panelImageLayout.createSequentialGroup().addContainerGap().addComponent(this.sliderValue, -1, 380, 32767).addContainerGap()
            )
      );
      panelImageLayout.setVerticalGroup(
         panelImageLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, panelImageLayout.createSequentialGroup().addGap(0, 271, 32767).addComponent(this.sliderValue, -2, -1, -2))
      );
      this.checkBoxAutomatic.setText("Automatic Treshold Value");
      this.checkBoxAutomatic.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent evt) {
            ThresholdFilter.this.checkBoxAutomaticStateChanged(evt);
         }
      });
      this.labelValue.setText("Threshold Value");
      this.buttonApply.setText("APPLY");
      this.buttonApply.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ThresholdFilter.this.buttonApplyActionPerformed(evt);
         }
      });
      this.buttonCancel.setText("CANCEL");
      this.buttonCancel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            ThresholdFilter.this.buttonCancelActionPerformed(evt);
         }
      });
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addComponent(this.panelImage, -1, -1, 32767)
            .addGroup(
               layout.createSequentialGroup()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(
                           layout.createSequentialGroup()
                              .addGap(10, 10, 10)
                              .addComponent(this.checkBoxAutomatic)
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addComponent(this.labelValue)
                        )
                        .addGroup(
                           Alignment.TRAILING,
                           layout.createSequentialGroup()
                              .addContainerGap(-1, 32767)
                              .addComponent(this.buttonCancel)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonApply)
                        )
                  )
                  .addContainerGap()
            )
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addComponent(this.panelImage, -1, -1, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.checkBoxAutomatic).addComponent(this.labelValue))
                  .addGap(18, 18, 18)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.buttonApply).addComponent(this.buttonCancel))
                  .addGap(8, 8, 8)
            )
      );
      this.pack();
   }

   private void sliderValueStateChanged(ChangeEvent evt) {
      this.currentTreshold = (int)Math.round(this.sliderValue.getValue() * 2.56);

      try {
         this.applyFilter(this.originalImage);
      } catch (FilterException var4) {
         ExceptionDialog ed = new ExceptionDialog(null, true);
         ed.setText(var4.getMessage());
         ed.setVisible(true);
         this.closeAndNotApply();
      }

      this.labelValue.setText(String.valueOf(this.currentTreshold));
      this.checkBoxAutomatic.setSelected(false);
   }

   private void checkBoxAutomaticStateChanged(ChangeEvent evt) {
      if (this.checkBoxAutomatic.isSelected()) {
         this.currentTreshold = this.getAutomaticTreshold();
         this.sliderValue.setValue((int)(this.currentTreshold / 2.56));
      }
   }

   private void buttonApplyActionPerformed(ActionEvent evt) {
      this.closeAndApply();
   }

   private void buttonCancelActionPerformed(ActionEvent evt) {
      this.closeAndNotApply();
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

   private int getAutomaticTreshold() {
      long outInt = 0L;

      for (int x = 0; x < this.originalImage.getWidth(); x++) {
         for (int y = 0; y < this.originalImage.getHeight(); y++) {
            Color c = new Color(this.originalImage.getRGB(x, y));
            outInt = outInt + c.getRed() + c.getGreen() + c.getBlue();
         }
      }

      outInt /= this.originalImage.getHeight() * this.originalImage.getWidth() * 3;
      return (int)outInt;
   }

   private void applyFilter(BufferedImage img) throws FilterException {
      if (img == null) {
         throw new FilterException();
      } else {
         int black = Color.BLACK.getRGB();
         int white = Color.WHITE.getRGB();
         this.filteredImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

         for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
               int rgb = img.getRGB(x, y);
               Color c = new Color(rgb);
               if (c.getRed() <= this.currentTreshold && c.getGreen() <= this.currentTreshold && c.getBlue() <= this.currentTreshold) {
                  this.filteredImage.setRGB(x, y, black);
               } else {
                  this.filteredImage.setRGB(x, y, white);
               }
            }
         }

         ((CustomJPanel)this.panelImage).setImage(this.filteredImage);
         ((CustomJPanel)this.panelImage).repaint();
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
         Logger.getLogger(ThresholdFilter.class.getName()).log(Level.SEVERE, null, var5);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            ThresholdFilter dialog = new ThresholdFilter(new JFrame(), true);
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
      ((CustomJPanel)this.panelImage).setImage(this.originalImage);
      ((CustomJPanel)this.panelImage).repaint();
   }

   @Override
   public BufferedImage getImage() {
      return this.filteredImage == null ? this.originalImage : this.filteredImage;
   }

   @Override
   public void applyFilter() throws FilterException {
      this.applyFilter(this.originalImage);
   }

   @Override
   public String getFilterName() {
      return "treshold";
   }

   @Override
   public String getAuthor() {
      return "Lukas Kalcok";
   }
}
