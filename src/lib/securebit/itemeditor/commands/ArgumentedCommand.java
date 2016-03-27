package lib.securebit.itemeditor.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import lib.securebit.itemeditor.Validate;

public class ArgumentedCommand extends BasicCommand {
	
	private HashMap<String, Argument<?>> arguments;
	
	private DefaultExecutor executor;
	
	public ArgumentedCommand(String name, CommandSettings settings, Plugin plugin) {
		super(name, settings, plugin);
		
		this.arguments = new HashMap<String, Argument<?>>();
	}
	
	@Override
	protected boolean executeCore(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 0) {
			for (String name : this.getArguments().keySet()) {
				if (args[0].equals(name)) {
					Argument<?> arg = this.getArgument(name);
					
					if (!(sender instanceof Player) && arg.isOnlyForPlayer()) {
						sender.sendMessage(this.getSettings().getMessageOnlyPlayer());
						return true;
					}
					
					if (arg.getPermission() != null && !this.hasPermission(sender, arg.getPermission())) {
						sender.sendMessage(this.getSettings().getMessageNoPermission());
						return true;
					}
					
					boolean printSyntax = !arg.execute(sender, cmd, label, args);
					
					if (printSyntax) {
						sender.sendMessage(String.format(this.getSettings().getMessageSyntax(), arg.getSyntax()));
					}
					
					return true;
				}
			}
			
			sender.sendMessage(this.getSettings().getMessageDefault());
		} else {
			if (this.executor != null) {
				return this.executor.onExecute(sender, cmd, label, args);
			}
		}
		
		return true;
	}
	
	public void registerArgument(String name, Argument<?> arg) {
		Validate.notNull(name, "Name cannot be null!");
		Validate.notNull(arg, "Argument cannot be null!");

		this.arguments.put(name, arg);
	}
	
	public void unregisterArgument(String name) {
		Validate.notNull(name, "Name cannot be null!");
		
		this.arguments.remove(name);
	}
	
	public void setExecutor(DefaultExecutor executor) {
		this.executor = executor;
	}
	
	public Argument<?> getArgument(String name) {
		Validate.notNull(name, "Name cannot be null!");

		return this.arguments.get(name);
	}
	
	public HashMap<String, Argument<?>> getArguments() {
		return this.arguments;
	}
	
}
