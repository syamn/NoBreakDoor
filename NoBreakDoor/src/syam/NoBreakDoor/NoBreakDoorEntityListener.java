package syam.NoBreakDoor;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;

public class NoBreakDoorEntityListener implements Listener {
	public final static Logger log = NoBreakDoor.log;
	private static final String logPrefix = NoBreakDoor.logPrefix;

	@SuppressWarnings("unused")
	private final NoBreakDoor plugin;

	public NoBreakDoorEntityListener(NoBreakDoor plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityBreakDoor(EntityBreakDoorEvent event){

		if (NoBreakDoor.verbose){
			log.info(logPrefix + "Catch try to break door at " + event.getBlock().getWorld().getName() + ":" +
					  event.getBlock().getX() + "," + event.getBlock().getY() + "," + event.getBlock().getZ());
		}

		// 無視ワールドリストが空の場合は無条件で破壊をキャンセルする
		if (NoBreakDoor.ignoreWorlds.isEmpty() ||
				NoBreakDoor.ignoreWorlds == null){
			if (NoBreakDoor.verbose){ log.info(logPrefix+"Ignore world is Empty! Prevent break door!"); }
			event.setCancelled(true);
			return;
		}

		// 無視ワールドリストに入っている場合は無視する
		for(int i = 0; i < NoBreakDoor.ignoreWorlds.size(); ++i){
			if(Bukkit.getWorld(NoBreakDoor.ignoreWorlds.get(i)) == event.getBlock().getWorld()){
				if (NoBreakDoor.verbose){ log.info(logPrefix+"This world is ignore on config file!"); }
				return;
			}
		}
		if (NoBreakDoor.verbose){ log.info(logPrefix+"Prevent break door!"); }
		event.setCancelled(true);
	}

}
