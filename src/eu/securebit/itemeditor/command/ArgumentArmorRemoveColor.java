package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.Argument;

public class ArgumentArmorRemoveColor extends Argument<Main> {

	public ArgumentArmorRemoveColor() {
		super(Main.instance());
	}

	@Override
	public String getSyntax() {
		return "/armor removecolor";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean isOnlyForPlayer() {
		return true;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInHand();
		
		if (item == null || !Main.isLeatherArmor(item)) {
			Main.layout().message(player, "-You have to hold leather armor in your hand.-");
			return true;
		}
		
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(null);
		item.setItemMeta(meta);
		player.getInventory().setItemInHand(item);
		
		Main.layout().message(player, "+Color successfully removed!+");
		
		return true;
	}

}
