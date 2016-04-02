package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomUnargumentedCommand;
import eu.securebit.itemeditor.config.Strings;

public class CommandDurability extends CustomUnargumentedCommand {

	public CommandDurability() {
		super("durability", Main.instance().getCommandPrefix());
		
		this.setUsage(Strings.get(Strings.USAGE_COMMAND_DURABILITY, this.getName()));
		this.setOnlyPlayers(true);
		this.setPermission("ie.durability");
		this.setAliases("setdurability, changedurability, modifydurability");
		this.setDescription(Strings.get(Strings.DESCRIPTION_DURABILITY));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getItemInHand();
		
		if (args.length > 1) {
			return false;
		}
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_ITEM_IN_HAND));
			return true;
		}
		
		if (args.length == 0) {
			short durability = (short) (item.getType().getMaxDurability() - item.getDurability());
			int value = (int) Math.round(100 * (double) durability / (double) item.getType().getMaxDurability());
			Main.layout().message(player, Strings.get(Strings.SUCCESS_DURABILITY_VALUE, item.getType().toString(), value));
			return true;
		}
		
		if (!Main.isNumber(args[0], 0, 100)) {
			Main.layout().message(player, Strings.get(Strings.ERROR_INVALID_INTEGER, 0, 100));
			return true;
		}
		
		double percentageValue = Integer.parseInt(args[0]) * 0.01;
		int durabilityValue = (int) (item.getType().getMaxDurability() - percentageValue * item.getType().getMaxDurability());
		item.setDurability((short) durabilityValue);
		
		Main.layout().message(player, Strings.get(Strings.SUCCESS_DURABILITY_SET, item.getType().toString(), (int) (percentageValue * 100)));
				
		return true;
	}

}
