package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgumentCommand;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.DefaultExecutor;

public class CommandLore extends CustomArgumentCommand implements DefaultExecutor {

	public CommandLore() {
		super("describe", Main.instance().getCommandPrefix());
		
		this.setAliases("lore", "desc", "description");
		this.setPermission("ie.describe");
		this.setExecutor(this);
		this.setDescription(Strings.get(Strings.DESCRIPTION_LORE));
		this.registerArgument("addline", new ArgumentLoreAddline(this));
		this.registerArgument("rmline", new ArgumentLoreRmline(this));
		this.registerArgument("clear", new ArgumentLoreClear(this));
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout().clone();
		layout.begin();
		layout.category("Describe$-Command");
		layout.line("/describe addline <line>");
		layout.line("/describe rmline [index]");
		layout.line("/describe clear");
		layout.barrier();
		layout.commit(sender);
		
		return true;
	}

}
