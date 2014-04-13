package nl.Steffion.BlockHunt.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.permissions.PermissionDefault;

public class OnPlayerJoinEvent implements Listener
{

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if (player != null && !player.hasPermission(PermissionDefault.OP.toString()))
		{
			if(!player.getInventory().contains(Material.WRITTEN_BOOK))
			{
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
				BookMeta bm = (BookMeta) book.getItemMeta();
				bm.setTitle("��èè��Ϸ����");
				String p1 = "1.�����߻��Զ����ܾ�����,ʹ����������ʱ��ȷ���к�δ���������߶������ľ�����,��������Ϊ��,�ٴι���ʱ�ᱻ��Ѫ";
				String p2 = "2.����߾�ֹ����5��������Ʒ�����Ҳ���Ʒ��ʾ�ķ���";
				String p3 = "3.����߿���ѡ���⣬���⣬ţ������ɶ�Ӧ�Ķ��ѡ������ɱ�è";
				String p4 = "4.����߿���ʹ�������ָ�����,ͬʱ�ᷢ��è�����������ע��";
				String p5 = "5.����߿��Է����̻����������߲��һ�ü�����BUFF";
				bm.addPage(p1, p2, p3, p4, p5);
				book.setItemMeta(bm);
				player.getInventory().addItem(book);
			}
		}
	}
}
