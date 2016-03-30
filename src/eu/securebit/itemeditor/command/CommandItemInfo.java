package eu.securebit.itemeditor.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.util.NBTReflection;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.UnargumentedCommand;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public class CommandItemInfo extends UnargumentedCommand {

	public CommandItemInfo() {
		super("iteminfo", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setUsage("/iteminfo [name]");
		this.setOnlyPlayers(true);
		this.setPermission("ie.iteminfo");
		this.setAliases("iinfo", "iteminformation", "itemi");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		ItemStack item;
		
		if (args.length == 1) {
			String materialName = args[1].toLowerCase().replace("minecraft:", "");
			Material material = Material.valueOf(materialName.toUpperCase());
			if (material == null) {
				Main.layout().message(player, "-Cannot find material '" + args[1] + "'.-");
				return true;
			}
			
			item = new ItemStack(material);
		} else {
			item = player.getItemInHand();
			if (item == null || item.getType() == Material.AIR) {
				Main.layout().message(player, "-You have to old a valid item in your hand.-");
				return true;
			}
		}
		
		InfoLayout layout = Main.layout().clone();
		layout.begin();
		layout.line("*Material:* " + item.getType().name());
		layout.line("*Item$-ID:* " + item.getTypeId());
		layout.line("*Item$-Data:* " + item.getData().getData());
		layout.line("*Amount:* " + item.getAmount());
		
		if (item.getItemMeta().hasDisplayName()) {
			layout.line("*Display$-Name:* " + item.getItemMeta().getDisplayName());
		}
		
		if (item.getItemMeta().hasLore()) {
			layout.line("*Size of lore:* " + item.getItemMeta().getLore().size());
		}
		
		layout.line("*Enchantments:* " + item.getEnchantments().size());
		layout.line("*NBT$-Tags:* " + NBTReflection.countTags(item));
		layout.commit(player);
		
		return true;
	}

}
