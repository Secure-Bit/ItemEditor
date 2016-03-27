package lib.securebit.itemeditor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import net.md_5.bungee.api.ChatColor;

public class InfoLayout {
	
	public static String replaceKeys(String text) {
		text = text.replace("+", "$1");
		text = text.replace("-", "$2");
		text = text.replace("*", "$3");
		
		return text;
	}
	
	public static String undoKeys(String text) {
		text = text.replace("$1", "+");
		text = text.replace("$2", "-");
		text = text.replace("$3", "*");
		
		return text;
	}
	
	public boolean uncolorConsole;
	
	public String colorPrimary;
	public String colorSecondary;
	public String colorImportant;
	public String colorPositive;
	public String colorNegative;
	public String prefix;
	
	protected List<String> transaction;
	
	public InfoLayout() {
		this("");
	}
	
	public InfoLayout(String prefix) {
		this.colorPrimary = "§8";
		this.colorSecondary = "§6";
		this.colorImportant = "§e";
		this.colorPositive = "§a";
		this.colorNegative = "§c";
		this.uncolorConsole = false;
		this.createPrefix(prefix);
		
		this.transaction = new ArrayList<String>();
	}
	
	public InfoLayout clone() {
		InfoLayout clone = new InfoLayout();
		clone.colorPrimary = this.colorPrimary;
		clone.colorSecondary = this.colorSecondary;
		clone.colorImportant = this.colorImportant;
		clone.colorPositive = this.colorPositive;
		clone.colorNegative = this.colorNegative;
		clone.uncolorConsole = this.uncolorConsole;
		clone.prefix = this.prefix;
		
		return clone;
	}
	
	public void createPrefix(String prefix) {
		this.prefix = this.colorPrimary + "[" + this.colorSecondary + prefix + this.colorPrimary + "] " + this.colorSecondary;
	}
	
	public void message(CommandSender sender, String text) {
		String msg = this.format("\\pre" + text);
		
		if (this.uncolorConsole && sender instanceof ConsoleCommandSender) {
			sender.sendMessage(ChatColor.stripColor(msg));
		} else {
			sender.sendMessage(msg);
		}
	}
	
	public void broadcast(String text) {
		Bukkit.broadcastMessage(this.format("\\pre" + text));
	}

	public void begin() {
		this.transaction.clear();
	}

	public void commit(CommandSender cs) {
		while (this.transaction.size() > 0) {
			String msg = this.format(this.transaction.remove(0)); // FIFO (first in, first out)
			
			if (this.uncolorConsole && cs instanceof ConsoleCommandSender) {
				cs.sendMessage(ChatColor.stripColor(msg));
			} else {
				cs.sendMessage(msg);
			}
		}
	}

	public void rollback() {
		this.transaction.clear();
	}
	
	public void category(String name) {
		this.barrier();
		this.line("*" + name + "*");
	}
	
	public void barrier() {
		this.barrier(39);
	}
	
	public void barrier(int count) {
		String barrier = "";
		
		for (int i = 0; i < count; i++) {
			barrier = barrier + "=";
		}
		
		this.line(this.colorPrimary + barrier);
	}
	
	public void line(String text) {
		this.transaction.add("\\pre" + text);
	}
	
	public void suggestion(String commandLine, String description) {
		if (description != null) {
			description = this.format(description);
		}
		
		this.line("*/" + commandLine + (description == null || description.isEmpty() || description.trim().isEmpty() ? "" :
			this.colorPrimary + "* $-$- " + description));
	}


	public void prefix(String newPrefix) {
		this.prefix = newPrefix;
	}

	public boolean hasPrefix() {
		return this.prefix != null && !this.prefix.isEmpty();
	}

	public String prefix() {
		return this.prefix;
	}
	
	public String format(String text) {
		text = this.colorSecondary + text;
		
		text = text.replace("$+", "$1");
		text = text.replace("$-", "$2");
		text = text.replace("$*", "$3");
		
		return this.format(text, 0);
	}
	
	private String format(String text, int status) {
		if (text.contains("+")) {
			int index = text.indexOf("+");
			
			if (status == 0) {
				return this.format(this.replace(text, index, this.colorPositive), 1);
			} else if (status == 1) {
				return this.format(this.replace(text, index, this.colorSecondary), 0);
			}
		}
		
		if (text.contains("-")) {
			int index = text.indexOf("-");
			
			if (status == 0) {
				return this.format(this.replace(text, index, this.colorNegative), 2);
			} else if (status == 2) {
				return this.format(this.replace(text, index, this.colorSecondary), 0);
			}
		}
		
		if (text.contains("*")) {
			int index = text.indexOf("*");
			
			if (status == 0) {
				return this.format(this.replace(text, index, this.colorImportant), 3);
			} else if (status == 3) {
				return this.format(this.replace(text, index, this.colorSecondary), 0);
			}
		}
		
		if (status != 0) {
			String s = "";
			
			if (status == 1) {
				s = "+";
			} else if (status == 2) {
				s = "-";
			} else if (status == 3) {
				s = "*";
			} else {
				s = null;
			}
			
			if (s != null) {
				throw new FormatException("The statment '" + s + "' is uncomplet in '" + text + "'!");
			} else {
				throw new FormatException("An expected error!");
			}
		}
		
		text = text.replace("$1", "+");
		text = text.replace("$2", "-");
		text = text.replace("$3", "*");
		text = text.replace("\\pre", this.prefix);
		text = text.replace("\\r", this.colorSecondary);
		return text;
	}
	
	private String replace(String string, int index, String replace) {
		return string.substring(0, index) + replace + string.substring(index + 1);
	}
	
	public static class FormatException extends RuntimeException {
		
		private static final long serialVersionUID = 6253342509633519622L;

		public FormatException(String string) {
			super(string);
		}
		
	}
	
}
