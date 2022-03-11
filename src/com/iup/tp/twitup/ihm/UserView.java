package com.iup.tp.twitup.ihm;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.iup.tp.twitup.controller.UserController;
import com.iup.tp.twitup.datamodel.User;

public class UserView extends JMenu {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame mFrame;
	
	private JPanel jPanel;
	
	private UserController userController;
	
	private List<Component> menuConnecte;
	
	private List<Component> menuDeconnecte;
	
	public UserView(JFrame mFrame, List<Component> menuConnecte, List<Component> menuDeconnecte, JPanel jPanel, UserController userController) {
		super("Utilisateur");
		this.jPanel = jPanel;
		this.mFrame = mFrame;
		this.userController = userController;
		this.menuConnecte = menuConnecte;
		this.menuDeconnecte = menuDeconnecte;
		Container mContent = mFrame.getContentPane();
        JTextField unInput = new JTextField();
        JTextField unInput2 = new JTextField();
		JLabel label4 = new JLabel ("Le chemin...");
        JButton unInput3 = this.getButtonParcourir(label4);
        JTextField unInputConnexion = new JTextField();
        JPasswordField unInputConnexion2 = new JPasswordField();
        
        ActionListener formConnectListener = this.getFormConnectListener(mContent, unInputConnexion, unInputConnexion2);
        ActionListener deconnectListener = this.getDeconnectListener(mContent);
        ActionListener formCreateListener = this.getFormCreateListener(mContent, unInput, unInput2, unInput3, label4);
        ActionListener formListUser = this.getFormListUser(mContent);
        ActionListener formConsultListener = this.getFormConsultListener(mContent);
        
		JMenuItem jMenuItemCreerUtilisateur = new JMenuItem("Creer un utilisateur");
		JMenuItem jMenuItemListerUtilisateur = new JMenuItem("Liste des utilisateurs");
		JMenuItem jMenuItemConnexionUtilisateur = new JMenuItem("Se connecter");
		JMenuItem jMenuItemDeconnexionUtilisateur = new JMenuItem("Se deconnecter");
		JMenuItem jMenuItemProfil = new JMenuItem("Profil");
		menuDeconnecte.add(jMenuItemConnexionUtilisateur);
		menuConnecte.add(jMenuItemProfil);
		menuConnecte.add(jMenuItemListerUtilisateur);
		menuConnecte.add(jMenuItemDeconnexionUtilisateur);
		ConnexionUtil.setConnection(false, menuConnecte, menuDeconnecte);
		jMenuItemCreerUtilisateur.addActionListener(formCreateListener);
		jMenuItemListerUtilisateur.addActionListener(formListUser);
		jMenuItemConnexionUtilisateur.addActionListener(formConnectListener);
		jMenuItemDeconnexionUtilisateur.addActionListener(deconnectListener);
		jMenuItemProfil.addActionListener(formConsultListener);
		this.add(jMenuItemCreerUtilisateur);
		this.add(jMenuItemListerUtilisateur);
		this.add(jMenuItemConnexionUtilisateur);
		this.add(jMenuItemProfil);
		this.add(jMenuItemDeconnexionUtilisateur);
	}


	private JButton getButtonParcourir(JLabel lChemin) {
		JButton bAvatar = new JButton("Parcourir...");
        bAvatar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Choisir le repertoire d'echange");
                int returnVal = chooser.showOpenDialog(UserView.this.mFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   lChemin.setText(chooser.getSelectedFile().getPath());
                }
            }
        });
        return bAvatar;
	}
	


	private ActionListener getFormListUser(Container mContent) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mContent.remove(jPanel);
				jPanel.removeAll();
				SwingUtilities.updateComponentTreeUI(mFrame);
				String[] col = {"Nom","Tag","Avatar", "Abonnement"};
				Object[][] data = UserView.this.userController.listUser();
				DefaultTableModel model = new DefaultTableModel(data,col);
		        JTable table = new JTable(model) {
		        	/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Class getColumnClass(int colonne) {
		        		return getValueAt(2, colonne).getClass();
		        	}
		        };
		        table.setRowHeight(140);
		        table.setAutoCreateRowSorter(true);
		        table.setAutoscrolls(true);
		        JTextField filtre = RowFilterUtil.createRowFilter(table);
		        JScrollPane scrollPane = new JScrollPane(table);
		        table.getColumn("Abonnement").setCellRenderer(new ButtonRenderer());
		        table.getColumn("Abonnement").setCellEditor(new ButtonEditor(new JCheckBox(), userController));
		        scrollPane.setColumnHeaderView(table.getTableHeader());
				jPanel.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
				jPanel.add(filtre, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
				jPanel.add(TwitupMainView.getButtonReturn(mContent, mFrame, jPanel), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
				mContent.add(jPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
				SwingUtilities.updateComponentTreeUI(mFrame);
			}
		};
	}

	private ActionListener getFormConsultListener(Container mContent) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mContent.remove(jPanel);
				jPanel.removeAll();
				SwingUtilities.updateComponentTreeUI(mFrame);
				User user = UserView.this.userController.getUserConnecte();
				JLabel label = new JLabel ("Nom : " + user.getName());
				jPanel.add(label, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
				JLabel label2 = new JLabel ("Tag : " + user.getUserTag());
				jPanel.add(label2, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
				ImageIcon imageAvatar = new ImageIcon(user.getAvatarPath());

                if(imageAvatar.getImageLoadStatus() != MediaTracker.COMPLETE) {
                    String avatar = "src/resources/images/default_avatar.png";
                    imageAvatar = new ImageIcon(avatar);
                }
                JLabel label3 = new JLabel (imageAvatar);
				jPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
				jPanel.add(TwitupMainView.getButtonReturn(mContent, mFrame, jPanel), new GridBagConstraints(0, 4, 1, 1, 1, 0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

				mContent.add(jPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
						GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
				SwingUtilities.updateComponentTreeUI(mFrame);
			}
		};
	}

	private ActionListener getFormConnectListener(Container mContent, JTextField unInputConnexion,
			JPasswordField unInputConnexion2) {
		return new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		mContent.remove(jPanel);
        		jPanel.removeAll();
        		SwingUtilities.updateComponentTreeUI(mFrame);
        		JLabel label = new JLabel ("Tag : ");
        		jPanel.add(label, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(unInputConnexion, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		JLabel label2 = new JLabel ("Mot de passe : ");
        		jPanel.add(label2, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(unInputConnexion2, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(this.getButtonConnect(unInputConnexion, unInputConnexion2), new GridBagConstraints(1, 3, 1, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(TwitupMainView.getButtonReturn(mContent, mFrame, jPanel), new GridBagConstraints(1, 4, 1, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		mContent.add(jPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		SwingUtilities.updateComponentTreeUI(mFrame);
        	}

			private Component getButtonConnect(JTextField labelTag, JTextField labelPass) {
				JButton button = new JButton("Connexion");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Optional<User> user = userController.connecter(labelTag.getText(),labelPass.getText(), menuConnecte, menuDeconnecte);
						if(labelTag.getText().equals("")||labelPass.getText().equals("")) {
				        	Icon image = new ImageIcon("src\\resources\\images\\logoIUP_50.jpg");
							JOptionPane.showMessageDialog(mFrame, "Veuillez saisir votre nom d'utilisateur / mot de passe", null, 0, image);
						}
						else if(user.isPresent()) {
				        	Icon image = new ImageIcon("src\\resources\\images\\logoIUP_50.jpg");
							JOptionPane.showMessageDialog(mFrame, "Vous etes connecte !", null, 0, image);
							labelPass.setText("");
							mContent.remove(jPanel);
							jPanel.removeAll();
			        		SwingUtilities.updateComponentTreeUI(mFrame);
						}
						else {
				        	Icon image = new ImageIcon("src\\resources\\images\\logoIUP_50.jpg");
							JOptionPane.showMessageDialog(mFrame, "Couple utilisateur / mot de passe non reconnu", null, 0, image);
						}
					}
				});
				return button;
			}
        };
	}

	private ActionListener getDeconnectListener(Container mContent) {
		return  new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		mContent.remove(jPanel);
        		jPanel.removeAll();
        		SwingUtilities.updateComponentTreeUI(mFrame);
        		JOptionPane.showMessageDialog(mFrame, "Vous etes deconnecte !");
        		ConnexionUtil.setConnection(false, menuConnecte, menuDeconnecte);
        	}
        };
	}

	private ActionListener getFormCreateListener(Container mContent, JTextField unInput, JTextField unInput2, JButton unInput3, JLabel label4) {
		return new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		mContent.remove(jPanel);
        		jPanel.removeAll();
        		SwingUtilities.updateComponentTreeUI(mFrame);
        		JLabel label = new JLabel ("Nom : ");
        		jPanel.add(label, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(unInput, new GridBagConstraints(1, 0, 2, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		JLabel label2 = new JLabel ("Tag : ");
        		jPanel.add(label2, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(unInput2, new GridBagConstraints(1, 1, 2, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		JLabel label3 = new JLabel ("Avatar : ");
        		jPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(unInput3, new GridBagConstraints(1, 2, 2, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(label4, new GridBagConstraints(3, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(this.getButtonAddUser(unInput, unInput2, label4), new GridBagConstraints(2, 4, 1, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		jPanel.add(TwitupMainView.getButtonReturn(mContent, mFrame, jPanel), new GridBagConstraints(1, 4, 1, 1, 1, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        		
        		mContent.add(jPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
        				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        		SwingUtilities.updateComponentTreeUI(mFrame);
        	}

			private Component getButtonAddUser(JTextField labelName, JTextField labelTag, JLabel labelAvatar) {
				JButton button = new JButton("Ajouter Utilisateur");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(labelName.getText().equals("")||labelTag.getText().equals("")) {
				        	Icon image = new ImageIcon("src\\resources\\images\\logoIUP_50.jpg");
							JOptionPane.showMessageDialog(mFrame, "Veuillez saisir le tag et le nom d'utilisateur", null, 0, image);
						}
						else if(userController.verification(labelTag.getText())) {
				        	Icon image = new ImageIcon("src\\resources\\images\\logoIUP_50.jpg");
							JOptionPane.showMessageDialog(mFrame, "Le tag est dejà utilise par quelqu'un d'autre, veuillez en entrer un autre", null, 0, image);
						}
						else {
							User user = new User(UUID.randomUUID(), labelTag.getText(), "azerty", labelName.getText(), new HashSet<String>(), labelAvatar.getText());
							userController.addUser(user);
							labelName.setText("");
							labelTag.setText("");
							labelAvatar.setText("Le chemin...");
				        	Icon image = new ImageIcon("src\\resources\\images\\logoIUP_50.jpg");
							JOptionPane.showMessageDialog(mFrame, "L'utilisateur est cree", null, 0, image);
						}
					}
				});
				return button;
			}
        };
	}

}
