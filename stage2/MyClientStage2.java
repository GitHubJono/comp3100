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
    String jobPars = (String)bin.readLine(); 

    str = (String)bin.readLine(); 
    Integer doJobs = 0; 
    
    //Loops through necessary requests
    while(!jobPars[0].equals("NONE")) {
    	
    	dout.write(("REDY\n").getBytes());
    	
    	job = (String)bin.readLine();  
     	jobPars = job.split(" ");
    	
    	//If there is a job, schedule it
    	if (jobPars[0].equals("JOBN")) {
    	
    		String bestServerParameters = jobPars[4]+" "+jobPars[5]+" "+jobPars[6]
    		
    		dout.write(("GETS Avail "+bestServerParameters+"\n").getBytes());
    		
    		//splits server information into relevant parameters
    		String serverData = (String)bin.readLine();
    		String[] serverDataPars = serverData.split(" ");
    		Integer totalServers = Integer.parseInt(serverDataPars[1]);
    		
    		dout.write(("OK\n").getBytes());
    		
    		String selectedServer = "";
    		
    		for (int i = 0; i < totalServers; i++) {
    			checkServer = bin.readLine();
    			if (i == 0) {
    				selectedServer = checkServer;
    			}
    		}
    		
    		String[] selectedServerPars = selectedServer.split(" ");
    		String serverName = selectedServerPars[0];
    		String serverNumber = selectedServerPars[1];
    		
    		if (totalServers == 0) {
    		
    			str = (String)bin.readLine(); 
    			dout.write(("REDY\n").getBytes());
    			job = (String)bin.readLine();  
    			
    			dout.write(("GETS Capable "+bestServerParameters+"\n").getBytes());
    		
    			//splits server information into relevant parameters
    			serverData = (String)bin.readLine();
    			serverDataPars = serverData.split(" ");
    			totalServers = Integer.parseInt(serverDataPars[1]);
    		
    			dout.write(("OK\n").getBytes());
    		
    			selectedServer = "";
    		
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
    	
    		dout.write(("SCHD "+doJobs+" "+serverName+" "+serverNumber+"\n").getBytes());
    		doJobs++;
    		
    	}
    	else
    	{
    		//otherwise continue on with the next request
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
