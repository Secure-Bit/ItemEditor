package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomUnargumentedCommand;

public class CommandDurability extends CustomUnargumentedCommand {

	public CommandDurability() {
		super("durability", Main.instance().getCommandPrefix());
		
		this.setUsage("/" + this.getName() + " [value]");
		this.setOnlyPlayers(true);
		this.setPermission("ie.durability");
		this.setAliases("setdurability, changedurability, modifydurability");
		this.setDescription("Sets / shows the durability of an item.");
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
			Main.layout().message(player, "-You have to hold an item in your hand.-");
			return true;
		}
		
		if (args.length == 0) {
			short durability = (short) (item.getType().getMaxDurability() - item.getDurability());
			int value = (int) Math.round(100 * (double) durability / (double) item.getType().getMaxDurability());
			Main.layout().message(player, "+Durability of your " + item.getType().toString() + ": " + value + "%+");
			return true;
		}
		
		if (!Main.isNumber(args[0], 0, 100)) {
			Main.layout().message(player, "-Please type a valid integer between 0 and 100.-");
			return true;
		}
		
		double percentageValue = Integer.parseInt(args[0]) * 0.01;
		int durabilityValue = (int) (item.getType().getMaxDurability() - percentageValue * item.getType().getMaxDurability());
		item.setDurability((short) durabilityValue);
		
		Main.layout().message(player, "+Success! Durability of your " + item.getType().toString() + " set to " + (int) (percentageValue * 100) + "%+");
				
		return true;
	}

}
