package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgumentCommand;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.DefaultExecutor;

public class CommandLore extends CustomArgumentCommand implements DefaultExecutor {

	public CommandLore() {
		super("describe", Main.instance().getCommandPrefix());
		
		this.setAliases("lore", "desc", "description");
		this.setPermission("ie.describe");
		this.setExecutor(this);
		this.setDescription("Modifies the lore of an item.");
		this.registerArgument("addline", new ArgumentLoreAddline(this));
		this.registerArgument("rmline", new ArgumentLoreRmline(this));
		this.registerArgument("clear", new ArgumentLoreClear(this));
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout().clone();
		layout.begin();
		layout.category("Describe$-Command");
		layout.line("/" + this.getName() + " addline <line>");
		layout.line("/" + this.getName() + " rmline [line]");
		layout.line("/" + this.getName() + " clear");
		layout.barrier();
		layout.commit(sender);
		
		return true;
	}

}
