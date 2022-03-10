package com.iup.tp.twitup.ihm;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.iup.tp.twitup.controller.UserController;

public class ButtonEditor extends DefaultCellEditor {
	protected JButton button;

	  private String label;

	  private boolean isPushed;
	  
	  private UserController userController;
	  
	  private String tag;
	  
	  private JTable table;
	  
	  private String abonner;
	  
	  private String desabonner = "Se désabonner";
	  
	  public ButtonEditor(JCheckBox checkBox, UserController userController) {
	    super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    this.userController = userController;
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
	    if (isSelected) {
	      button.setForeground(table.getSelectionForeground());
	      button.setBackground(table.getSelectionBackground());
	    } else {
	      button.setForeground(table.getForeground());
	      button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    button.setText(label);
	    this.tag = table.getValueAt(row, 1).toString();
	    isPushed = true;
	    return button;
	  }

	  public Object getCellEditorValue() {
	    if (isPushed) {
	    	JOptionPane.showMessageDialog(null, this.userController.abonnerDesabonner(this.tag));
	    }
	    isPushed = false;
	    return new String(label);
	  }

	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
	    super.fireEditingStopped();
	  }

}
