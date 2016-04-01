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
	
	public static String get(String key, Object... arguments) {
		if (arguments.length == 0) {
			return Strings.bundle.getString(key);
		}
		
		String result = Strings.bundle.getString(key);
		for (int i = 0; i < arguments.length; i++) {
			result = result.replace("{" + i + "}", arguments[i].toString());
		}
		
		return result;
	}
	
	public static ResourceBundle getBundle() {
		return Strings.bundle;
	}
	
	public static String getLanguage() {
		return Strings.language;
	}
	
	
	// ---------- console outputs ---------- //
	public static final String CONSOLE_INIT_COMMANDS = "console.init_commands";
	public static final String CONSOLE_PLUGIN_ENABLED = "console.plugin_enabled";
	public static final String CONSOLE_PLUGIN_DISABLED = "console.plugin_disabled";
	
	
	
	// ---------- errors ---------- //
	public static final String ERROR_ITEM_NO_LORE = "error.item_no_lore";
	public static final String ERROR_RESOLVE_COLOR = "error.resolve_color";
	public static final String ERROR_NO_ITEM_IN_HAND = "error.no_item_in_hand";
	public static final String ERROR_NO_COLOR_PROVIDED = "error.no_color_provided";
	public static final String ERROR_LORE_LINE_NOT_EXISTING = "error.lore_line_not_existing";
	public static final String ERROR_PARAMETER_INDEX_INVALID = "error.parameter_index_invalid";
	public static final String ERROR_NO_LEATHER_ARMOR_IN_HAND = "error.no_leather_armor_in_hand";
	public static final String ERROR_SPECIFY_THREE_INTEGERS_RGB = "error.specify_three_integers_rgb";
	public static final String ERROR_NO_ATTRIBUTE_INFORMATION_PROVIDED = "error.no_attribute_info_provided";
	public static final String ERROR_COMMAND_ATTRIBUTE_NOT_AVAILABLE = "error.command_attribute_not_available";
	public static final String ERROR_COMMAND_ATTRIBUTE_ARGUMENT_NOT_AVAILABLE = "error.command_attribute_argument_not_available";
	
	public static final String ERROR_ATTR_PROTECTION_ONLY_ARMOR = "error.attr.protection_only_armor";
	public static final String ERROR_ATTR_DAMAGE_ONLY_TOOLS_AND_WEAPONS = "error.attr.damage_only_tools_and_weapons";
	
	
	
	// ---------- success ---------- //
	public static final String SUCCESS_LORE_CLEARED = "success.lore_cleared";
	public static final String SUCCESS_LORE_LINE_ADDED = "success.lore_line_added";
	public static final String SUCCESS_LORE_LINE_REMOVED = "success.lore_line_removed";
	
	public static final String SUCCESS_ARMOR_COLOR_SET = "success.armor_color_set";
	public static final String SUCCESS_ARMOR_COLOR_REMOVED = "success.armor_color_removed";
	
	public static final String SUCCESS_ATTRIBUTE_SET = "success.attribute_set";
}
