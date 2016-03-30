package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;

public class ArgumentAttributeDamage extends ArgumentAttributeSet {

	@Override
	public String validateItem(ItemStack item) {
		String materialName = item.getType().toString().toLowerCase();
		if (materialName.contains("axe")
				|| materialName.contains("spade")
				|| materialName.contains("pickaxe")
				|| materialName.contains("sword")
				|| item.getType() == Material.STICK) {
			return null;
		}
		
		return "Attack damage is only applicable on tools and weapons.";
	}

	@Override
	public AttributeInfo getAttributeInfo(Player player, String[] args) {
		if (Main.isNumber(args[1], 0, Integer.MAX_VALUE)) {
			return new AttributeInfo("damage", "generic.attackDamage", Integer.parseInt(args[1]));
		}
		
		return null;
	}

	@Override
	public String getSyntax() {
		return "/attribute damage <value> (greater or equals 0)";
	}

}
