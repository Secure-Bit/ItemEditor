package eu.securebit.itemeditor.command;

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

public class ArgumentLoreClear extends CustomArgument {

	public ArgumentLoreClear(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return "/" + this.getCommand().getName() + " clear";
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
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_ITEM_IN_HAND));
		}
		
		ItemMeta meta = item.getItemMeta();
		if (meta.hasLore()) {
			List<String> lore = meta.getLore();
			lore.clear();
			meta.setLore(lore);
		}
		
		item.setItemMeta(meta);
				
		Main.layout().message(player, Strings.get(Strings.SUCCESS_LORE_CLEARED));
		
		return true;
	}

}
