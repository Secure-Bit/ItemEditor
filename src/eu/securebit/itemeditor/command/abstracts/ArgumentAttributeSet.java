package eu.securebit.itemeditor.command.abstracts;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
		
		// TODO Improve performance of this code by caching reflection lookups
		
		
		Object nmsItem = NBTReflection.convertToNMSItem(item);
		Field fieldTag = ReflectionUtil.getDeclaredField(ReflectionUtil.getNMSClass("ItemStack"), "tag");
		Object tag = ReflectionUtil.createObject(fieldTag, nmsItem);
		if (tag == null) {
			tag = NBTReflection.emptyCompound();
		}
		
		Object attributeList = NBTReflection.getAttributeList(tag);

		{
			Class<?> tlclass = ReflectionUtil.getNMSClass("NBTTagList");
			Method methodSize = ReflectionUtil.getMethod(tlclass, "size", new Class<?>[0]);
			Method methodGet = ReflectionUtil.getMethod(tlclass, "get", new Class[] { int.class });
			Method methodGetString = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("NBTTagCompound"), "getString", new Class<?>[] { String.class });
			Method methodRemove = ReflectionUtil.getMethod(tlclass, "remove", new Class[] { int.class });
			Integer listSize = (Integer) ReflectionUtil.createObject(methodSize, attributeList);
			for (int i = 0; i < listSize; i++) {
				Object entry = ReflectionUtil.createObject(methodGet, attributeList, i);
				String attrName = (String) ReflectionUtil.createObject(methodGetString, entry, "AttributeName");
				if (attrName.equals(info.getAttributeName())) {
					ReflectionUtil.invokeMethod(methodRemove, attributeList, i);
				}
			}
		}
		
		Object attribute = NBTReflection.emptyCompound();
		
		{
			Class<?> compoundC = ReflectionUtil.getNMSClass("NBTTagCompound");
			Method a = ReflectionUtil.getMethod(compoundC, "a", new Class<?>[] { String.class, UUID.class });
			Method setInt = ReflectionUtil.getMethod(compoundC, "setInt", new Class<?>[] { String.class, int.class });
			Method setString = ReflectionUtil.getMethod(compoundC, "setString", new Class<?>[] { String.class, String.class });
			Method setDouble = ReflectionUtil.getMethod(compoundC, "setDouble", new Class<?>[] { String.class, double.class });
			
			ReflectionUtil.invokeMethod(a, attribute, "UUID", ReflectionUtil.createStaticObject(ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("MathHelper"), "a", new Class<?>[] { Random.class }), ThreadLocalRandom.current()));
			ReflectionUtil.invokeMethod(setInt, attribute, "Operation", 0);
			ReflectionUtil.invokeMethod(setDouble, attribute, "Amount", info.getAttributeValue());
			ReflectionUtil.invokeMethod(setString, attribute, "Name", "CustomAttribute"); // name doesn't care
			ReflectionUtil.invokeMethod(setString, attribute, "AttributeName", info.getAttributeName());
		}
		
		Method methodAdd = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("NBTTagList"), "add", new Class<?>[] { ReflectionUtil.getNMSClass("NBTBase") });
		ReflectionUtil.invokeMethod(methodAdd, attributeList, attribute);
		
		Method methodSet = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("NBTTagCompound"), "set", new Class<?>[] { String.class, ReflectionUtil.getNMSClass("NBTBase") });
		ReflectionUtil.invokeMethod(methodSet, tag, "AttributeModifiers", attributeList);
		
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
