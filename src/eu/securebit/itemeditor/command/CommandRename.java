package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.UnargumentedCommand;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;
import net.md_5.bungee.api.ChatColor;

public class CommandRename extends UnargumentedCommand {

	public CommandRename() {
		super("rename", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setOnlyPlayers(true);
		this.setAliases("changename", "ren", "re");
		this.setPermission("ie.rename");
		this.setUsage("/rename <new display name>");
		this.setDescription("Changes the display name of an item.");
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
		
		if (args.length == 0) {
			return false;
		}
		
		String newName = Main.buildMessage(args);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§r" + ChatColor.translateAlternateColorCodes('&', newName));
		item.setItemMeta(meta);
		
		Main.layout().message(player, "+ItemStack successfully renamed!+");
		
		return true;
	}
}
