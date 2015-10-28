package com.rafael.bookcode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BookCode extends JavaPlugin{

	private Player player;
	private BookMeta book;
	private String pyName;
	
	//Enter path to python here
	private String pythonPath = "/Python27/python" ;
	
	//private String programPath = "/Users/Rafael/Spigot/ServerSpigot/" ;
	
	public void onEnable(){
	getLogger().info("BookCode is ready.");
	
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	    
		if(cmd.getName().equalsIgnoreCase("run")){
	    	
	    	if (sender instanceof Player)
	    	{
	    	
	    	player = (Player) sender;	
	    	ItemMeta item	= player.getItemInHand().getItemMeta();
	    	
	    	// Checks if the held item is a book. If not, prints a method
		    	if (item instanceof BookMeta)
		    	{
			    	 book = (BookMeta) item;
			    	 String title = book.getTitle();
			    	 List<String> pages = book.getPages();
			    	 
			    	  //Makes a python file and writes in it.
			    	 try 
			    	 {
				    	 PrintWriter writer = new PrintWriter(title + ".py");
				    	 //sender.sendMessage("title " + title );
				    	 
						for (String page : pages)
						{
							Scanner in = new Scanner(page);
							
					    	while (in.hasNextLine())
					    	{
					    		String line = in.nextLine(); ////Scan each line in the book
								writer.println(line);		///writes the scanned line in the python file 
								//sender.sendMessage(line );	///This just prints the lines scanned
					    	}
					    	
					    	in.close();
					    }
						
						
						writer.close();
						
						
								//ProcessBuilder pb = new ProcessBuilder(pythonPath, programPath + title + ".py");
								//ProcessBuilder pb = new ProcessBuilder("/Python27/python", "/Users/Rafael/DIRT/ServerSpigot" + title + ".py");
								//Process p = Runtime.getRuntime().exec("python "+ title+".py");
								//ProcessBuilder pb = new ProcessBuilder("/Python27/python", "/Users/Rafael/Spigot/ServerSpigot/" + title + ".py");
								
						ProcessBuilder pb = new ProcessBuilder(pythonPath,  title + ".py");
						
						Process p = pb.start();
			    	 } 
				    	catch (IOException e) 
			    	 	{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	 	}
				    else
				    {
				    	sender.sendMessage("Thats not a book.");
				    }
	    	}
		    else
		    {
		    	sender.sendMessage("Somehow you are not a player?");}
		    }
	    
	    if(cmd.getName().equalsIgnoreCase("testcom")){
	        sender.sendMessage("Testing commands 123 123");
	    }
	       return false;
	}
	
	

	public void onDisable(){
		
	}

}
