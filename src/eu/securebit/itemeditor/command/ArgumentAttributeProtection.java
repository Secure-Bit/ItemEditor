package eu.securebit.itemeditor.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;

public class ArgumentAttributeProtection extends ArgumentAttributeSet {

	@Override
	public String validateItem(ItemStack item) {
		switch (item.getType()) {
			case LEATHER_HELMET:
			case LEATHER_CHESTPLATE:
			case LEATHER_LEGGINGS:
			case LEATHER_BOOTS:
			case GOLD_HELMET:
			case GOLD_CHESTPLATE:
			case GOLD_LEGGINGS:
			case GOLD_BOOTS:
			case CHAINMAIL_HELMET:
			case CHAINMAIL_CHESTPLATE:
			case CHAINMAIL_LEGGINGS:
			case CHAINMAIL_BOOTS:
			case IRON_HELMET:
			case IRON_CHESTPLATE:
			case IRON_LEGGINGS:
			case IRON_BOOTS:
			case DIAMOND_HELMET:
			case DIAMOND_CHESTPLATE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_BOOTS:
			case ELYTRA:
				return null;
				
			default:
				return "Protection is only applicable on armor.";
		}
	}

	@Override
	public AttributeInfo getAttributeInfo(Player player, String[] args) {
		if (Main.isNumber(args[1], 0, 30)) {
			return new AttributeInfo("Protection", "generic.armor", Integer.parseInt(args[1]));
		}
		
		return null;
	}

	@Override
	public String getSyntax() {
		return "/attribute protection <value> (between 0 and 30)";
	}

}
