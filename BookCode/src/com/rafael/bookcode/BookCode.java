package com.rafael.bookcode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import net.minecraft.server.v1_8_R1.Block;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.block.Action;

public class BookCode extends JavaPlugin{

	private String prgDir;
	
	private String mainDir;
	private File dir;
	
	private boolean ready = false;
	//Enter path to python here
	private String pythonPath;
	
	
	public void onEnable(){
		
	getLogger().info("BookCode is ready.");
	
	
	mainDir = System.getProperty("user.dir");
	prgDir = mainDir + "\\plugins\\programs";
	getLogger().info(mainDir);
	new File(prgDir).mkdir();
	
	dir = new File(prgDir );
	//dir.mkdir();
	
	

	this.saveDefaultConfig();
	
	if (this.getConfig().getString("pyPath").contains("python")){
		getLogger().info("THE PATH IS SET.");
		pythonPath = this.getConfig().getString("pyPath");
		getLogger().info(pythonPath);
		ready = true;
	}
	else{
		getLogger().info("THERE IS NO PATH");
		ready = false;
	}
	
	
	/*new LeverListener(this)*/
	getServer().getPluginManager().registerEvents(new LeverListener(pythonPath, dir), this);
	
	}
	

	
	
	public void onDisable(){
		
	}

}
