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

public class CommandHideInfo extends CustomUnargumentedCommand {

	public CommandHideInfo() {
		super("hideinfo", Main.instance().getCommandPrefix());
		
		this.setOnlyPlayers(true);
		this.setAliases("removeinfo", "rminfo", "rinfo", "deleteinfo", "delinfo", "hinfo", "hideflags");
		this.setPermission("ie.removeinfo");
		this.setUsage("/" + this.getName());
		this.setDescription("Hides flags of an item.");
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
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		
		Main.layout().message(player, "+ItemStack successfully modified!+");
		
		return true;
	}

}
