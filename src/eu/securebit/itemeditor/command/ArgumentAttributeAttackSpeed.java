package eu.securebit.itemeditor.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.command.abstracts.ArgumentAttributeSet;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentAttributeAttackSpeed extends ArgumentAttributeSet {

	public ArgumentAttributeAttackSpeed(BasicCommand command) {
		super(command);
	}

	@Override
	public String validateItem(ItemStack item) {
		return null;
	}

	@Override
	public AttributeInfo getAttributeInfo(Player player, String[] args) {
		try {
			double value = Double.parseDouble(args[1]);
			if (value < 0 || value > 1024) {
				throw new NumberFormatException();
			}
			
			return new AttributeInfo("attack speed", "generic.attackSpeed", value);
		} catch (NumberFormatException exception) {
			return null;
		}
	}

	@Override
	public String getSyntax() {
		return "/" + this.getCommand().getName() + " attackspeed <value> (between 0 and 1024, default 4)";
	}

}
