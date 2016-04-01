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
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentLoreRmline extends CustomArgument {

	public ArgumentLoreRmline(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return "/" + this.getCommand().getName() + " rmline [line]";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		ItemStack item = player.getItemInHand();
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_ITEM_IN_HAND));
		}
		
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		
		if (lore == null) {
			lore = new ArrayList<>();
		}
		
		if (lore.isEmpty()) {
			Main.layout().message(player, Strings.get(Strings.ERROR_ITEM_NO_LORE));
		}
		
		int index;
		
		if (args.length == 1) {
			index = lore.size() - 1;
		} else {
			try {
				index = Integer.parseInt(args[1]) - 1;
				if (index < 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException ex) {
				Main.layout().message(player, Strings.get(Strings.ERROR_PARAMETER_INDEX_INVALID));
				return true;
			}
		}
		
		if (index >= lore.size()) {
			Main.layout().message(player, Strings.get(Strings.ERROR_LORE_LINE_NOT_EXISTING, index + 1));
			return true;
		}
		
		lore.remove(index);
		meta.setLore(lore);
		item.setItemMeta(meta);
				
		Main.layout().message(player, Strings.get(Strings.SUCCESS_LORE_LINE_REMOVED));
		return true;
	}

}
