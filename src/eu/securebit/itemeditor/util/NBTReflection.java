package eu.securebit.itemeditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import lib.securebit.itemeditor.ReflectionUtil;

public class NBTReflection {

	private static Class<?> classNBTTagCompound;
	private static Class<?> classCraftItemStack;
	private static Class<?> classNBTTagList;
	private static Class<?> classItemStack;
	
	private static Method methodNMSCopy;
	private static Method methodBukkitCopy;
	private static Method methodKeyOfType;
	private static Method methodGetList;
	private static Method methodTagListAdd;
	private static Method methodTagCompoundGetString;
	private static Method methodTagCompoundSet;
	private static Method methodTagCompoundSetInt;
	private static Method methodTagCompoundSetString;
	private static Method methodTagCompoundSetDouble;
	
	private static Constructor<?> constructorNBTTagCompound;
	
	static {
		classNBTTagCompound = ReflectionUtil.getNMSClass("NBTTagCompound");
		classCraftItemStack = ReflectionUtil.getCraftBukkitClass("inventory.CraftItemStack");
		classNBTTagList = ReflectionUtil.getNMSClass("NBTTagList");
		classItemStack = ReflectionUtil.getNMSClass("ItemStack");
		
		methodNMSCopy = ReflectionUtil.getMethod(classCraftItemStack, "asNMSCopy", new Class<?>[] { ItemStack.class });
		methodBukkitCopy = ReflectionUtil.getMethod(classCraftItemStack, "asBukkitCopy", new Class<?>[] { classItemStack });
		methodKeyOfType = ReflectionUtil.getMethod(classNBTTagCompound, "hasKeyOfType", new Class<?>[] { String.class, int.class });
		methodGetList = ReflectionUtil.getMethod(classNBTTagCompound, "getList", new Class<?>[] { String.class, int.class });
	
		constructorNBTTagCompound = ReflectionUtil.getConstructor(classNBTTagCompound, new Class<?>[0]);
		
		methodTagListAdd = ReflectionUtil.getMethod(classNBTTagList, "add", new Class<?>[] { ReflectionUtil.getNMSClass("NBTBase") });
		methodTagCompoundGetString = ReflectionUtil.getMethod(classNBTTagCompound, "getString", new Class<?>[] { String.class });
		
		methodTagCompoundSet = ReflectionUtil.getMethod(classNBTTagCompound, "set", new Class<?>[] { String.class, ReflectionUtil.getNMSClass("NBTBase") });
		methodTagCompoundSetInt = ReflectionUtil.getMethod(classNBTTagCompound, "setInt", new Class<?>[] { String.class, int.class });
		methodTagCompoundSetString = ReflectionUtil.getMethod(classNBTTagCompound, "setString", new Class<?>[] { String.class, String.class });
		methodTagCompoundSetDouble = ReflectionUtil.getMethod(classNBTTagCompound, "setDouble", new Class<?>[] { String.class, double.class });
	}
	
 	public static Object convertToNMSItem(ItemStack item) {
		return ReflectionUtil.createStaticObject(methodNMSCopy, item);
	}
	
	public static Object convertToBukkitItem(Object nmsItem) {
		return ReflectionUtil.createStaticObject(methodBukkitCopy, nmsItem);
	}
	
	public static Object getAttributeList(Object nbtTag) {
		Boolean result = (Boolean) ReflectionUtil.createObject(methodKeyOfType, nbtTag, "AttributeModifiers", Constants.ID_NBT_TAG_LIST);
		
		if (result) {
			return ReflectionUtil.createObject(methodGetList, nbtTag, "AttributeModifiers", Constants.ID_NBT_TAG_COMPOUND);
		}
		
		return ReflectionUtil.createObject(classNBTTagList);
	}
	
	public static Object emptyCompound() {
		return ReflectionUtil.createObject(constructorNBTTagCompound);
	}
	
	public static void removeDuplicates(Object tagList, String attributeName) {
		Field fieldInternalList = ReflectionUtil.getDeclaredField(classNBTTagList, "list");
		List<?> list = (List<?>) ReflectionUtil.createObject(fieldInternalList, tagList);
		for (int i = 0; i < list.size(); i++) {
			Object entry = list.get(i);
			String attrName = (String) ReflectionUtil.createObject(methodTagCompoundGetString, entry, "AttributeName");
			if (attrName.equals(attributeName)) {
				list.remove(i);
			}
		}
		
		ReflectionUtil.setValue(fieldInternalList, tagList, list);
	}
	
	public static void clearAttributeList(Object tagList) {
		Field fieldInternalList = ReflectionUtil.getDeclaredField(classNBTTagList, "list");
		List<?> list = (List<?>) ReflectionUtil.createObject(fieldInternalList, tagList);
		list.clear();
		ReflectionUtil.setValue(fieldInternalList, tagList, list);
	}
	
	public static void writeAttribute(Object attribute, String attributeName, double value) {
		ReflectionUtil.invokeMethod(methodTagCompoundSetInt, attribute, "UUIDMost", 0x01);
		ReflectionUtil.invokeMethod(methodTagCompoundSetInt, attribute, "UUIDLeast", 0x01);
		ReflectionUtil.invokeMethod(methodTagCompoundSetInt, attribute, "Operation", 0x00);
		ReflectionUtil.invokeMethod(methodTagCompoundSetDouble, attribute, "Amount", value);
		ReflectionUtil.invokeMethod(methodTagCompoundSetString, attribute, "Name", "CustomAttribute"); // name doesn't care
		ReflectionUtil.invokeMethod(methodTagCompoundSetString, attribute, "AttributeName", attributeName);
	}
	
	public static void addToList(Object tagList, Object nbtBase) {
		ReflectionUtil.invokeMethod(methodTagListAdd, tagList, nbtBase);
	}
	
	public static void compoundSet(Object compound, String key, Object nbtBase) {
		ReflectionUtil.invokeMethod(methodTagCompoundSet, compound, key, nbtBase);
	}
	
	public static int countTags(ItemStack item) {
		Object nmsItem = NBTReflection.convertToNMSItem(item);
		Object compound = ReflectionUtil.createObject(ReflectionUtil.getDeclaredField(classItemStack, "tag"), nmsItem);
		
		if (compound == null) {
			return 0;
		}
		
		Field fieldMap = ReflectionUtil.getDeclaredField(classNBTTagCompound, "map");
		Map<?, ?> map = (Map<?, ?>) ReflectionUtil.createObject(fieldMap, compound);
		return map.size();
	}
}
