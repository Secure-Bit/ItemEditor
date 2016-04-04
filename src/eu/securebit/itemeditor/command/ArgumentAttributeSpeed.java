package eu.securebit.itemeditor.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentAttributeSpeed extends ArgumentAttributeSet {

	public ArgumentAttributeSpeed(BasicCommand command) {
		super(command);
	}

	@Override
	public String validateItem(ItemStack item) {
		return null;
	}

	@Override
	public AttributeInfo getAttributeInfo(Player player, String[] args) {
		try {
			return new AttributeInfo("movement speed", "generic.movementSpeed", Double.parseDouble(args[1]));
		} catch (NumberFormatException exception) {
			return null;
		}
	}

	@Override
	public String getSyntax() {
		return Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_SPEED, this.getCommand().getName());
	}

}
