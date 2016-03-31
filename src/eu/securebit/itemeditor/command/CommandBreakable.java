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

public class CommandBreakable extends UnargumentedCommand {

	public CommandBreakable() {
		super("breakable", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setUsage("/breakable [true|false]");
		this.setOnlyPlayers(true);
		this.setPermission("ie.breakable");
		this.setAliases("break");
		this.setDescription("Makes items unbreakable / breakable.");
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
		
		boolean unbreakable;
		
		if (args.length == 0) {
			unbreakable = !meta.spigot().isUnbreakable();
		} else {
			unbreakable = !Boolean.parseBoolean(args[0]);
		}
		
		meta.spigot().setUnbreakable(unbreakable);
		item.setItemMeta(meta);
				
		Main.layout().message(player, "+ItemStack successfully modified; unbreakable:+ *" + Boolean.toString(unbreakable) + "* +!+");
		
		return true;
	}

}
