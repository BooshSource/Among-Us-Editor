package club.koupah.aue.utility.playerprefs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.Setting;
import club.koupah.aue.utility.PopUp;

public class PlayerPrefsManager {
	
	Editor instance;
	public String[] newSettings;
	public String[] currentSettings;
	
	public PlayerPrefsManager(Editor instance) {
		this.instance = instance;
	}
	
	public void savePlayerPrefs() {
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(instance.playerPrefs), "UTF-8"))) {
			bufferedWriter.write(String.join(",", newSettings));
			bufferedWriter.close();
		} catch (IOException e) {
			new PopUp("Error writing to file!\n" + e.getMessage(), true);
		} 
	}

	public void loadSettings() {
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(instance.playerPrefs), "UTF-8"))) {

			String line = bufferedReader.readLine();
			System.out.println("Read following line from settings file: \n" + line);

			// This is unnecessary, playerPrefs file is 1 line
			// while(line != null) { line = bufferedReader.readLine(); }

			bufferedReader.close();
			
			boolean save = false;
			
			if (line == null) {/*Forgot to check if this was null lol*/
				//Default setting for people who for some reason have an empty playerPrefs file
				//Red, no cosmetics on and mouse+keyboard movement
				line = instance.defaultSettings;
				new PopUp("It doesn't look like you played the game before...\nYour settings have now been set to some default ones!", false);
				save = true;
			}
			
			if (line != null && line.contains(",") && line.split(",").length == 20)
				currentSettings = line.split(",");
			else
			new PopUp("Error loading settings (Potentially newer version?)\nScreenshot the following and send it to Koupah#5129 (Discord)\n"
							+ line + "\n[" + instance.playerPrefs.exists() +"]", true);

			newSettings = currentSettings;
			
			Setting.setCurrentSettings(currentSettings);
			
			//Save it so we don't have a "first time launching" message every launch until they save settings lol
			if (save) {
				savePlayerPrefs();
			}
		} catch (FileNotFoundException e) {
			new PopUp(
					"Error loading settings, file doesn't exist?\nAre you sure you have the game and have run it before?",
					true);
		} catch (IOException e) {
			new PopUp("Error reading playerPrefs!\n" + e.getMessage(), true);
		}
	}
}
