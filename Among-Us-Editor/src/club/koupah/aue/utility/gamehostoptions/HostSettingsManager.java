package club.koupah.aue.utility.gamehostoptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import club.koupah.aue.utility.PopUp;

public class HostSettingsManager {

	File hostSettings;

	boolean exists;

	String[] currentHex;

	String[] newHex;

	public HostSettingsManager(File hostSettings) {
		this.hostSettings = hostSettings;
		this.exists = hostSettings.exists();

		if (exists) {
			try {
				currentHex = readHex();
				newHex = currentHex.clone();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Current Hex: ");

		int index = 0;
		for (String hex : currentHex) {
			if (index % 16 == 0) {
				System.out.println("");
			}
			System.out.print(hex + ' ');
			index++;
		}

		System.out.println("\n");
	}

	public void setIndex(int index, String value) {
		newHex[index] = value;
	}

	public String[] readHex() throws IOException {
		ArrayList<String> hexValues = new ArrayList<String>();
		FileInputStream fin = new FileInputStream(hostSettings);

		int len;
		byte data[] = new byte[16];
		int index = 0;

		while ((len = fin.read(data)) != -1) {
			for (int j = 0; j < len; j++) {
				hexValues.add(String.format("%02X", data[j]));
				index++;
			}
		}
		fin.close();
		String[] hex = new String[index];
		index = 0;
		for (String h : hexValues) {
			hex[index] = h;
			index++;
		}

		return hex;
	}

	public String[] getHex() {
		return this.currentHex;
	}

	public String getHex(int index, int length) {
		String toreturn = "";
		for (int i = (length / 8); i > 0; i--) {
			toreturn += this.currentHex[index + i - 1];
		}
		return toreturn;
	}

	public String[] getHexArray(int index, int length) {
		String[] toreturn = new String[length / 8];
		for (int i = 0; i < (0 + (length / 8)); i++)
			toreturn[i] = this.currentHex[index + i];

		return toreturn;
	}

	public void saveHostSettings() {

		try (OutputStream os = new FileOutputStream(hostSettings)) {
			int index = 0;
			for (String hex : newHex) {

				if (index % 16 == 0) {
					System.out.println("");
				}
				System.out.print(hex + ' ');
				index++;

				os.write((char) Integer.parseInt(hex, 16));
			}
			os.close();
		} catch (IOException e) {
			new PopUp("Error writing to file!\n" + e.getMessage(), true);
		}

	}
}
