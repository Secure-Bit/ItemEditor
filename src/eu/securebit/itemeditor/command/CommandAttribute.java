package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.ArgumentedCommand;
import lib.securebit.itemeditor.commands.DefaultExecutor;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public class CommandAttribute extends ArgumentedCommand implements DefaultExecutor {

	private final boolean enabled;
	
	public CommandAttribute() {
		super("attribute", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setPermission("ie.attribute");
		this.setExecutor(this);
		
		if (Main.getMinecraftVersion() >= 18) {
			this.enabled = true;
			this.registerArgument("damage", new ArgumentAttributeDamage());
			this.registerArgument("protection", new ArgumentAttributeProtection());
		} else {
			this.enabled = false;
		}
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		if (this.enabled) {
			InfoLayout layout = Main.layout().clone();
			layout.begin();
			layout.category("Attribute$-Command");
			layout.line("/attribute damage <value>");
			layout.line("/attribute protection <value>");
			layout.commit(sender);
			return true;
		}
		
		Main.layout().message(sender, "The attribute command is only available in Minecraft 1.8 and later versions.");
		
		return true;
	}

}
