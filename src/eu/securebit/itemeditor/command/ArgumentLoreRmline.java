package eu.securebit.itemeditor.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.Argument;

public class ArgumentLoreRmline extends Argument<Main> {

	public ArgumentLoreRmline(Main main) {
		super(main);
	}

	@Override
	public String getSyntax() {
		return "/describe rmline [line]";
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
		
		ItemStack item = player.getItemInHand();
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, "-You have to hold an item in your hand!-");
		}
		
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		
		if (lore == null) {
			lore = new ArrayList<>();
		}
		
		if (lore.isEmpty()) {
			Main.layout().message(sender, "-The item does not have a lore!-");
		}
		
		int index;
		
		if (args.length == 1) {
			index = lore.size() - 1;
		} else {
			try {
				index = Integer.parseInt(args[1]) - 1;
			} catch (NumberFormatException ex) {
				Main.layout().message(player, "-The parameter 'index' has to be a valid number!-");
				return true;
			}
		}
		
		if (index < 0) {
			Main.layout().message(player, "-The line has to be greater than 0!-");
			return true;
		}
		
		if (index >= lore.size()) {
			Main.layout().message(sender, "-The line- *" + Integer.toString(index) + "* -does not exist!-");
			return true;
		}
		
		lore.remove(index);
		meta.setLore(lore);
		item.setItemMeta(meta);
				
		Main.layout().message(player, "+The line has been removed!+");
		return true;
	}

}
