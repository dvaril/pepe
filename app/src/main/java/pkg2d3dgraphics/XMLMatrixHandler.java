package pkg2d3dgraphics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLMatrixHandler {
   private ImageMatrix matrix;

   public void setMatrix(ImageMatrix m) {
      this.matrix = m.getMatrixCopy();
   }

   public ImageMatrix loadMatrix(File f) throws ParserConfigurationException, SAXException, IOException {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(f);
      doc.getDocumentElement().normalize();
      NodeList dimensionXNode = doc.getElementsByTagName("dimensionX");
      int dimX = new Integer(dimensionXNode.item(0).getTextContent());
      NodeList dimensionYNode = doc.getElementsByTagName("dimensionY");
      int dimY = new Integer(dimensionYNode.item(0).getTextContent());
      NodeList coefficientNode = doc.getElementsByTagName("coefficient");
      Float coefficient = new Float(coefficientNode.item(0).getTextContent());
      NodeList normalisedNode = doc.getElementsByTagName("normalised");
      String norm = normalisedNode.item(0).getTextContent();
      boolean nor = false;
      if (norm.equals("true")) {
         nor = true;
      }

      this.matrix = new ImageMatrix(dimX, dimY, nor);
      this.matrix.setCoefficient(coefficient);
      this.matrix.setNormalization(nor);
      NodeList cols = doc.getElementsByTagName("column");
      if (cols.getLength() > dimX) {
         throw new IOException("\"Bad Input format of XML file, Too much data. / X");
      } else {
         for (int x = 0; x < dimX; x++) {
            Node node = cols.item(x);
            Node cell = node.getFirstChild();

            int y;
            for (y = 0; cell != null; cell = cell.getNextSibling()) {
               if (cell.getNodeName().equals("cell")) {
                  if (y >= dimY) {
                     throw new IOException("Bad Input format of XML file, Too much data.");
                  }

                  this.matrix.set(x, y, new Integer(cell.getTextContent()));
                  y++;
               }
            }

            if (y != dimY) {
               throw new IOException("Bad Input format of XML file, Not enough data.");
            }
         }

         return this.matrix;
      }
   }

   public void saveMatrix(File f) throws TransformerConfigurationException, TransformerException, IOException {
      if (this.matrix == null) {
         throw new IIOException("Matrix is not set.");
      } else {
         if (!f.getName().endsWith(".xml")) {
            f = new File(f.getPath() + ".xml");
         }

         f.createNewFile();

         try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n");
            writer.write("<matrix>\n");
            writer.write("  <dimensionX>" + this.matrix.length(0) + "</dimensionX>\n");
            writer.write("  <dimensionY>" + this.matrix.length(1) + "</dimensionY>\n");
            writer.write("  <normalised>" + this.matrix.normalised() + "</normalised>\n");
            writer.write("  <coefficient>" + this.matrix.getCoefficient() + "</coefficient>\n");

            for (int x = 0; x < this.matrix.length(0); x++) {
               writer.write("  <column>\n");

               for (int y = 0; y < this.matrix.length(1); y++) {
                  writer.write("      <cell>" + Integer.toString(this.matrix.get(x, y)) + "</cell>\n");
               }

               writer.write("  </column>\n");
            }

            writer.write("</matrix>\n");
         }
      }
   }
}
