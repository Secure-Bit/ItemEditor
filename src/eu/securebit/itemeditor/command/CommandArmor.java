package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.ArgumentedCommand;
import lib.securebit.itemeditor.commands.DefaultExecutor;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public class CommandArmor extends ArgumentedCommand implements DefaultExecutor {

	public CommandArmor() {
		super("armor", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setAliases("armorcl", "armorcolor");
		this.setExecutor(this);
		this.setPermission("ie.armor");
		this.registerArgument("color", new ArgumentArmorColor());
		this.registerArgument("removecolor", new ArgumentArmorRemoveColor());
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout().clone();
		layout.begin();
		layout.category("Armor$-Command");
		layout.line("/armor color {<color> | <red> <green> <blue>}");
		layout.line("/armor removecolor");
		layout.commit(sender);
		
		return true;
	}
	

}
