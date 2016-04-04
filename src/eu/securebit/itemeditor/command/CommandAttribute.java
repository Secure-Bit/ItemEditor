package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgumentCommand;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.Argument;
import lib.securebit.itemeditor.commands.DefaultExecutor;

public class CommandAttribute extends CustomArgumentCommand implements DefaultExecutor {

	private final boolean enabled;
	
	public CommandAttribute() {
		super("attribute", Main.instance().getCommandPrefix());
		
		this.setPermission("ie.attribute");
		this.setExecutor(this);
		this.setDescription(Strings.get(Strings.DESCRIPTION_ATTRIBUTE));
		
		if (Main.getMinecraftVersion() >= 18) {
			this.enabled = true;
			
			this.registerArgument("clear", new ArgumentAttributeClear(this));
			this.registerArgument("damage", new ArgumentAttributeDamage(this));
			
			if (Main.getMinecraftVersion() >= 19) {
				this.registerArgument("protection", new ArgumentAttributeProtection(this));
				this.registerArgument("knockbackresistance", new ArgumentAttributeKnockbackResistance(this));
				this.registerArgument("speed", new ArgumentAttributeSpeed(this));
				this.registerArgument("attackspeed", new ArgumentAttributeAttackSpeed(this));
				this.registerArgument("maxhealth", new ArgumentAttributeMaxHealth(this));
			} else {
				ArgumentUnavailable argument = new CommandAttribute.ArgumentUnavailable();
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
			layout.line(Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_CLEAR, this.getName()));
			layout.line(Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_DAMAGE, this.getName()));
			
			if (Main.getMinecraftVersion() >= 19) {
				layout.line(Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_PROTECTION, this.getName()));
				layout.line(Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_MAXHEALTH, this.getName()));
				layout.line(Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_SPEED, this.getName()));
				layout.line(Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_KNOCKBACKRESISTANCE, this.getName()));
				layout.line(Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_ATTACKSPEED, this.getName()));
			}
			
			layout.commit(sender);
			return true;
		}
		
		Main.layout().message(sender, Strings.get(Strings.ERROR_COMMAND_ATTRIBUTE_NOT_AVAILABLE));
		
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
			Main.layout().message(sender, Strings.get(Strings.ERROR_COMMAND_ATTRIBUTE_ARGUMENT_NOT_AVAILABLE));
			return true;
		}
		
	}
}
