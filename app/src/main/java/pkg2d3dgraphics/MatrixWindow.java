package pkg2d3dgraphics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class MatrixWindow extends JDialog {
   private JFileChooser fileChooserLoad;
   private JFileChooser fileChooserSave;
   private ImageMatrix originalMatrix;
   private ImageMatrix localMatrix;
   private JSpinner[][] spinnerFields;
   private XMLMatrixHandler xmlMatrixHandler;
   private boolean saveMarix = false;
   private JButton buttonCancel;
   private JButton buttonClearMatrix;
   private JButton buttonLoadMatrixFromXML;
   private JButton buttonOK;
   private JButton buttonSaveMatrixFromXML;
   private JCheckBox checkboxNormalisation;
   private JSeparator jSeparator1;
   private JSeparator jSeparator3;
   private JLabel labelCoefficientText;
   private JLabel labelMatrixSumText;
   private JLabel labelNormalisation;
   private JLabel labelOverall;
   private Panel panel;

   public MatrixWindow(Frame parent, boolean modal, ImageMatrix m) {
      super(parent, modal);
      this.originalMatrix = m.getMatrixCopy();
      this.localMatrix = m.getMatrixCopy();
      this.spinnerFields = new JSpinner[this.localMatrix.length(0)][this.localMatrix.length(1)];
      this.initComponents();
      this.createFieldComponents();
      this.distributeMatrix();
      this.fileChooserLoad = new JFileChooser();
      this.fileChooserSave = new JFileChooser();
   }

   public void setMatrix(ImageMatrix m) {
      this.originalMatrix = m.getMatrixCopy();
      this.localMatrix = m.getMatrixCopy();
   }

   private void distributeMatrix() {
      ImageMatrix mal = this.localMatrix.getMatrixCopy();
      this.labelNormalisation.setText(mal.getCoefficient() + " ");
      this.checkboxNormalisation.setSelected(mal.normalised());

      for (int x = 0; x < this.localMatrix.length(0); x++) {
         for (int y = 0; y < this.localMatrix.length(1); y++) {
            this.spinnerFields[x][y].setValue(mal.get(x, y));
         }
      }
   }

   public ImageMatrix showDialog(Boolean b) {
      this.setVisible(b);
      return this.localMatrix;
   }

   private float getCoefficient() {
      return 1.0F / this.getSum();
   }

   private int getSum() {
      Integer sum = 0;

      for (int x = 0; x < this.localMatrix.length(0); x++) {
         for (int y = 0; y < this.localMatrix.length(1); y++) {
            Integer val = (Integer)this.spinnerFields[x][y].getValue();
            sum = sum + val;
         }
      }

      return sum;
   }

   private void computeMatrixAndUpdateGUI() {
      if (this.localMatrix.length(0) != this.spinnerFields.length || this.localMatrix.length(1) != this.spinnerFields[0].length) {
         this.clearFieldComponents();
         this.createFieldComponents();
      }

      for (int x = 0; x < this.localMatrix.length(0); x++) {
         for (int y = 0; y < this.localMatrix.length(1); y++) {
            this.localMatrix.set(x, y, (Integer)this.spinnerFields[x][y].getValue());
         }
      }

      this.labelOverall.setText(" " + Integer.valueOf(this.getSum()).toString());
      this.localMatrix.setNormalization(this.checkboxNormalisation.isSelected());
      if (this.localMatrix.normalised()) {
         this.localMatrix.setCoefficient(this.getCoefficient());
         this.labelNormalisation.setText(String.valueOf(this.localMatrix.getCoefficient()));
      } else {
         this.labelNormalisation.setText("Not Normalized");
      }

      this.localMatrix.setCoefficient(this.getCoefficient());
   }

   private void clearFieldComponents() {
      this.panel.removeAll();
      this.panel.validate();
      this.panel.repaint();
   }

   private void createFieldComponents() {
      Border border = BorderFactory.createMatteBorder(1, 5, 1, 1, Color.gray);
      this.spinnerFields = new JSpinner[this.localMatrix.length(0)][this.localMatrix.length(1)];

      for (int x = 0; x < this.localMatrix.length(0); x++) {
         for (int y = 0; y < this.localMatrix.length(1); y++) {
            this.spinnerFields[x][y] = new JSpinner();
            this.spinnerFields[x][y].setSize(50, 30);
            this.spinnerFields[x][y].setBorder(border);
            this.spinnerFields[x][y].setLocation(60 * x, y * 40);
            this.spinnerFields[x][y].setVisible(true);
            this.spinnerFields[x][y].addChangeListener(new ChangeListener() {
               @Override
               public void stateChanged(ChangeEvent e) {
                  MatrixWindow.this.computeMatrixAndUpdateGUI();
               }
            });
            this.panel.add(this.spinnerFields[x][y]);
         }
      }

      this.panel.validate();
      this.panel.repaint();
   }

   private void saveMatrix() {
      if (this.xmlMatrixHandler == null) {
         this.xmlMatrixHandler = new XMLMatrixHandler();
      }

      this.xmlMatrixHandler.setMatrix(this.localMatrix);
      FileNameExtensionFilter ff = new FileNameExtensionFilter("XML", "xml", "xml");
      this.fileChooserSave.setFileFilter(ff);
      int returnVal = this.fileChooserSave.showOpenDialog(this);
      if (returnVal == 0) {
         File file = this.fileChooserSave.getSelectedFile();

         try {
            this.xmlMatrixHandler.saveMatrix(file);
         } catch (TransformerConfigurationException var6) {
            ExceptionDialog exd = new ExceptionDialog(null, true);
            exd.setText(var6.getMessage());
            exd.setVisible(true);
         } catch (IOException | TransformerException var7) {
            ExceptionDialog exdx = new ExceptionDialog(null, true);
            exdx.setText(var7.getMessage());
            exdx.setVisible(true);
         }
      }
   }

   private void loadMatrix() {
      if (this.xmlMatrixHandler == null) {
         this.xmlMatrixHandler = new XMLMatrixHandler();
      }

      FileNameExtensionFilter ff = new FileNameExtensionFilter("XML", "xml", "xml");
      this.fileChooserLoad.setFileFilter(ff);
      int returnVal = this.fileChooserLoad.showOpenDialog(this);
      if (returnVal == 0) {
         File file = this.fileChooserLoad.getSelectedFile();

         try {
            this.localMatrix = this.xmlMatrixHandler.loadMatrix(file).getMatrixCopy();
            this.clearFieldComponents();
            this.createFieldComponents();
            this.distributeMatrix();
         } catch (SAXException | IOException | ParserConfigurationException var6) {
            ExceptionDialog exd = new ExceptionDialog(null, true);
            exd.setText(var6.getMessage());
            exd.setVisible(true);
         }
      }
   }

   private void initComponents() {
      this.buttonOK = new JButton();
      this.buttonCancel = new JButton();
      this.labelOverall = new JLabel();
      this.panel = new Panel();
      this.checkboxNormalisation = new JCheckBox();
      this.labelNormalisation = new JLabel();
      this.buttonSaveMatrixFromXML = new JButton();
      this.buttonLoadMatrixFromXML = new JButton();
      this.jSeparator1 = new JSeparator();
      this.jSeparator3 = new JSeparator();
      this.buttonClearMatrix = new JButton();
      this.labelCoefficientText = new JLabel();
      this.labelMatrixSumText = new JLabel();
      this.setDefaultCloseOperation(2);
      this.buttonOK.setText("OK");
      this.buttonOK.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MatrixWindow.this.buttonOKActionPerformed(evt);
         }
      });
      this.buttonCancel.setText("Cancel");
      this.buttonCancel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MatrixWindow.this.buttonCancelActionPerformed(evt);
         }
      });
      this.labelOverall.setText("0");
      GroupLayout panelLayout = new GroupLayout(this.panel);
      this.panel.setLayout(panelLayout);
      panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
      panelLayout.setVerticalGroup(panelLayout.createParallelGroup(Alignment.LEADING).addGap(0, 380, 32767));
      this.checkboxNormalisation.setText("Normalisation");
      this.checkboxNormalisation.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MatrixWindow.this.checkboxNormalisationActionPerformed(evt);
         }
      });
      this.labelNormalisation.setText("1");
      this.buttonSaveMatrixFromXML.setText("Save Matrix");
      this.buttonSaveMatrixFromXML.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MatrixWindow.this.buttonSaveMatrixFromXMLActionPerformed(evt);
         }
      });
      this.buttonLoadMatrixFromXML.setText("Load Matrix");
      this.buttonLoadMatrixFromXML.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MatrixWindow.this.buttonLoadMatrixFromXMLActionPerformed(evt);
         }
      });
      this.buttonClearMatrix.setText("Clear Matrix");
      this.buttonClearMatrix.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            MatrixWindow.this.buttonClearMatrixActionPerformed(evt);
         }
      });
      this.labelCoefficientText.setText("/  Coefficient:");
      this.labelMatrixSumText.setText("Matrix Sum:");
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.jSeparator1)
                        .addComponent(this.panel, Alignment.TRAILING, -1, -1, 32767)
                        .addComponent(this.jSeparator3)
                        .addGroup(
                           layout.createSequentialGroup()
                              .addComponent(this.checkboxNormalisation)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.labelCoefficientText)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.labelNormalisation, -2, 120, -2)
                              .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                              .addComponent(this.labelMatrixSumText)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.labelOverall)
                        )
                        .addGroup(
                           layout.createSequentialGroup()
                              .addGroup(
                                 layout.createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                       layout.createSequentialGroup()
                                          .addComponent(this.buttonOK)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.buttonCancel)
                                    )
                                    .addGroup(
                                       layout.createSequentialGroup()
                                          .addComponent(this.buttonSaveMatrixFromXML)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.buttonLoadMatrixFromXML)
                                          .addPreferredGap(ComponentPlacement.RELATED)
                                          .addComponent(this.buttonClearMatrix)
                                    )
                              )
                              .addGap(0, 249, 32767)
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
                  .addComponent(this.panel, -2, -1, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.checkboxNormalisation)
                        .addComponent(this.labelNormalisation)
                        .addComponent(this.labelOverall)
                        .addComponent(this.labelCoefficientText)
                        .addComponent(this.labelMatrixSumText)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.jSeparator1, -2, 10, -2)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(this.buttonLoadMatrixFromXML)
                        .addComponent(this.buttonSaveMatrixFromXML)
                        .addComponent(this.buttonClearMatrix)
                  )
                  .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                  .addComponent(this.jSeparator3, -2, 10, -2)
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.buttonOK).addComponent(this.buttonCancel))
                  .addContainerGap()
            )
      );
      this.pack();
   }

   private void buttonOKActionPerformed(ActionEvent evt) {
      this.saveMarix = true;
      this.dispose();
   }

   private void buttonCancelActionPerformed(ActionEvent evt) {
      this.saveMarix = false;
      this.dispose();
   }

   private void checkboxNormalisationActionPerformed(ActionEvent evt) {
      this.computeMatrixAndUpdateGUI();
   }

   private void buttonSaveMatrixFromXMLActionPerformed(ActionEvent evt) {
      this.saveMatrix();
   }

   private void buttonLoadMatrixFromXMLActionPerformed(ActionEvent evt) {
      this.loadMatrix();
   }

   private void buttonClearMatrixActionPerformed(ActionEvent evt) {
      this.localMatrix.clearMatrix();
      this.clearFieldComponents();
      this.createFieldComponents();
      this.distributeMatrix();
   }

   @Override
   public void dispose() {
      if (!this.saveMarix) {
         this.localMatrix = this.originalMatrix;
      }

      super.dispose();
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
         Logger.getLogger(MatrixWindow.class.getName()).log(Level.SEVERE, null, var5);
      } catch (InstantiationException var6) {
         Logger.getLogger(MatrixWindow.class.getName()).log(Level.SEVERE, null, var6);
      } catch (IllegalAccessException var7) {
         Logger.getLogger(MatrixWindow.class.getName()).log(Level.SEVERE, null, var7);
      } catch (UnsupportedLookAndFeelException var8) {
         Logger.getLogger(MatrixWindow.class.getName()).log(Level.SEVERE, null, var8);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            MatrixWindow dialog = new MatrixWindow(new JFrame(), true, new ImageMatrix());
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
}
