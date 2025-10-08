package pkg2d3dgraphics;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;

public class AboutDialog extends JDialog {
   private JButton buttonOK;
   private JLabel labelText;

   public AboutDialog(Frame parent, boolean modal) {
      super(parent, modal);
      this.initComponents();
   }

   public void showDialog(Boolean b) {
      this.setVisible(b);
      System.out.println("lol");
   }

   private void initComponents() {
      this.buttonOK = new JButton();
      this.labelText = new JLabel();
      this.setDefaultCloseOperation(2);
      this.buttonOK.setText("OK");
      this.buttonOK.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent evt) {
            AboutDialog.this.buttonOKMouseClicked(evt);
         }
      });
      this.buttonOK.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            AboutDialog.this.buttonOKActionPerformed(evt);
         }
      });
      this.labelText.setHorizontalAlignment(0);
      this.labelText.setText("Text o Programu, Jméno, Příjmení, Verze ...");
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 333, 32767).addComponent(this.buttonOK))
                        .addComponent(this.labelText, -1, -1, 32767)
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
                  .addComponent(this.labelText, -1, 128, 32767)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(this.buttonOK)
                  .addContainerGap()
            )
      );
      this.pack();
   }

   private void buttonOKActionPerformed(ActionEvent evt) {
      this.dispose();
   }

   private void buttonOKMouseClicked(MouseEvent evt) {
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
         Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, var5);
      } catch (InstantiationException var6) {
         Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, var6);
      } catch (IllegalAccessException var7) {
         Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, var7);
      } catch (UnsupportedLookAndFeelException var8) {
         Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, var8);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            AboutDialog dialog = new AboutDialog(new JFrame(), true);
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
