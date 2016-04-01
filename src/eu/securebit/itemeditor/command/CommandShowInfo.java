package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomUnargumentedCommand;
import eu.securebit.itemeditor.config.Strings;

public class CommandShowInfo extends CustomUnargumentedCommand {

	public CommandShowInfo() {
		super("showinfo", Main.instance().getCommandPrefix());
		
		this.setOnlyPlayers(true);
		this.setAliases("addinfo", "ainfo", "sinfo", "showflags");
		this.setPermission("ie.addinfo");
		this.setUsage("/" + this.getName());
		this.setDescription("Makes the item flags visible.");
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
		
		ItemMeta meta = item.getItemMeta();
		meta.removeItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		
		Main.layout().message(player, "+ItemStack successfully modified!+");
		
		return true;
	}

}
