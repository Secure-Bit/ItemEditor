package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentPotionClear extends CustomArgument {

	public ArgumentPotionClear(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return Strings.get(Strings.USAGE_COMMAND_POTION_CLEAR, this.getCommand().getName());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInHand();
		
		if (item == null || !Main.isPotion(item)) {
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_POTION_IN_HAND));
			return true;
		}
		
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		int effects = meta.getCustomEffects().size();
		meta.clearCustomEffects();
		item.setItemMeta(meta);
		
		Main.layout().message(player, Strings.get(Strings.SUCCESS_POTION_CLEARED, effects));
		
		return true;
	}

}
