package eu.securebit.itemeditor.command;

import java.lang.reflect.Field;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.Argument;

public class ArgumentArmorColor extends Argument<Main> {

	public ArgumentArmorColor() {
		super(Main.instance());
	}

	@Override
	public String getSyntax() {
		return "/armor color {<color> | <red> <green> <blue>}";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean isOnlyForPlayer() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInHand();
		Color color = null;
		
		if (item == null || !Main.isLeatherArmor(item)) {
			Main.layout().message(player, "-You have to hold leather armor in your hand.-");
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
				Main.layout().message(player, "-Cannot resolve the color from your input.-");
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
				Main.layout().message(player, "-Specify 3 integer values (red, green, blue) between 0 and 255.-");
				return true;
			}
		}
		
		if (color == null) {
			Main.layout().message(player, "-No color provided.-");
			return true;
		}
		
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		player.getInventory().setItemInHand(item);
		
		int red = color.getRed();
		int blue = color.getBlue();
		int green = color.getGreen();
		Main.layout().message(player, "+Success! Color was set to RGB(" + red + ", " + green + ", " + blue + ").+");
		
		return true;
	}
	
	private boolean isRGB(int value) {
		return value >= 0 && value <= 255;
	}
}
