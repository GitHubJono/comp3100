    import java.io.*;  
    import java.net.*;  
    public class MyClient {  
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
    
    //Gets server information
    dout.write(("GETS Capable\n").getBytes());
    
    //Splits server information into relevant parameters
    String data = (String)bin.readLine();  
    String[] dataInfo = data.split(" ");
    Integer totalServers = Integer.parseInt(dataInfo[1]);
    
    dout.write(("OK\n").getBytes());
    
    String biggestServer = null;
    Integer biggestCores = 0;
    String checkServer = null;
    Integer totalServersOfType = 0;
    
    //Finds server with largest cores
    for (Integer i = 0; i < totalServers; i++) {
    
    	checkServer = bin.readLine();
    	String[] checkServerPars = checkServer.split(" ");
    	
    	if (Integer.parseInt(checkServerPars[4]) > biggestCores | biggestServer == null) {
    		biggestServer = checkServer;
    		biggestCores = Integer.parseInt(checkServerPars[4]);
    	}
    	
    	String[] biggestServerPars = biggestServer.split(" ");
    	String serverName = biggestServerPars[0];
    	
    	if (checkServerPars[0].equals(serverName)) {
    		totalServersOfType = Integer.parseInt(checkServerPars[1]);
    	}
    }
    
    //Splits relevant parameters of largest server
    String[] biggestServerPars = biggestServer.split(" ");
    String serverName = biggestServerPars[0];
    Integer serverNumber = 0;
    Integer doJobs = 0;
    
    dout.write(("OK\n").getBytes());
    
    String jobPars = (String)bin.readLine();  
    
    //Does first job
    String[] checkJobPars = jobPars.split(" ");
    
    if (serverNumber > totalServersOfType) {
    	serverNumber = 0;
    }	
    	
    dout.write(("SCHD "+doJobs+" "+serverName+" "+serverNumber+"\n").getBytes());
    doJobs++;
    serverNumber++;
    	
    str = (String)bin.readLine();  
    
    //Loops through necessary requests
    while(!checkJobPars[0].equals("NONE")) {
    	
    	dout.write(("REDY\n").getBytes());
    	
    	jobPars = (String)bin.readLine();  
    	
     	checkJobPars = jobPars.split(" ");
    	
    	//If there is a job, schedule it
    	if (checkJobPars[0].equals("JOBN")) {
    	
    		if (serverNumber > totalServersOfType) {
    			serverNumber = 0;
    		}
    	
    		dout.write(("SCHD "+doJobs+" "+serverName+" "+serverNumber+"\n").getBytes());
    		doJobs++;
    		serverNumber++;
    	}
    	else
    	{
    		//otherwise continue on with the next request
    		continue;
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
