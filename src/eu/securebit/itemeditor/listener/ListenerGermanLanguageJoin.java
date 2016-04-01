package eu.securebit.itemeditor.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.config.Strings;

public class ListenerGermanLanguageJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.isOp() && !player.hasPermission("ie.admin")) {
			return;
		}
		
		if (Strings.getLanguage().equals("de")) {
			Main.layout().message(player, "*Hinweis*: Der deutsche Sprachmodus ist aktiv."
					+ " Bitte beachte, dass die Übersetzung zurzeit unvollständig ist.");
		}
	}
}
