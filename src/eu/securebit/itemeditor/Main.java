package eu.securebit.itemeditor;

import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.itemeditor.command.CommandArmor;
import eu.securebit.itemeditor.command.CommandAttribute;
import eu.securebit.itemeditor.command.CommandBreakable;
import eu.securebit.itemeditor.command.CommandDurability;
import eu.securebit.itemeditor.command.CommandHideInfo;
import eu.securebit.itemeditor.command.CommandItemInfo;
import eu.securebit.itemeditor.command.CommandLore;
import eu.securebit.itemeditor.command.CommandPotion;
import eu.securebit.itemeditor.command.CommandRename;
import eu.securebit.itemeditor.command.CommandShowInfo;
import eu.securebit.itemeditor.command.CommandSkull;
import eu.securebit.itemeditor.config.ConfigValue;
import eu.securebit.itemeditor.config.Strings;
import eu.securebit.itemeditor.listener.ListenerGermanLanguageJoin;
import lib.securebit.itemeditor.InfoLayout;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static InfoLayout layout;
	
	public static Main instance() {
		return Main.instance;
	}
	
	public static InfoLayout layout() {
		return Main.layout;
	}
	
	public static String buildMessage(String[] args) {
		return buildMessage(args, 0, args.length - 1);
	}
	
	public static String buildMessage(String[] args, int startIndex, int endIndex) {
		if (startIndex > endIndex) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = startIndex; i <= endIndex; i++) {
			builder.append(args[i]);
			if (i != endIndex) {
				builder.append(" ");
			}
		}
		
		return builder.toString();
	}
	
	public static int getMinecraftVersion() {
		String versionString = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		versionString = versionString.substring(1, versionString.length() - 2);
		versionString = versionString.replace("_", "");
		return Integer.parseInt(versionString);
	}
	
	public static boolean isLeatherArmor(ItemStack item) {
		return item.getType() == Material.LEATHER_HELMET ||
				item.getType() == Material.LEATHER_CHESTPLATE ||
				item.getType() == Material.LEATHER_LEGGINGS ||
				item.getType() == Material.LEATHER_BOOTS;
				
	}
	
	public static boolean isPotion(ItemStack item) {
		return item.getType().toString().equals("POTION")
				|| item.getType().toString().equals("LINGERING_POTION")
				|| item.getType().toString().equals("SPLASH_POTION");
	}
	
	public static boolean isNumber(String string) {
		return isNumber(string, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public static boolean isNumber(String string, int minimum, int maximum) {
		try {
			int value = Integer.parseInt(string);
			return minimum <= value && value <= maximum;
		} catch (NumberFormatException exception) {
			return false;
		}
	}
	
	
	private String commandPrefix;
	
	@Override
	public void onLoad() {
		Main.instance = this;
		Main.layout = new InfoLayout("ItemEditor");
		Main.layout.uncolorConsole = true;
	}
	
	@Override
	public void onEnable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		
		this.saveDefaultConfig();
		if (this.getConfig().getBoolean(ConfigValue.NO_CONFLICT)) {
			this.commandPrefix = this.getConfig().getString(ConfigValue.NO_CONFLICT_PREFIX);
		}
		
		String language = this.getConfig().getString(ConfigValue.LANGUAGE);
		if (language.equalsIgnoreCase("de") || language.equalsIgnoreCase("german")) {
			language = "de";
			Bukkit.getPluginManager().registerEvents(new ListenerGermanLanguageJoin(), this);
		} else {
			language = "en";
		}
		
		Strings.init(language);
				
		Main.layout.message(sender, Strings.get(Strings.CONSOLE_INIT_COMMANDS));
		new CommandRename().create();
		new CommandLore().create();
		new CommandHideInfo().create();
		new CommandShowInfo().create();
		new CommandBreakable().create();
		new CommandSkull().create();
		new CommandArmor().create();
		new CommandAttribute().create();
		new CommandItemInfo().create();
		new CommandDurability().create();
		new CommandPotion().create();
		
		Main.layout.message(sender, Strings.get(Strings.CONSOLE_PLUGIN_ENABLED));
	}
	
	@Override
	public void onDisable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		ResourceBundle.clearCache();
		Main.layout.message(sender, Strings.get(Strings.CONSOLE_PLUGIN_DISABLED));		
	}
	
	public String getCommandPrefix() {
		return this.commandPrefix;
	}
}
