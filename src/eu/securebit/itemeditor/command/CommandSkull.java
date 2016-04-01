package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomUnargumentedCommand;

public class CommandSkull extends CustomUnargumentedCommand {

	public CommandSkull() {
		super("skull", Main.instance().getCommandPrefix());
		
		this.setUsage("/" + this.getName() + " [player]");
		this.setOnlyPlayers(true);
		this.setPermission("ie.skull");
		this.setDescription("Getting a skull by username.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		String username = player.getName();
		
		if (args.length == 1) {
			username = args[0];
		}
		
		boolean alreadyInHand = false;
		ItemStack skull = null;
		if (player.getInventory().getItemInHand().getType() == Material.SKULL_ITEM) {
			skull = player.getInventory().getItemInHand();
			alreadyInHand = true;
		} else {
			skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3); 
		}
		
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		if (!meta.setOwner(username)) {
			Main.layout().message(player, "-Cannot resolve username!-");
			return true;
		}
		
		skull.setItemMeta(meta);
		
		if (!alreadyInHand) {
			player.getInventory().addItem(skull);
		}
		
		Main.layout().message(player, "+Skull given! Username: " + username + "+");
		
		return true;
	}

}
