package com.rafael.bookcode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class LeverListener implements Listener{
	private BookCode plugin;
	
	/*public LeverListener(BookCode plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}*/
	public void chestReader(Chest c)
	{
		Bukkit.broadcastMessage("Initiating chestReader");
		
		 Inventory chest = c.getBlockInventory();
		 if(chest.contains(Material.WRITTEN_BOOK) || chest.contains(Material.BOOK) || chest.contains(Material.BOOK_AND_QUILL))
		 {
			 ItemStack[] stuff = chest.getContents();
			 for(int i =0; i<stuff.length; i++)
			 {
				 if (stuff[i].getItemMeta() instanceof BookMeta){
					 BookMeta b = (BookMeta) stuff[i].getItemMeta();
					 Bukkit.broadcastMessage("There is a book called: "+ b.getTitle());
					 
				 }
			 }
		 }
		 else
		 {
			 Bukkit.broadcastMessage("There are no books");
		 }
		
	}
	@EventHandler
	public void onSwitch( BlockRedstoneEvent event ){
		
		
		/*if (event.getClickedBlock().getType() == Material.CHEST){
			event.getPlayer().sendMessage("Can get chest" );
		}
		
		if (event.getClickedBlock().getType() == Material.CHEST && event.getClickedBlock().isBlockIndirectlyPowered()){
			event.getPlayer().sendMessage("Chest can be powered" );
			
			Chest c = (Chest) event.getClickedBlock();
			event.getPlayer().sendMessage("Inventory" + c.getBlockInventory());
		}*/
		
		if (event.getBlock().getType().equals(Material.LEVER))
		{
			Block lever = event.getBlock();
			if (lever.isBlockIndirectlyPowered() && lever.getRelative(BlockFace.NORTH).getType().equals(Material.LAPIS_BLOCK) && lever.getRelative(0, 1, -1).getType().equals(Material.CHEST))
			{
				Bukkit.broadcastMessage("Looking north");
				
				if (lever.getRelative(0, 1, -1).getState() instanceof Chest)
				{
				Chest c = (Chest) lever.getRelative(0, 1, -1).getState();
				chestReader(c);
				}
				else{Bukkit.broadcastMessage("Casting Failed");}
			}
			
			if (lever.isBlockIndirectlyPowered() && lever.getRelative(BlockFace.SOUTH).getType().equals(Material.LAPIS_BLOCK) && lever.getRelative(0, 1, 1).getType().equals(Material.CHEST))
			{
				Bukkit.broadcastMessage("Looking south");
				if (lever.getRelative(0, 1, -1).getState() instanceof Chest)
				{
				Chest c = (Chest) lever.getRelative(0, 1, -1).getState();
				chestReader(c);
				}
				else{Bukkit.broadcastMessage("Casting Failed");}
			}
			
			if (lever.isBlockIndirectlyPowered() && lever.getRelative(BlockFace.EAST).getType().equals(Material.LAPIS_BLOCK) && lever.getRelative(1, 1, 0).getType().equals(Material.CHEST))
			{
				Bukkit.broadcastMessage("Looking east");
				if (lever.getRelative(0, 1, -1).getState() instanceof Chest)
				{
				Chest c = (Chest) lever.getRelative(0, 1, -1).getState();
				chestReader(c);
				}
				else{Bukkit.broadcastMessage("Casting Failed");}
			}
			
			if (lever.isBlockIndirectlyPowered() && lever.getRelative(BlockFace.WEST).getType().equals(Material.LAPIS_BLOCK)&& lever.getRelative(-1, 1, 0).getType().equals(Material.CHEST))
			{
				Bukkit.broadcastMessage("Looking west");
				if (lever.getRelative(0, 1, -1).getState() instanceof Chest)
				{
				Chest c = (Chest) lever.getRelative(0, 1, -1).getState();
				chestReader(c);
				}
				else{Bukkit.broadcastMessage("Casting Failed");}
			}
		
		}
		
		//if(event.getClickedBlock().getType().equals(Material.CHEST) && event.getClickedBlock().isBlockIndirectlyPowered())
		
		
		
	}
}