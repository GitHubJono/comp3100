    import java.io.*;  
    import java.net.*;  
    public class MyClient {  
    public static void main(String[] args) {  
    try{      
    Socket socket = new Socket("localhost", 50000);
      
    DataOutputStream dout = new DataOutputStream(socket.getOutputStream()); 
    
    BufferedReader bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
     
    dout.write(("HELO\n").getBytes());  
    
    String str = (String)bin.readLine();  
    System.out.println("message1 = "+str); 
    
    String username = System.getProperty("user.name");
    
    dout.write(("AUTH "+username+"\n").getBytes());
    
    str = (String)bin.readLine();  
    System.out.println("message2 = "+str); 
    
    dout.write(("REDY\n").getBytes());
    
    String job = (String)bin.readLine();  
    System.out.println("message3 = "+job); 
    
    dout.write(("GETS All\n").getBytes());
    
    String data = (String)bin.readLine();  
    System.out.println("message4 = "+data); 
    String[] dataInfo = data.split(" ");
    Integer totalServers = Integer.parseInt(dataInfo[1]);
    
    dout.write(("OK\n").getBytes());
    
    String biggestServer = null;
    Integer biggestCores = 0;
    String checkServer = null;
    Integer totalServersOfType = 0;
    
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
    
    String[] biggestServerPars = biggestServer.split(" ");
    String serverName = biggestServerPars[0];
    Integer serverNumber = 0;
    Integer doJobs = 0;
    
    dout.write(("OK\n").getBytes());
    
    String jobPars = (String)bin.readLine();  
    	
    String[] checkJobPars = jobPars.split(" ");
    
    if (serverNumber > totalServersOfType) {
    	serverNumber = 0;
    }	
    	
    dout.write(("SCHD "+doJobs+" "+serverName+" "+serverNumber+"\n").getBytes());
    doJobs++;
    serverNumber++;
    	
    str = (String)bin.readLine();  
    
    while(!checkJobPars[0].equals("NONE")) {
    	
    	dout.write(("REDY\n").getBytes());
    	
    	jobPars = (String)bin.readLine();  
    	
     	checkJobPars = jobPars.split(" ");
    	
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
    		continue;
    	}
    	
    	str = (String)bin.readLine();  
    }
    
    dout.write(("QUIT\n").getBytes()); 
    
    str=(String)bin.readLine();  
    
    dout.flush();  
    dout.close();  
    socket.close();  
    }catch(Exception e){System.out.println(e);}  
    }  
    }  
