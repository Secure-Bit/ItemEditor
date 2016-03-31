package eu.securebit.itemeditor.command;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.Argument;

public class ArgumentLoreClear extends Argument<Main> {

	public ArgumentLoreClear(Main plugin) {
		super(plugin);
	}

	@Override
	public String getSyntax() {
		return "/describe clear";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean isOnlyForPlayer() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (args.length != 1) {
			return false;
		}
		
		ItemStack item = player.getItemInHand();
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, "-You have to hold an item in your hand.-");
		}
		
		ItemMeta meta = item.getItemMeta();
		if (meta.hasLore()) {
			List<String> lore = meta.getLore();
			lore.clear();
			meta.setLore(lore);
		}
		
		item.setItemMeta(meta);
				
		Main.layout().message(player, "+The description (lore) has been cleared!+");
		
		return true;
	}

}
