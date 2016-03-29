package eu.securebit.itemeditor.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.Argument;

public class ArgumentLoreAddline extends Argument<Main> {

	public ArgumentLoreAddline(Main plugin) {
		super(plugin);
	}

	@Override
	public String getSyntax() {
		return "/describe addline <line>";
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
		
		if (args.length == 1) {
			return false;
		}
		
		ItemStack item = player.getItemInHand();
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, "-You have to hold an item in your hand.-");
		}
		
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		
		if (lore == null) {
			lore = new ArrayList<>();
		}
		
		lore.add("§r" + ChatColor.translateAlternateColorCodes('&', Main.buildMessage(args, 1, args.length - 1)));
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		player.setItemInHand(item);
		
		Main.layout().message(player, "+The line has been added!+");
		return true;
	}

}
