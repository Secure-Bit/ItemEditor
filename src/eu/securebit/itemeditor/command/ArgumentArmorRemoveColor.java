package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentArmorRemoveColor extends CustomArgument {

	public ArgumentArmorRemoveColor(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return "/" + this.getCommand().getName() + " removecolor";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInHand();
		
		if (item == null || !Main.isLeatherArmor(item)) {
			Main.layout().message(player, "-You have to hold leather armor in your hand.-");
			return true;
		}
		
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(null);
		item.setItemMeta(meta);
		
		Main.layout().message(player, "+Color successfully removed!+");
		
		return true;
	}

}
