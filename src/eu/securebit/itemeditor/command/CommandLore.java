package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.ArgumentedCommand;
import lib.securebit.itemeditor.commands.DefaultExecutor;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public class CommandLore extends ArgumentedCommand implements DefaultExecutor {

	public CommandLore() {
		super("describe", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setAliases("lore", "desc", "description");
		this.setPermission("ie.describe");
		this.setExecutor(this);
		this.registerArgument("addline", new ArgumentLoreAddline(Main.instance()));
		this.registerArgument("rmline", new ArgumentLoreRmline(Main.instance()));
		this.registerArgument("clear", new ArgumentLoreClear(Main.instance()));
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout().clone();
		layout.begin();
		layout.category("Desrcibe$-Command");
		layout.line("/describe addline <line>");
		layout.line("/describe rmline [line]");
		layout.line("/describe clear");
		layout.barrier();
		layout.commit(sender);
		
		return true;
	}

}
