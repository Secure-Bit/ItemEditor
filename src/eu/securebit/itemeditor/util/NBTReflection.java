package eu.securebit.itemeditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.inventory.ItemStack;

import lib.securebit.itemeditor.ReflectionUtil;

public class NBTReflection {

	public static Object convertToNMSItem(ItemStack item) {
		Class<?>[] params = { ItemStack.class };
		Method method = ReflectionUtil.getMethod(ReflectionUtil.getCraftBukkitClass("inventory.CraftItemStack"), "asNMSCopy", params);
		return ReflectionUtil.createStaticObject(method, item);
	}
	
	public static Object convertToBukkitItem(Object nmsItem) {
		Class<?>[] params = { ReflectionUtil.getNMSClass("ItemStack") };
		Method method = ReflectionUtil.getMethod(ReflectionUtil.getCraftBukkitClass("inventory.CraftItemStack"), "asBukkitCopy", params);
		return ReflectionUtil.createStaticObject(method, nmsItem);
	}
	
	public static Object getAttributeList(Object nbtTag) {
		Class<?>[] params = { String.class, int.class };
		Method m = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("NBTTagCompound"), "hasKeyOfType", params);
		Boolean result = (Boolean) ReflectionUtil.createObject(m, nbtTag, "AttributeModifiers", 9); // 9 = NBTTagList
		if (result) {
			Method getList = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("NBTTagCompound"), "getList", params);
			return ReflectionUtil.createObject(getList, nbtTag, "AttributeModifiers", 10); // 10 = NBTTagCompound
		} else {
			return ReflectionUtil.createObject(ReflectionUtil.getNMSClass("NBTTagList"));
		}
	}
	
	public static Object emptyCompound() {
		Constructor<?> c = ReflectionUtil.getConstructor(ReflectionUtil.getNMSClass("NBTTagCompound"), new Class<?>[0]);
		return ReflectionUtil.createObject(c);
	}
}
