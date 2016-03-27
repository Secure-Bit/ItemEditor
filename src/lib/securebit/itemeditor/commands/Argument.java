package lib.securebit.itemeditor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import lib.securebit.itemeditor.Validate;

public abstract class Argument<T extends Plugin> {
	
	protected final T plugin;
	
	public Argument(T plugin) {
		Validate.notNull(plugin, "Plugin cannot be null!");
		this.plugin = plugin;
	}
	
	public abstract String getSyntax();
	
	public abstract String getPermission();
	
	public abstract boolean isOnlyForPlayer();
	
	public abstract boolean execute(CommandSender sender, Command cmd, String label, String[] args);
	
	protected final T getPlugin() {
		return this.plugin;
	}
}
