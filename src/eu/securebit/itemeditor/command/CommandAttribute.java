package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.Argument;
import lib.securebit.itemeditor.commands.ArgumentedCommand;
import lib.securebit.itemeditor.commands.DefaultExecutor;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public class CommandAttribute extends ArgumentedCommand implements DefaultExecutor {

	private final boolean enabled;
	
	public CommandAttribute() {
		super("attribute", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setPermission("ie.attribute");
		this.setExecutor(this);
		this.setDescription("Alters generic attributes of items.");
		
		if (Main.getMinecraftVersion() >= 18) {
			this.enabled = true;
			
			this.registerArgument("damage", new ArgumentAttributeDamage());
			
			if (Main.getMinecraftVersion() >= 19) {
				this.registerArgument("protection", new ArgumentAttributeProtection());
				this.registerArgument("knockbackresistance", new ArgumentAttributeKnockbackResistance());
				this.registerArgument("speed", new ArgumentAttributeSpeed());
				this.registerArgument("attackspeed", new ArgumentAttributeAttackSpeed());
				this.registerArgument("maxhealth", new ArgumentAttributeMaxHealth());
			} else {
				ArgumentUnavailable argument = new ArgumentUnavailable();
				this.registerArgument("protection", argument);
				this.registerArgument("knockbackresistance", argument);
				this.registerArgument("speed", argument);
				this.registerArgument("attackspeed", argument);
				this.registerArgument("maxhealth", argument);
			}
			
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
			
			if (Main.getMinecraftVersion() >= 19) {
				layout.line("/attribute protection <value>");
				layout.line("/attribute maxhealth <value>");
				layout.line("/attribute speed <value>");
				layout.line("/attribute knockbackresistance <value>");
				layout.line("/attribute attackspeed <value>");
			}
			
			layout.commit(sender);
			return true;
		}
		
		Main.layout().message(sender, "The attribute command is only available in Minecraft 1.8 and later versions.");
		
		return true;
	}

	
	private class ArgumentUnavailable extends Argument<Main> {

		public ArgumentUnavailable() {
			super(Main.instance());
		}

		@Override
		public String getSyntax() {
			return null;
		}

		@Override
		public String getPermission() {
			return null;
		}

		@Override
		public boolean isOnlyForPlayer() {
			return false;
		}

		@Override
		public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
			Main.layout().message(sender, "-This argument is unavailable in Minecraft 1.8 and earlier versions.-");
			return true;
		}
		
	}
}
