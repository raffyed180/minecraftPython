package com.rafael.bookcode;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class BookCode extends JavaPlugin{

	private String prgDir;
	
	private File dir;
	
	private boolean ready = false;
	//Enter path to python here
	private String pythonPath;
	
	
	public void onEnable(){
		
	getLogger().info("BookCode is ready.");
	
	
	prgDir = System.getProperty("user.dir") + "\\plugins\\programs";

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
	if(ready)
	{
	getServer().getPluginManager().registerEvents(new LeverListener(pythonPath, dir), this);
	}
	else{
		getLogger().info("Path to python not set. Edit config file");
	}
	}
	

	
	
	

	
	public void onDisable(){
		
	}

}
