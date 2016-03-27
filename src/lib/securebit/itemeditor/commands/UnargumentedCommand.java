package lib.securebit.itemeditor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class UnargumentedCommand extends BasicCommand implements DefaultExecutor {
	
	private boolean onlyPlayers;
	
	public UnargumentedCommand(String name, CommandSettings settings, Plugin plugin) {
		super(name, settings, plugin);
	}
	
	@Override
	protected boolean executeCore(CommandSender sender, Command cmd, String label, String[] args) {
		if (this.onlyPlayers && !(sender instanceof Player)) {
			sender.sendMessage(this.getSettings().getMessageOnlyPlayer());
			return true;
		}
			
		boolean syntax = !this.onExecute(sender, cmd, label, args);
		
		if (syntax) {
			sender.sendMessage(String.format(this.getSettings().getMessageSyntax(), this.getUsage()));
		}
		
		return true;
	}
	
	public void setOnlyPlayers(boolean value) {
		this.onlyPlayers = value;
	}
	
	public boolean isOnlyPlayers() {
		return this.onlyPlayers;
	}
	
}
