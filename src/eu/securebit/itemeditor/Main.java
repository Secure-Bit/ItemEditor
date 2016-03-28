package eu.securebit.itemeditor;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.itemeditor.command.CommandAddInfo;
import eu.securebit.itemeditor.command.CommandBreakable;
import eu.securebit.itemeditor.command.CommandLore;
import eu.securebit.itemeditor.command.CommandRemoveInfo;
import eu.securebit.itemeditor.command.CommandRename;
import eu.securebit.itemeditor.command.CommandSkull;
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
	
	@Override
	public void onLoad() {
		Main.instance = this;
		Main.layout = new InfoLayout("ItemEditor");
		Main.layout.uncolorConsole = true;
	}
	
	@Override
	public void onEnable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		Main.layout.message(sender, "Creating commands...");
		
		new CommandRename().create();
		new CommandLore().create();
		new CommandRemoveInfo().create();
		new CommandAddInfo().create();
		new CommandBreakable().create();
		new CommandSkull().create();
		
		Main.layout.message(sender, "Plugin started!");
	}
	
	@Override
	public void onDisable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		Main.layout.message(sender, "Plugin stoped!");		
	}
	
}
