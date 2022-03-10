package com.iup.tp.twitup.ihm;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.iup.tp.twitup.controller.TwitController;
import com.iup.tp.twitup.controller.UserController;
import com.iup.tp.twitup.datamodel.User;

public class TwitView extends JMenu {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame mFrame;
	
	private JPanel jPanel;
	
	UserController userController;
	
	TwitController twitController;
	
	private List<Component> menuConnecte;
	
	private List<Component> menuDeconnecte;

	public TwitView(JFrame mFrame, List<Component> menuConnecte, List<Component> menuDeconnecte, JPanel jPanel,
			UserController userController, TwitController twitController) {
		super("Twit");
		this.jPanel = jPanel;
		this.mFrame = mFrame;
		this.userController = userController;
		this.menuConnecte = menuConnecte;
		this.menuDeconnecte = menuDeconnecte;
		this.twitController = twitController;
		Container mContent = mFrame.getContentPane();
        
		JMenuItem jMenuItemEcrireTwit = new JMenuItem("Écrire un twit");
		JMenuItem jMenuItemListerTwits = new JMenuItem("Rechercher des twits");
        ActionListener formSendTwit = this.getFormSendTwit(mContent);
        ActionListener formListTwit = this.getFormListdTwit(mContent);
        jMenuItemEcrireTwit.addActionListener(formSendTwit);
        jMenuItemListerTwits.addActionListener(formListTwit);
		menuConnecte.add(this);
		this.setVisible(userController.getUserConnecte()!=null);
		this.add(jMenuItemEcrireTwit);
		this.add(jMenuItemListerTwits);
		
	}

	private ActionListener getFormListdTwit(Container mContent) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mContent.remove(jPanel);
				jPanel.removeAll();
				SwingUtilities.updateComponentTreeUI(mFrame);
				
				String[] col = {""};
				Object[][] data = TwitView.this.twitController.listTwits();
				DefaultTableModel model = new DefaultTableModel(data,col);
		        JTable table = new JTable(model){
		            private static final long serialVersionUID = 1L;

		            public boolean isCellEditable(int row, int column) {                
		                    return false;               
		            };
		        };
		        table.setIntercellSpacing(new Dimension(50,20));
		        table.setRowHeight(140);
		        table.setTableHeader(null);
		        table.setCellSelectionEnabled(false);
		        table.setFocusable(false);
		        JScrollPane scrollPane = new JScrollPane(table);
		        scrollPane.setPreferredSize(new Dimension(500,500));
		        
		        TableRowSorter<TableModel> sort = new TableRowSorter<>(table.getModel());
		        table.setRowSorter(sort);
		        JTextField recherche = new JTextField();
		        recherche.getDocument().addDocumentListener(new DocumentListener()
				{
		            @Override
		            public void insertUpdate(DocumentEvent e) {
		                String str = recherche.getText();
		                if (str.trim().length() == 0) {
		                    sort.setRowFilter(null);
		                } else {
		                    //(?i) recherche insensible à la casse
		                	if(str.contains("@") || str.contains("#")) {
		                		sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
		                	}else {
		                		sort.setRowFilter(RowFilter.regexFilter("(?i)[#,@]+" + str));
		                	}
		                }
		            }
		            @Override
		            public void removeUpdate(DocumentEvent e) {
		                String str = recherche.getText();
		                if (str.trim().length() == 0) {
		                    sort.setRowFilter(null);
		                } else {
		                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
		                }
		            }
		            @Override
		            public void changedUpdate(DocumentEvent e) {}
		        });
		        
		        jPanel.add(recherche, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
				jPanel.add(scrollPane, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(TwitupMainView.getButtonReturn(mContent, mFrame, jPanel), new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
				mContent.add(jPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
				SwingUtilities.updateComponentTreeUI(mFrame);
			}

		};
	}

	private ActionListener getFormSendTwit(Container mContent) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mContent.remove(jPanel);
				jPanel.removeAll();
				SwingUtilities.updateComponentTreeUI(mFrame);
				JTextArea saisieTwit = new JTextArea(20, 50);
				saisieTwit.setLineWrap(true);
				saisieTwit.setWrapStyleWord(true);
				LimitField limite = new LimitField(250);
				saisieTwit.setDocument(limite);
				JScrollPane scrollPane = new JScrollPane(saisieTwit);
        		jPanel.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(getButtonAddTwit(saisieTwit, userController.getUserConnecte()), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(TwitupMainView.getButtonReturn(mContent, mFrame, jPanel), new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
				mContent.add(jPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
				SwingUtilities.updateComponentTreeUI(mFrame);
			}
			private Component getButtonAddTwit(JTextArea textTwit, User user) {
				JButton button = new JButton("Ajouter twit");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(textTwit.getText().equals(null)||textTwit.getText().equals("")) {
							JOptionPane.showMessageDialog(mFrame, "Veuillez saisir un twit", null, 0);
						}
						else {
							System.out.println(user.getName());
							System.out.println(textTwit.getText());
							twitController.addTwit(user, textTwit.getText());
							textTwit.setText("");
							JOptionPane.showMessageDialog(mFrame, "Le twit est envoyé", null, 0);
						}
					}
				});
				return button;
			}
		};
	}
}
