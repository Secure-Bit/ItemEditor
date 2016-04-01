package eu.securebit.itemeditor.command;

import java.lang.reflect.Field;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentArmorColor extends CustomArgument {

	public ArgumentArmorColor(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return "/" + this.getCommand().getName() + " color {<color> | <red> <green> <blue>}";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInHand();
		Color color = null;
		
		if (item == null || !Main.isLeatherArmor(item)) {
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_LEATHER_ARMOR_IN_HAND));
			return true;
		}
		
		if (args.length != 2 && args.length != 4) {
			return false;
		}
		
		if (args.length == 2) {
			try {
				Field[] fields = Color.class.getDeclaredFields();
				for (Field field : fields) {
					if (field.getType() == Color.class && field.getName().equalsIgnoreCase(args[1])) {
						color = (Color) field.get(null);
						break;
					}
				}
				
				if (color == null) {
					throw new Exception();
				}
				
			} catch (Exception exception) {
				Main.layout().message(player, Strings.get(Strings.ERROR_RESOLVE_COLOR, args[1]));
				return true;
			}
		}
		
		if (args.length == 4) {
			try {
				int red = Integer.parseInt(args[1]);
				int green = Integer.parseInt(args[2]);
				int blue = Integer.parseInt(args[3]);
				
				if (!isRGB(red) || !isRGB(green) || !isRGB(blue)) {
					throw new NumberFormatException();
				}
				
				color = Color.fromRGB(red, green, blue);
			} catch (NumberFormatException exception) {
				Main.layout().message(player, Strings.get(Strings.ERROR_SPECIFY_THREE_INTEGERS_RGB));
				return true;
			}
		}
		
		if (color == null) {
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_COLOR_PROVIDED));
			return true;
		}
		
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		
		int red = color.getRed();
		int blue = color.getBlue();
		int green = color.getGreen();
		Main.layout().message(player, Strings.get(Strings.SUCCESS_ARMOR_COLOR_SET, red, green, blue));
		
		return true;
	}
	
	private boolean isRGB(int value) {
		return value >= 0 && value <= 255;
	}
}
