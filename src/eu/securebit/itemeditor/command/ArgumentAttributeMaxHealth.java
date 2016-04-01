package eu.securebit.itemeditor.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentAttributeMaxHealth extends ArgumentAttributeSet {

	public ArgumentAttributeMaxHealth(BasicCommand command) {
		super(command);
	}

	@Override
	public String validateItem(ItemStack item) {
		return null;
	}

	@Override
	public AttributeInfo getAttributeInfo(Player player, String[] args) {
		if (Main.isNumber(args[1], 0, Integer.MAX_VALUE)) {
			return new AttributeInfo("maximum health", "generic.maxHealth", Integer.parseInt(args[1]));
		}
		
		return null;
	}

	@Override
	public String getSyntax() {
		return "/" + this.getCommand().getName() + " maxhealth <value>";
	}

}
