package club.koupah.amongus.editor;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import club.koupah.amongus.editor.gui.settings.cosmetics.Colors;
import club.koupah.amongus.editor.gui.settings.cosmetics.Hats;
import club.koupah.amongus.editor.gui.settings.cosmetics.Pets;
import club.koupah.amongus.editor.gui.settings.cosmetics.Skins;
import club.koupah.amongus.editor.utility.PopUp;

public class AUEMain {

	// Ideally I'm going to make my own Look & Feel but for now, windows is desired
	static String desiredLookAndFeel = "WindowsLookAndFeel";
	
	static double version = 1.469;
	
	public static void main(String[] args) {
		
		// Idk how to get them to initialize their values cause am big noob
		Hats.values();
		Pets.values();
		Skins.values();
		Colors.values();
		
		//Local variable, I'm not going to use it again from outside this class
		Editor editor = new Editor(version);
		
		//Default look and feel
		editor.lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		
			// Run this first, before me do any GUI stuff
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (info.getClassName().contains(desiredLookAndFeel)) {
					editor.lookAndFeel = info.getClassName();
					editor.windows = true;
					break;
				}
			}
		
			if (editor.windows) {
				try {
					UIManager.setLookAndFeel(editor.lookAndFeel);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					new PopUp("Your system reported itself as having the Windows Look & Feel but didn't work!\nResorting to basic java L&F\n"
							+ e1.getMessage(), false);
				}
				
				//Because JPanels in tabbed panels are fucked and don't let me change the background color,
				//Set the panels to not be opaque that way we use the original background color
				//UIManager.put("TabbedPane.contentOpaque", false);
				
			} else {
				/*
				 * 
				 * Don't set any look & feel, Sorry non-windows users.
				 * You'll have to deal with Java's ugly L&F until I write up my own.
				 * (Unless someone wants to provide a nice Linux/Mac L&F :P)
				 * 
				 */
					
				
				 //UIManager.put("ToolTip.background", Editor.background);
				 //UIManager.put("ToolTip.border", new BorderUIResource(new LineBorder(Color.BLACK)));
			}
			
			//Setup the config and other stuff
			editor.setupFiles();
		   
			//Finish setting the window up
			editor.setupWindow();
						
			//Show the frame! Where the magic happens
			editor.setVisible(true);
			
			//Run update check, this is a seperate thread so it won't interrupt the main thread
			editor.runUpdateCheck(null);
	}
	
}
