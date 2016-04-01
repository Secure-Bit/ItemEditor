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
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentLoreAddline extends CustomArgument {
	
	public ArgumentLoreAddline(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return "/" + this.getCommand().getName() + " addline <line>";
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
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_ITEM_IN_HAND));
		}
		
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		
		if (lore == null) {
			lore = new ArrayList<>();
		}
		
		lore.add("§r" + ChatColor.translateAlternateColorCodes('&', Main.buildMessage(args, 1, args.length - 1)));
		meta.setLore(lore);
		item.setItemMeta(meta);
				
		Main.layout().message(player, Strings.get(Strings.SUCCESS_LORE_LINE_ADDED));
		return true;
	}

}
