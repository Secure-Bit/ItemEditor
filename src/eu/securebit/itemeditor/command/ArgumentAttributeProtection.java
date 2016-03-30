package eu.securebit.itemeditor.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;

public class ArgumentAttributeProtection extends ArgumentAttributeSet {

	@Override
	public String validateItem(ItemStack item) {
		return null;
	}

	@Override
	public AttributeInfo getAttributeInfo(Player player, String[] args) {
		return new AttributeInfo("Protection", "generic.armor", Integer.parseInt(args[1]));
	}

	@Override
	public String getSyntax() {
		return "/attribute protection <value> (between 0 and 30)";
	}

}
