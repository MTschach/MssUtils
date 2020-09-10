package de.mss.utils.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class JCheckBoxList extends JList<JCheckBox> {

   private static final long serialVersionUID = 1L;

   protected Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);


   public JCheckBoxList() {
      setCellRenderer(new JCheckBoxListCellRenderer());
      addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            int index = locationToIndex(e.getPoint());
            if (index != -1) {
               JCheckBox checkbox = getModel().getElementAt(index);
               checkbox.setSelected(!checkbox.isSelected());
               repaint();
            }
         }
      });
      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }


   public JCheckBoxList(ListModel<JCheckBox> model) {
      this();
      setModel(model);
   }


}
