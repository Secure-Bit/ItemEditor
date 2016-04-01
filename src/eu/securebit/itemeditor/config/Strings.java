package eu.securebit.itemeditor.config;

import java.util.Locale;
import java.util.ResourceBundle;

import eu.securebit.itemeditor.Main;

public final class Strings {

	private static String language;
	private static ResourceBundle bundle;
	
	public static void init(String language) {
		Strings.language = language;
		Strings.bundle = ResourceBundle.getBundle("strings/strings", new Locale(language), Main.class.getClassLoader());
	}
	
	public static String get(String key) {
		return Strings.bundle.getString(key);
	}
	
	public static ResourceBundle getBundle() {
		return Strings.bundle;
	}
	
	public static String getLanguage() {
		return Strings.language;
	}
	
	public static final String CONSOLE_INIT_COMMANDS = "console_init_commands";
	public static final String CONSOLE_PLUGIN_ENABLED = "console_plugin_enabled";
	public static final String CONSOLE_PLUGIN_DISABLED = "console_plugin_disabled";
}
