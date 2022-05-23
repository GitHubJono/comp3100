    import java.io.*;  
    import java.net.*;  
    public class MyClientStage2 {  
    public static void main(String[] args) {  
    try{   
    
    //Creates relevant instances of a socket, data output stream and buffered reader  
    Socket socket = new Socket("localhost", 50000);
    DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
    BufferedReader bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
    //Establishes HELO
    dout.write(("HELO\n").getBytes());  
    String str = (String)bin.readLine();  
    
    //Gets System Username
    String username = System.getProperty("user.name");
    
    //Establishes authentication
    dout.write(("AUTH "+username+"\n").getBytes());
    str = (String)bin.readLine();  
    
    //Is ready
    dout.write(("REDY\n").getBytes());
    
    //Gets first job
    String job = (String)bin.readLine();  
    String jobPars[] = job.split(" ");
    String selectedServer = "";
    String checkServer = "";
    String[] selectedServerPars;
    String serverName = "";
    String serverNumber = "";
    
    dout.write(("OK\n").getBytes());

    str = (String)bin.readLine(); 
    str = (String)bin.readLine(); 
    Integer doJobs = 0; 
    
    
    //Loops through necessary requests
    while(!jobPars[0].equals("NONE")) {
    
    	System.out.println("Ready for next object");
    	
    	dout.write(("REDY\n").getBytes());
    	
    	job = (String)bin.readLine();  
     	jobPars = job.split(" ");
     	
    	
    	//If there is a job, schedule it
    	if (jobPars[0].equals("JOBN")) {
    	
    		//str = (String)bin.readLine(); 
    	
    		String bestServerParameters = jobPars[4]+" "+jobPars[5]+" "+jobPars[6];
    		
    		System.out.println("Finding available servers...");
    		
    		dout.write(("GETS Avail "+bestServerParameters+"\n").getBytes());
    		
    		//splits server information into relevant parameters
    		String serverData = (String)bin.readLine();
    		
    		System.out.println(serverData);
    		
    		String[] serverDataPars = serverData.split(" ");
    		Integer totalServers = Integer.parseInt(serverDataPars[1]);
    		
    		System.out.println("Sending OK 1");
    		
    		dout.write(("OK\n").getBytes());
    		
    		
    		
    		if (totalServers == 0) {
    		
    			System.out.println("Ready for next object 2");	
    		
    			str = (String)bin.readLine(); 
    			dout.write(("REDY\n").getBytes());
    			job = (String)bin.readLine();  
    			
    			System.out.println("Finding capable servers...");
    			
    			dout.write(("GETS Capable "+bestServerParameters+"\n").getBytes());
    		
    			//splits server information into relevant parameters
    			serverData = (String)bin.readLine();
    			serverDataPars = serverData.split(" ");
    			totalServers = Integer.parseInt(serverDataPars[1]);
    			
    			System.out.println("Sending OK 2");
    		
    			dout.write(("OK\n").getBytes());
    		
    			selectedServer = "";
    			checkServer = "";
    		
    			for (int i = 0; i < totalServers; i++) {
    				checkServer = bin.readLine();
    				if (i == 0) {
    					selectedServer = checkServer;
    				}
    			}
    		
    			selectedServerPars = selectedServer.split(" ");
    			serverName = selectedServerPars[0];
    			serverNumber = selectedServerPars[1];
    			 
    		}
    		else
    		{ 
			selectedServer = "";
    			checkServer = "";
    		
    			for (int i = 0; i < totalServers; i++) {
    				checkServer = bin.readLine();
    				if (i == 0) {
    					selectedServer = checkServer;
    				}
    			}
    		
    			selectedServerPars = selectedServer.split(" ");
    			serverName = selectedServerPars[0];
    			serverNumber = selectedServerPars[1];
		}
    		
    		System.out.println("Sending OK 3");
    		
    		dout.write(("OK\n").getBytes());
    		str = (String)bin.readLine();
    		
    		System.out.println("Scheduling job..."); 
    	
    		dout.write(("SCHD "+doJobs+" "+serverName+" "+serverNumber+"\n").getBytes());
    		doJobs++;
    		
    	}
    	else
    	{
    		//otherwise continue on with the next request
    		System.out.println("Sending OK 4");
    		
    		dout.write(("OK\n").getBytes());
    		str = (String)bin.readLine();  
    	}
    	
    	str = (String)bin.readLine();  
    }
    
    //Quit
    dout.write(("QUIT\n").getBytes()); 
    
    str=(String)bin.readLine();  
    
    //Close everything
    dout.flush();  
    dout.close();  
    socket.close();  
    }catch(Exception e){System.out.println(e);}  
    }  
    }  
