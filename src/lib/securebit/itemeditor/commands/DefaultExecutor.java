package lib.securebit.itemeditor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface DefaultExecutor {
	
	public abstract boolean onExecute(CommandSender sender, Command cmd, String label, String[] args);
	
}
