package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentAttributeDamage extends ArgumentAttributeSet {

	public ArgumentAttributeDamage(BasicCommand command) {
		super(command);
	}

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
		
		return Strings.get(Strings.ERROR_ATTR_DAMAGE_ONLY_TOOLS_AND_WEAPONS);
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
		return "/" + this.getCommand().getName() + " damage <value> (greater or equals 0)";
	}

}
