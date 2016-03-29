package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.UnargumentedCommand;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public class CommandRemoveInfo extends UnargumentedCommand {

	public CommandRemoveInfo() {
		super("removeinfo", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setOnlyPlayers(true);
		this.setAliases("rminfo", "rinfo", "deleteinfo", "delinfo");
		this.setPermission("ie.removeinfo");
		this.setUsage("/removeinfo");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getItemInHand();
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, "-You have to hold an item in your hand!-");
			return true;
		}
		
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		
		player.setItemInHand(item);
		
		Main.layout().message(player, "+ItemStack successfully modified!+");
		
		return true;
	}

}
