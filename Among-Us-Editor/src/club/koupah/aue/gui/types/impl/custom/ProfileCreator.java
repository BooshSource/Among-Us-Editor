package club.koupah.aue.gui.types.impl.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.GUIComponent;
import club.koupah.aue.utility.PopUp;
import club.koupah.aue.utility.config.Profile;

public class ProfileCreator extends GUIComponent {

	String[] allProfileNames;
		
	JButton create;
	
	//Going to use this to load and delete profiles, saving/sharing will be another class
	public ProfileCreator(final JLabel label, JTextField component) {
		super(label, component);
	}
	
	@Override
	public void addToPane(GUIPanel contentPane) {
		index = contentPane.getSettingCount();
		
		create = new JButton("Create Profile");
		create.setToolTipText("Creates a profile with the current cosmetic settings!");
		
		label.setBounds(10, 15 + (index * Editor.scale), 100, 30);
		component.setBounds(260, 20 + (index * Editor.scale), 160, 20);
		
		create.setBounds(110, 20 + (index * Editor.scale), 140, 20);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = (String) ((JTextField) ProfileCreator.this.component).getText();
				if (Profile.profileExists(name)) {
					((JTextField) ProfileCreator.this.component).setText("");
					new PopUp("That profile already exists!", false);
					return;
				} else {
					
					if (name == null  || name.length() < 1) {
						new PopUp("You need a name for the profile!", false);
						return;
					}
					
					if (name.length() > 20) {
						new PopUp("That name is too long!", false);
						return;
					}
					
					if (name.length() < 3) {
						new PopUp("That name is too short!", false);
						return;
					}
					
					if (name.equals("None")) {
						new PopUp("You cannot create a profile with this name!", false);
						return;
					}
					
					((JTextField) ProfileCreator.this.component).setText("");
					String[] settings = Editor.getInstance().profileManager.makeProfileConfig(name);
					new Profile(settings);
					
					Editor.getInstance().profileManager.updateProfiles(name);	
					Editor.getInstance().configManager.saveConfig();
				}
			}
		});

		
		
		//I use a seperate method so I can then increment the settingCount value
		contentPane.addLabel(this.label);
		
		contentPane.add(this.component);
		contentPane.add(create);
	}
	

}
