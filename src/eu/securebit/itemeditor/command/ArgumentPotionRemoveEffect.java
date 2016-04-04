package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentPotionRemoveEffect extends CustomArgument {

	public ArgumentPotionRemoveEffect(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return Strings.get(Strings.USAGE_COMMAND_POTION_REMOVEEFFECT, this.getCommand().getName());
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
		
	
		if (args.length != 2) {
			return false;
		}
		
		PotionEffectType type = null;
		if (Main.isNumber(args[1])) {
			type = PotionEffectType.getById(Integer.parseInt(args[1]));
		}
		
		if (type == null) {
			type = PotionEffectType.getByName(args[1]);
		}
		
		if (type == null) {
			Main.layout().message(player, Strings.get(Strings.ERROR_RESOLVE_POTION_TYPE));
			return true;
		}
		
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.removeCustomEffect(type);
		item.setItemMeta(meta);
		
		Main.layout().message(player, Strings.get(Strings.SUCCESS_POTION_EFFECT_REMOVED, type.getName()));
		
		return true;
	}

}
