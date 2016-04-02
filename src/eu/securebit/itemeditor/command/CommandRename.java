package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomUnargumentedCommand;
import eu.securebit.itemeditor.config.Strings;
import net.md_5.bungee.api.ChatColor;

public class CommandRename extends CustomUnargumentedCommand {

	public CommandRename() {
		super("rename", Main.instance().getCommandPrefix());
		
		this.setOnlyPlayers(true);
		this.setAliases("changename", "ren", "re");
		this.setPermission("ie.rename");
		this.setUsage(Strings.get(Strings.USAGE_COMMAND_RENAME, this.getName()));
		this.setDescription(Strings.get(Strings.DESCRIPTION_RENAME));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getItemInHand();
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_ITEM_IN_HAND));
			return true;
		}
		
		if (args.length == 0) {
			return false;
		}
		
		String newName = Main.buildMessage(args);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§r" + ChatColor.translateAlternateColorCodes('&', newName));
		item.setItemMeta(meta);
		
		Main.layout().message(player, Strings.get(Strings.SUCCESS_ITEM_RENAMED));
		
		return true;
	}
}
