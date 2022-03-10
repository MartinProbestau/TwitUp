package com.iup.tp.twitup.ihm;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.iup.tp.twitup.controller.TwitController;
import com.iup.tp.twitup.controller.UserController;
import com.iup.tp.twitup.core.EntityManager;

/**
 * Classe de la vue principale de l'application.
 */
public class TwitupMainView {
		/**
		 * Fenetre du bouchon
		 */
		protected JFrame mFrame;

		/**
		 * Gestionnaire de bdd et de fichier.
		 */
		protected EntityManager mEntityManager;
		
		protected UserController userController;
		
		protected TwitController twitController;
		
		protected JPanel jPanel = new JPanel(new GridBagLayout());
		
		protected List<Component> menuConnecte;
		
		protected List<Component> menuDeconnecte;

		/**
		 * Constructeur.
		 * 
		 * @param database
		 *            , Base de données de l'application.
		 */
		public TwitupMainView(UserController userController, TwitController twitController) {
			this.userController = userController;
			this.twitController = twitController;
			menuConnecte = new ArrayList<Component>();
			menuDeconnecte = new ArrayList<Component>();
		}


		/**
		 * Lance l'afficahge de l'IHM.
		 */
		public void showGUI() {
			// Init auto de l'IHM au cas ou ;)
			if (mFrame == null) {
				this.initGUI();
			}

			// Affichage dans l'EDT
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// Custom de l'affichage
					JFrame frame = mFrame;
					Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation((screenSize.width - frame.getWidth()) / 6,
							(screenSize.height - frame.getHeight()) / 4);

					// Affichage
					mFrame.setVisible(true);

					mFrame.pack();
				}
			});
		}

		/**
		 * Initialisation de l'IHM
		 */
		protected void initGUI() {
			// Création de la fenetre principale
			mFrame = new JFrame("MOCK");
			mFrame.setPreferredSize(new Dimension(800,700));
			mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mFrame.setLayout(new GridBagLayout());
			JMenuBar mMenuBar = this.getMenuBar();
			
			mFrame.setJMenuBar(mMenuBar);
			Container mContent = mFrame.getContentPane();
    		mContent.add(this.getButtonClose(), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH,
    				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		}

		private JButton getButtonClose() {
			JButton mButton = new JButton();
			mButton.setText("Quitter");
			mButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
			ImageIcon image = new ImageIcon("src\\resources\\images\\exitIcon_20.png");
			mButton.setIcon(image);
			return mButton;
		}

		private JMenuBar getMenuBar() {
			JMenuBar jMenuBar = new JMenuBar();
			jMenuBar.add(this.getMenuFichier());
			jMenuBar.add(new UserView(mFrame, menuConnecte, menuDeconnecte, jPanel, userController));
			jMenuBar.add(new TwitView(mFrame, menuConnecte, menuDeconnecte, jPanel, userController, twitController));
			jMenuBar.add(this.getMenuInform());
			return jMenuBar;
		}

		protected static JButton getButtonReturn(Container mContent, JFrame mframe, JPanel jPanel) {
			JButton mButton = new JButton();
			mButton.setText("Retour");
			mButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mContent.remove(jPanel);
	        		SwingUtilities.updateComponentTreeUI(mframe);
				}
			});
			return mButton;
		}

		private JMenuItem getMenuInform() {
			JMenu mMenuInform = new JMenu("?");
			JMenuItem jMenuItemAPropos = new JMenuItem("A propos");
			ActionListener InformationListener = new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	Icon image = new ImageIcon("src\\resources\\images\\logoIUP_50.jpg");
					JOptionPane.showMessageDialog(mFrame, "Message", null, 0, image);
		        }
		      };
			jMenuItemAPropos.addActionListener(InformationListener);
			mMenuInform.add(jMenuItemAPropos);
			return mMenuInform;
		}

		private JMenu getMenuFichier() {
			JMenu mMenu = new JMenu("Fichier");
			JMenuItem jMenuItemEchange = new JMenuItem("Choisir répertoire d'échange");
			ActionListener FichierListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					JFileChooser jFileChooser = new JFileChooser();
					jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jFileChooser.setDialogTitle("Choisir le répertoire d'échanges");;
					jFileChooser.showOpenDialog(mFrame);
				}
			};
			jMenuItemEchange.addActionListener(FichierListener);
			mMenu.add(jMenuItemEchange);
			return mMenu;
		}
}