package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentPotionAddEffect extends CustomArgument {

	public ArgumentPotionAddEffect(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return Strings.get(Strings.USAGE_COMMAND_POTION_ADDEFFECT, this.getCommand().getName());
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
		
		if (args.length != 4) {
			return false;
		}
		
		if (!Main.isNumber(args[2], 0, Integer.MAX_VALUE)) {
			Main.layout().message(player, Strings.get(Strings.ERROR_INVALID_INTEGER, 0, Integer.MAX_VALUE));
			return true;
		}
		
		if (!Main.isNumber(args[3], 0, 255)) {
			Main.layout().message(player, Strings.get(Strings.ERROR_INVALID_INTEGER, 0, 255));
			return true;
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
		
		int duration = Integer.parseInt(args[2]) * 20;
		int amplifier = Integer.parseInt(args[3]) - 1;
		
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
		item.setItemMeta(meta);
		
		Main.layout().message(player, Strings.get(Strings.SUCCESS_POTION_EFFECT_ADD, type.getName(), amplifier, duration / 20));
		
		return true;
	}

}
