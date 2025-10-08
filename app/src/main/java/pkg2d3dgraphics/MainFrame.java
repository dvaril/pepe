package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {
   private JFileChooser fileChooserLoad = new JFileChooser();
   private JFileChooser fileChooserSave;
   private BufferedImage originalImage;
   private BufferedImage img;
   private ImageMatrix matrix;
   private MatrixWindow matrixWindow;
   private FilterController filterController;
   private ExceptionDialog excDialog;
   private JButton buttonEditMatrix;
   private JButton buttonGenerateImage;
   private ButtonGroup buttonGroup;
   private JButton buttonRestoreOriginal;
   private JButton buttonRunFilter;
   private JPanel customPanel;
   private JButton loadButton;
   private JTextArea logTextArea;
   private JPanel matrixJPanel;
   private JMenu menuAbout;
   private JMenuBar menuBar;
   private JMenu menuExit;
   private JMenu menuFile;
   private JMenu menuFilters;
   private JMenuItem menuItemLoadImage;
   private JMenuItem menuItemSaveImage;
   private JRadioButton radioButtonModified;
   private JRadioButton radioButtonOriginal;
   private JScrollPane scrollPaneLog;

   public MainFrame() {
      FileNameExtensionFilter ff = new FileNameExtensionFilter("JPEG", "jpg", "jpeg");
      this.fileChooserLoad.setFileFilter(ff);
      this.fileChooserSave = new JFileChooser();
      FileNameExtensionFilter ff2 = new FileNameExtensionFilter("JPEG", "jpg", "jpeg");
      this.fileChooserSave.setFileFilter(ff2);
      this.matrix = new ImageMatrix();
      this.filterController = new FilterController(this);
      this.initComponents();
      this.loadFilters();
      this.printIntoLog("Application started sucessfully.");
   }

   private void loadFilters() {
      Set filters = this.filterController.getFilters();
      Iterator it = filters.iterator();

      while (it.hasNext()) {
         JMenuItem jm = new JMenuItem((String)it.next());
         jm.setEnabled(false);
         jm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                  MainFrame.this.img = MainFrame.this.filterController.runFilter(((JMenuItem)e.getSource()).getText(), MainFrame.this.img);
                  MainFrame.this.radioButtonModified.setSelected(true);
               } catch (NoFilterException | FilterException var3) {
                  MainFrame.this.excDialog = new ExceptionDialog(null, true);
                  MainFrame.this.excDialog.setText(var3.getMessage());
                  MainFrame.this.excDialog.setVisible(true);
               }

               MainFrame.this.redrawPanel();
            }
         });
         this.menuFilters.add(jm);
      }

      this.printIntoLog("Loaded " + filters.size() + "filters.");
   }

   private void closeApplication() {
      System.exit(0);
   }

   private void redrawPanel() {
      ((CustomJPanel)this.customPanel).setImage(this.img);
      ((CustomJPanel)this.customPanel).repaint();
   }

   private void drawMatrix() {
      ((MatrixJPanel)this.matrixJPanel).setMatrix(this.matrix);
      ((MatrixJPanel)this.matrixJPanel).repaint();
   }

   private void printIntoLog(String text) {
      this.logTextArea.setEditable(false);
      this.logTextArea.append("\n" + text);
   }

   private void loadImage() {
      int returnVal = this.fileChooserLoad.showOpenDialog(this);
      if (returnVal == 0) {
         File file = this.fileChooserLoad.getSelectedFile();
         this.img = null;

         try {
            this.img = ImageIO.read(file);
            this.originalImage = this.img;
            this.printIntoLog("Loaded image:" + file.getName());
            this.redrawPanel();

            for (int a = 0; a < this.menuFilters.getItemCount(); a++) {
               this.menuFilters.getItem(a).setEnabled(true);
            }

            this.buttonRunFilter.setEnabled(true);
            this.menuItemSaveImage.setEnabled(true);
            this.buttonRestoreOriginal.setEnabled(true);
            this.radioButtonOriginal.setSelected(true);
            this.radioButtonModified.setEnabled(true);
            this.radioButtonOriginal.setEnabled(true);
         } catch (IOException var4) {
            this.excDialog = new ExceptionDialog(this, true) {};
            this.excDialog.setText("Error while reading file.");
            this.excDialog.setVisible(true);
         }
      }
   }

   private void saveImage() {
      int returnVal = this.fileChooserSave.showSaveDialog(this);
      if (returnVal == 0) {
         File file = this.fileChooserSave.getSelectedFile();
         int i = file.getName().lastIndexOf(46);
         if (i > 0) {
            String ext = file.getName().substring(i);
            ext = ext.toLowerCase();
            if (ext == null || ext != ".jpg" || ext != ".jpeg") {
               file = new File(file.getAbsoluteFile() + ".jpg");
            }
         } else {
            file = new File(file.getAbsoluteFile() + ".jpg");
         }

         try {
            ImageIO.write(this.img, "jpeg", file);
            this.printIntoLog("Image saved as " + file.getName());
         } catch (IOException var5) {
            this.excDialog = new ExceptionDialog(this, true) {};
            this.excDialog.setText("Error while writing file");
            this.excDialog.setVisible(true);
         }
      }
   }

   private void initComponents() {
      this.buttonGroup = new ButtonGroup();
      this.loadButton = new JButton();
      this.customPanel = new CustomJPanel();
      this.buttonEditMatrix = new JButton();
      this.matrixJPanel = new MatrixJPanel();
      this.buttonRunFilter = new JButton();
      this.scrollPaneLog = new JScrollPane();
      this.logTextArea = new JTextArea();
      this.radioButtonOriginal = new JRadioButton();
      this.radioButtonModified = new JRadioButton();
      this.buttonRestoreOriginal = new JButton();
      this.buttonGenerateImage = new JButton();
      this.menuBar = new JMenuBar();
      this.menuFile = new JMenu();
      this.menuItemLoadImage = new JMenuItem();
      this.menuItemSaveImage = new JMenuItem();
      this.menuFilters = new JMenu();
      this.menuAbout = new JMenu();
      this.menuExit = new JMenu();
      this.setDefaultCloseOperation(3);
      this.loadButton.setText("Select Image File");
      this.loadButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.loadButtonActionPerformed(evt);
         }
      });
      GroupLayout customPanelLayout = new GroupLayout(this.customPanel);
      this.customPanel.setLayout(customPanelLayout);
      customPanelLayout.setHorizontalGroup(customPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 574, 32767));
      customPanelLayout.setVerticalGroup(customPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
      this.buttonEditMatrix.setText("Edit Matrix");
      this.buttonEditMatrix.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.buttonEditMatrixActionPerformed(evt);
         }
      });
      this.matrixJPanel.setBackground(new Color(0, 0, 0));
      this.matrixJPanel.setMinimumSize(new Dimension(90, 90));
      this.matrixJPanel.setPreferredSize(new Dimension(90, 90));
      GroupLayout matrixJPanelLayout = new GroupLayout(this.matrixJPanel);
      this.matrixJPanel.setLayout(matrixJPanelLayout);
      matrixJPanelLayout.setHorizontalGroup(matrixJPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 90, 32767));
      matrixJPanelLayout.setVerticalGroup(matrixJPanelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 90, 32767));
      this.buttonRunFilter.setText("Apply Matrix Filter");
      this.buttonRunFilter.setEnabled(false);
      this.buttonRunFilter.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.buttonRunFilterActionPerformed(evt);
         }
      });
      this.scrollPaneLog.setPreferredSize(new Dimension(105, 105));
      this.logTextArea.setColumns(20);
      this.logTextArea.setLineWrap(true);
      this.logTextArea.setRows(5);
      this.scrollPaneLog.setViewportView(this.logTextArea);
      this.buttonGroup.add(this.radioButtonOriginal);
      this.radioButtonOriginal.setText("Original Image");
      this.radioButtonOriginal.setEnabled(false);
      this.radioButtonOriginal.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.radioButtonOriginalActionPerformed(evt);
         }
      });
      this.buttonGroup.add(this.radioButtonModified);
      this.radioButtonModified.setSelected(true);
      this.radioButtonModified.setText("Modified Image");
      this.radioButtonModified.setEnabled(false);
      this.radioButtonModified.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.radioButtonModifiedActionPerformed(evt);
         }
      });
      this.buttonRestoreOriginal.setText("Restore Original Image");
      this.buttonRestoreOriginal.setEnabled(false);
      this.buttonRestoreOriginal.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.buttonRestoreOriginalActionPerformed(evt);
         }
      });
      this.buttonGenerateImage.setText("Generate Image");
      this.buttonGenerateImage.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.buttonGenerateImageActionPerformed(evt);
         }
      });
      this.menuFile.setText("File");
      this.menuItemLoadImage.setText("Load Image");
      this.menuItemLoadImage.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.menuItemLoadImageActionPerformed(evt);
         }
      });
      this.menuFile.add(this.menuItemLoadImage);
      this.menuItemSaveImage.setText("Save Image");
      this.menuItemSaveImage.setEnabled(false);
      this.menuItemSaveImage.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.menuItemSaveImageActionPerformed(evt);
         }
      });
      this.menuFile.add(this.menuItemSaveImage);
      this.menuBar.add(this.menuFile);
      this.menuFilters.setText("Filters");
      this.menuBar.add(this.menuFilters);
      this.menuAbout.setText("About");
      this.menuAbout.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent evt) {
            MainFrame.this.menuAboutMouseClicked(evt);
         }
      });
      this.menuAbout.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.menuAboutActionPerformed(evt);
         }
      });
      this.menuBar.add(this.menuAbout);
      this.menuExit.setText("Exit");
      this.menuExit.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent evt) {
            MainFrame.this.menuExitMouseClicked(evt);
         }
      });
      this.menuExit.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MainFrame.this.menuExitActionPerformed(evt);
         }
      });
      this.menuBar.add(this.menuExit);
      this.menuExit.getAccessibleContext().setAccessibleName("");
      this.setJMenuBar(this.menuBar);
      this.menuBar.getAccessibleContext().setAccessibleName("");
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(this.customPanel, -1, -1, 32767)
                  .addGap(18, 18, 18)
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(this.loadButton, -1, 147, 32767)
                        .addComponent(this.buttonEditMatrix, -1, -1, 32767)
                        .addComponent(this.buttonRunFilter, -1, -1, 32767)
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.matrixJPanel, -2, -1, -2).addGap(28, 28, 28))
                        .addComponent(this.scrollPaneLog, -1, 147, 32767)
                        .addComponent(this.radioButtonOriginal)
                        .addComponent(this.radioButtonModified)
                        .addComponent(this.buttonRestoreOriginal, -1, -1, 32767)
                        .addComponent(this.buttonGenerateImage, -1, -1, 32767)
                  )
                  .addContainerGap()
            )
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.customPanel, -1, -1, 32767)
                        .addGroup(
                           layout.createSequentialGroup()
                              .addComponent(this.loadButton)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonEditMatrix)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.matrixJPanel, -2, -1, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonRunFilter)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.scrollPaneLog, -1, 153, 32767)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonGenerateImage, -2, 45, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.buttonRestoreOriginal, -2, 49, -2)
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.radioButtonOriginal)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.radioButtonModified)
                        )
                  )
                  .addContainerGap()
            )
      );
      this.loadButton.getAccessibleContext().setAccessibleName("");
      this.pack();
   }

   private void menuExitMouseClicked(MouseEvent evt) {
      this.closeApplication();
   }

   private void loadButtonActionPerformed(ActionEvent evt) {
      this.loadImage();
   }

   private void menuExitActionPerformed(ActionEvent evt) {
      this.dispose();
   }

   private void buttonEditMatrixActionPerformed(ActionEvent evt) {
      if (this.matrixWindow == null) {
         this.matrixWindow = new MatrixWindow(this, true, this.matrix);
      }

      this.matrixWindow.setMatrix(this.matrix);
      this.matrix = this.matrixWindow.showDialog(true);
      this.drawMatrix();
      this.printIntoLog("New matrix is set.");
   }

   private void buttonRunFilterActionPerformed(ActionEvent evt) {
      MatrixFilter imgFilter = new MatrixFilter();
      imgFilter.setMatrix(this.matrix);
      imgFilter.setImage(this.img);

      try {
         imgFilter.applyFilter();
      } catch (FilterException var4) {
         this.excDialog = new ExceptionDialog(this, true);
         this.excDialog.setVisible(true);
      }

      this.img = imgFilter.getImage();
      this.redrawPanel();
      this.printIntoLog("Convolution filter applied at image.");
      this.radioButtonModified.setSelected(true);
   }

   private void menuItemLoadImageActionPerformed(ActionEvent evt) {
      this.loadImage();
   }

   private void menuItemSaveImageActionPerformed(ActionEvent evt) {
      this.saveImage();
   }

   private void buttonRestoreOriginalActionPerformed(ActionEvent evt) {
      this.img = this.originalImage;
      this.radioButtonOriginal.setSelected(true);
      this.redrawPanel();
      this.printIntoLog("Original image restored.");
   }

   private void radioButtonOriginalActionPerformed(ActionEvent evt) {
      ((CustomJPanel)this.customPanel).setImage(this.originalImage);
      ((CustomJPanel)this.customPanel).repaint();
   }

   private void radioButtonModifiedActionPerformed(ActionEvent evt) {
      this.redrawPanel();
   }

   private void menuAboutActionPerformed(ActionEvent evt) {
      AboutDialog ab = new AboutDialog(this, true);
      ab.showDialog(true);
   }

   private void buttonGenerateImageActionPerformed(ActionEvent evt) {
      this.generateImage();
   }

   private void menuAboutMouseClicked(MouseEvent evt) {
      AboutDialog ab = new AboutDialog(this, true);
      ab.showDialog(true);
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
         Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, var5);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            new MainFrame().setVisible(true);
         }
      });
   }

   private void generateImage() {
      this.img = this.makeColoredImage();
      this.originalImage = this.img;
      this.printIntoLog("New image generated.");
      this.redrawPanel();

      for (int a = 0; a < this.menuFilters.getItemCount(); a++) {
         this.menuFilters.getItem(a).setEnabled(true);
      }

      this.buttonRunFilter.setEnabled(true);
      this.menuItemSaveImage.setEnabled(true);
      this.buttonRestoreOriginal.setEnabled(true);
      this.radioButtonOriginal.setSelected(true);
      this.radioButtonModified.setEnabled(true);
      this.radioButtonOriginal.setEnabled(true);
   }

   public BufferedImage makeColoredImage() {
      BufferedImage bImage = new BufferedImage(600, 600, 5);

      for (int x = 0; x < bImage.getWidth(); x++) {
         for (int y = 0; y < bImage.getHeight(); y++) {
            bImage.setRGB(x, y, new Color(x % 255, y % 255, (x + y) % 255).getRGB());
         }
      }

      return bImage;
   }
}
