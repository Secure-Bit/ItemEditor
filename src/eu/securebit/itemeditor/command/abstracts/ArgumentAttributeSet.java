package eu.securebit.itemeditor.command.abstracts;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.util.NBTReflection;
import lib.securebit.itemeditor.ReflectionUtil;
import lib.securebit.itemeditor.commands.Argument;

public abstract class ArgumentAttributeSet extends Argument<Main> {

	public ArgumentAttributeSet() {
		super(Main.instance());
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
		ItemStack item = player.getItemInHand();
		if (item == null || item.getType() == Material.AIR) {
			Main.layout().message(player, "-You have to hold an item in your hands.-");
			return true;
		}
		
		String errorMessage = this.validateItem(item);
		if (errorMessage != null) {
			Main.layout().message(player, "-" + errorMessage + "-");
			return true;
		}
		
		if (args.length != 2) {
			return false;
		}
		
		AttributeInfo info = this.getAttributeInfo(player, args);
		if (info == null) {
			Main.layout().message(player, "-No or malformed attribute information provided.-");
			return true;
		}		
		
		Object nmsItem = NBTReflection.convertToNMSItem(item);
		Field fieldTag = ReflectionUtil.getDeclaredField(ReflectionUtil.getNMSClass("ItemStack"), "tag");
		Object tag = ReflectionUtil.createObject(fieldTag, nmsItem);
		if (tag == null) {
			tag = NBTReflection.emptyCompound();
		}
		
		Object attributeList = NBTReflection.getAttributeList(tag);
		NBTReflection.removeDuplicates(attributeList, info.getAttributeName());
		Object attribute = NBTReflection.emptyCompound();
		NBTReflection.writeAttribute(attribute, info.getAttributeName(), info.getAttributeValue());
		NBTReflection.addToList(attributeList, attribute);
		NBTReflection.compoundSet(tag, "AttributeModifiers", attributeList);
		ReflectionUtil.setValue(fieldTag, nmsItem, tag);
		
		item = (ItemStack) NBTReflection.convertToBukkitItem(nmsItem);
		
		player.setItemInHand(item);
		
		String msg = "+Attribute '" + info.getLabel() + "' successfully set to " + info.getAttributeValue() + ".+";
		Main.layout().message(player, msg);
		
		return true;
	}
	
	public abstract String validateItem(ItemStack item);
	public abstract AttributeInfo getAttributeInfo(Player player, String[] args);
	
	protected class AttributeInfo {
		
		private String label;
		private String attributeName;
		private double attributeValue;
		
		public AttributeInfo(String label, String attributeName, double attributeValue) {
			this.label = label;
			this.attributeName = attributeName;
			this.attributeValue = attributeValue;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public String getAttributeName() {
			return this.attributeName;
		}
		
		public double getAttributeValue() {
			return this.attributeValue;
		}
	}
}
