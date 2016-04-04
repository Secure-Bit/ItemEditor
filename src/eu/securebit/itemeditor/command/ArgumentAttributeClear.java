package eu.securebit.itemeditor.command;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgument;
import eu.securebit.itemeditor.config.Strings;
import eu.securebit.itemeditor.util.NBTReflection;
import lib.securebit.itemeditor.ReflectionUtil;
import lib.securebit.itemeditor.commands.BasicCommand;

public class ArgumentAttributeClear extends CustomArgument {

	public ArgumentAttributeClear(BasicCommand command) {
		super(command);
	}

	@Override
	public String getSyntax() {
		return Strings.get(Strings.USAGE_COMMAND_ATTRIBUTE_CLEAR, this.getCommand().getName());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInHand();
		
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, Strings.get(Strings.ERROR_NO_ITEM_IN_HAND));
			return true;
		}
		
		Object nmsItem = NBTReflection.convertToNMSItem(item);
		Field fieldTag = ReflectionUtil.getDeclaredField(ReflectionUtil.getNMSClass("ItemStack"), "tag");
		Object tag = ReflectionUtil.createObject(fieldTag, nmsItem);
		if (tag == null) {
			tag = NBTReflection.emptyCompound();
		}
		
		Object attributeList = NBTReflection.getAttributeList(tag);
		NBTReflection.clearAttributeList(attributeList);
		NBTReflection.compoundSet(tag, "AttributeModifiers", attributeList);
		ReflectionUtil.setValue(fieldTag, nmsItem, tag);
		
		player.setItemInHand((ItemStack) NBTReflection.convertToBukkitItem(nmsItem));
		Main.layout().message(player, Strings.get(Strings.SUCCESS_ATTRIBUTES_CLEARED, item.getType().toString()));
		
		return true;
	}

}
