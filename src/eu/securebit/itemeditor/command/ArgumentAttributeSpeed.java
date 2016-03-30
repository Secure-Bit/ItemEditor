package eu.securebit.itemeditor.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;

public class ArgumentAttributeSpeed extends ArgumentAttributeSet {

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
		return "/attribute speed <value> (greater or equals 0, default 0.7)";
	}

}
