package eu.securebit.itemeditor.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

@SuppressWarnings("deprecation")
public class ArgumentPotionColor extends CustomArgument {
	
	private static final List<PotionColorData> COLOR_TABLE = Arrays.asList(
			new PotionColorData(0x01, "blue"),
			new PotionColorData(0x05, "dark_red", "darkred"),
			new PotionColorData(0x06, "red", "lightred", "light_red"),
			new PotionColorData(0x07, "purple"),
			new PotionColorData(0x08, "lime", "light_green", "lightgreen"),
			new PotionColorData(0x0A, "pink", "light_purple", "lightpurple"),
			new PotionColorData(0x0C, "orange"),
			new PotionColorData(0x0D, "dark_blue", "darkblue"),
			new PotionColorData(0x13, "dark_green", "darkgreen"),
			new PotionColorData(0x1A, "green")
	);
	
	public ArgumentPotionColor(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return Strings.get(Strings.USAGE_COMMAND_POTION_COLOR, this.getCommand().getName());
	}

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
		if (Main.isNumber(args[1], 1, Integer.MAX_VALUE)) {
			type = PotionEffectType.getById(Integer.parseInt(args[1]));
		}
		
		if (type == null) {
			label:
			for (PotionColorData data : COLOR_TABLE) {
				for (String color : data.getColors()) {
					if (args[1].equalsIgnoreCase(color)) {
						type = PotionEffectType.getById(data.getId());
						break label;
					}
				}
			}
		}
		
		if (type == null) {
			Main.layout().message(player, Strings.get(Strings.ERROR_RESOLVE_POTION_TYPE));
			return true;
		}
		
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(PotionType.getByEffect(type)));
		meta.removeItemFlags(ItemFlag.values());
		meta.clearCustomEffects();
		item.setItemMeta(meta);
		
		Main.layout().message(player, Strings.get(Strings.SUCCESS_POTION_COLORED, type.getName()));
		
		return true;
	}

	
	private static class PotionColorData {
		
		private int id;
		private List<String> colors;
		
		public PotionColorData(int id, String... color) {
			this.id = id;
			this.colors = Arrays.asList(color);
		}
		
		public int getId() {
			return this.id;
		}
		
		public List<String> getColors() {
			return this.colors;
		}
	}
}
