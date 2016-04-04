package eu.securebit.itemeditor.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentAttributeKnockbackResistance extends ArgumentAttributeSet {

	public ArgumentAttributeKnockbackResistance(BasicCommand command) {
		super(command);
	}

	@Override
	public String validateItem(ItemStack item) {
		return null;
	}

	@Override
	public AttributeInfo getAttributeInfo(Player player, String[] args) {
		if (Main.isNumber(args[1], 0, 100)) {
			double attributeValue = 0.01 * Integer.parseInt(args[1]);
			return new AttributeInfo("knockback resistance", "generic.knockbackResistance", attributeValue);
		}
		
		return null;
	}

	@Override
	public String getSyntax() {
		return Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_KNOCKBACKRESISTANCE, this.getCommand().getName());
	}

}
