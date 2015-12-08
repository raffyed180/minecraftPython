package com.rafael.bookcode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.material.Lever;


public class LeverListener implements Listener{
	//private BookCode plugin;
	
	private String pythonPath;
	//private String prgDir;
	
	//private String mainDir;
	private File prgDirectory;
	
	public LeverListener(String p, File dir){
		pythonPath = p;
		prgDirectory = dir;
	}
	
	/**
	 * This method will read any book within a chest and write the text into a python file. Then the python file will be executed.
	 * @param c: The chest holding the books.
	 */
	public void chestReader(Chest c)
	{
		Bukkit.broadcastMessage("Looking through chest");
		
		 Inventory chest = c.getBlockInventory();
		 
		 //Checks if the chest has any books
		 if(chest.contains(Material.WRITTEN_BOOK) || chest.contains(Material.BOOK) || chest.contains(Material.BOOK_AND_QUILL))
		 {
			 ItemStack[] content = chest.getContents();
			 for (ItemStack stack : content)
			 {
				if ( stack!=null && stack.getItemMeta() instanceof BookMeta)
				 {
						 
					BookMeta book = (BookMeta) stack.getItemMeta();
					String title = book.getTitle();
					List<String> pages = book.getPages();
					
					Bukkit.broadcastMessage("There is a book called: "+ title);
				   	  
					//Makes a python file and writes in it.
				   	 try 
				   	 {
				   		File pyfile = new File(prgDirectory, title + ".py");
				   		BufferedWriter writer = new BufferedWriter(new FileWriter(pyfile));
				   		 
					    for (String page : pages)
						{
							Scanner in = new Scanner(page);
								
						    while (in.hasNextLine())
						    {
						    	String line = in.nextLine(); ////Scan each line in the book
						    	writer.write(line +"\n");
						    }
						    	
						    in.close();
						}
						
					    writer.close();
						
					    //Executes the python file
						ProcessBuilder pb = new ProcessBuilder(pythonPath,  title + ".py");
						pb.directory(prgDirectory);
						Process p = pb.start();
						
				   	 } 
					 
				   	 catch (IOException e) 
				   	 {
						// TODO Auto-generated catch block
						e.printStackTrace();
				   	 }
				}
			 }
		 }
		 else
		 {
			 Bukkit.broadcastMessage("There are no books");
		 }

	}
	
	/**
	 * This method is set up so when a lever is attached to a lapis block, 
	 * and there is a chest on top of the block, the method chestReader will run.
	 * @param event: This parameter is an event, called upon changes in redstone currents. This method loocks for the event of a lever being switched 
	 */
	@EventHandler
	public void onSwitch( BlockRedstoneEvent event ){
		
		if (event.getBlock().getType().equals(Material.LEVER))
		{
			
			Block lever = event.getBlock();
			Lever check = (Lever) event.getBlock().getState().getData();
			
			if (check.isPowered())
			{
				//If the lever is on the south side of the lapis block
				if(check.getAttachedFace().equals(BlockFace.NORTH) && lever.getRelative(BlockFace.NORTH).getType().equals(Material.LAPIS_BLOCK) && lever.getRelative(0, 1, -1).getType().equals(Material.CHEST))
				{
					//Bukkit.broadcastMessage("Lever on south side of the block");
					
					if (lever.getRelative(0, 1, -1).getState() instanceof Chest)
					{
					Chest c = (Chest) lever.getRelative(0, 1, -1).getState();
					chestReader(c);
					}
				}
				
				//If the lever is on the north side of the lapis block
				if(check.getAttachedFace().equals(BlockFace.SOUTH) && lever.getRelative(BlockFace.SOUTH).getType().equals(Material.LAPIS_BLOCK) && lever.getRelative(0, 1, 1).getType().equals(Material.CHEST))
				{
					//Bukkit.broadcastMessage("Lever on north side of the block");
					if (lever.getRelative(0, 1, 1).getState() instanceof Chest)
					{
					Chest c = (Chest) lever.getRelative(0, 1, 1).getState();
					chestReader(c);
					}
				}
				
				//If the lever is on the west side of the lapis block
				if(check.getAttachedFace().equals(BlockFace.EAST) && lever.getRelative(BlockFace.EAST).getType().equals(Material.LAPIS_BLOCK) && lever.getRelative(1, 1, 0).getType().equals(Material.CHEST))
				{
					//Bukkit.broadcastMessage("Lever on west side of the block");
					if (lever.getRelative(1, 1, 0).getState() instanceof Chest)
					{
					Chest c = (Chest) lever.getRelative(1, 1, 0).getState();
					chestReader(c);
					}
				}
				
				//If the lever is on the east side of the lapis block
				if(check.getAttachedFace().equals(BlockFace.WEST) && lever.getRelative(BlockFace.WEST).getType().equals(Material.LAPIS_BLOCK) && lever.getRelative(-1, 1, 0).getType().equals(Material.CHEST))
				{
					//Bukkit.broadcastMessage("Lever on east side of the block");
					if (lever.getRelative(-1, 1, 0).getState() instanceof Chest)
					{
					Chest c = (Chest) lever.getRelative(-1, 1, 0).getState();
					chestReader(c);
					}
				}
			}
		
		}
		
		
		
	}
}
