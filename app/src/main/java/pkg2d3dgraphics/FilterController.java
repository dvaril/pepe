package pkg2d3dgraphics;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JDialog;

public class FilterController {
   private Map<String, ImageFilter> filters = new HashMap<>();

   public FilterController(Frame parent) {
      NegativeFilter nf = new NegativeFilter();
      this.filters.put(nf.getFilterName(), nf);
      IdentityFilter iff = new IdentityFilter();
      this.filters.put(iff.getFilterName(), iff);
      ThresholdFilter tW = new ThresholdFilter(parent, true);
      this.filters.put(tW.getFilterName(), tW);
      ColorFilter cF = new ColorFilter(parent, true);
      this.filters.put(cF.getFilterName(), cF);
      BWfilter bF = new BWfilter();
      this.filters.put(bF.getFilterName(), bF);
      PixelizerFilter pF = new PixelizerFilter(parent, true);
      this.filters.put(pF.getFilterName(), pF);
      VienetteFilter vf = new VienetteFilter(parent, true);
      this.filters.put(vf.getFilterName(), vf);
      OldFilmFilter off = new OldFilmFilter(parent, true);
      this.filters.put(off.getFilterName(), off);
   }

   public Set getFilters() {
      return this.filters.keySet();
   }

   public BufferedImage runFilter(String name, BufferedImage image) throws NoFilterException, FilterException {
      ImageFilter filter = this.filters.get(name);
      if (filter != null) {
         filter.setImage(image);
         if (filter instanceof JDialog) {
            ((JDialog)filter).setModal(true);
            ((JDialog)filter).setVisible(true);
         } else {
            filter.applyFilter();
         }

         return filter.getImage();
      } else {
         throw new NoFilterException(name);
      }
   }
}
