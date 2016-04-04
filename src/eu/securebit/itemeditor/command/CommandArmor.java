package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgumentCommand;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.DefaultExecutor;

public class CommandArmor extends CustomArgumentCommand implements DefaultExecutor {

	public CommandArmor() {
		super("armor", Main.instance().getCommandPrefix());
		
		this.setAliases("armorcl", "armorcolor");
		this.setExecutor(this);
		this.setPermission("ie.armor");
		this.setDescription("Changes the leather armor color.");
		this.registerArgument("color", new ArgumentArmorColor(this));
		this.registerArgument("removecolor", new ArgumentArmorRemoveColor(this));
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout().clone();
		layout.begin();
		layout.category("Armor$-Command");
		layout.line(Strings.get(Strings.USAGE_COMMAND_ARMOR_COLOR, this.getName()));
		layout.line("/" + this.getName() + " removecolor");
		layout.commit(sender);
		
		return true;
	}
	

}
