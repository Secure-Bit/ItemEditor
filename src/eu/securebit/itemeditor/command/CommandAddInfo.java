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

public class CommandAddInfo extends UnargumentedCommand {

	public CommandAddInfo() {
		super("addinfo", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setOnlyPlayers(true);
		this.setAliases("ainfo");
		this.setPermission("ie.addinfo");
		this.setUsage("/addinfo");
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
		meta.removeItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		
		player.setItemInHand(item);
		
		Main.layout().message(player, "+ItemStack successfully modified!+");
		
		return true;
	}

}
