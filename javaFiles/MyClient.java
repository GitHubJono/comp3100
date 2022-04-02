    import java.io.*;  
    import java.net.*;  
    public class MyClient {  
    public static void main(String[] args) {  
    try{      
    Socket socket = new Socket("localhost", 50000);
      
    DataOutputStream dout=new DataOutputStream(socket.getOutputStream()); 
    
    BufferedReader bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
     
    dout.write(("HELO\n").getBytes());  
    
    String str = (String)bin.readLine();  
    System.out.println("message = "+str); 
    
    String username = System.getProperty("user.name");
    
    dout.write(("AUTH "+username+"\n").getBytes());
    
    str = (String)bin.readLine();  
    System.out.println("message = "+str); 
    
    dout.write(("REDY\n").getBytes());
    
    String job = (String)bin.readLine();  
    System.out.println("message = "+job); 
    
    dout.write(("GETS All\n").getBytes());
    
    String data = (String)bin.readLine();  
    System.out.println("message = "+data); 
    String[] dataInfo = data.split(" ");
    Integer totalServers = Integer.parseInt(dataInfo[1]);
    
    dout.write(("OK\n").getBytes());
    
    String biggestServer = null;
    Integer biggestCores = 0;
    String checkServer = null;
    
    for (Integer i = 0; i < totalServers; i++) {
    	checkServer = bin.readLine();;
    	System.out.println(checkServer);
    	String[] checkServerPars = checkServer.split(" ");
    	System.out.println("checking: "+Integer.parseInt(checkServerPars[4]));
    	if (Integer.parseInt(checkServerPars[4]) >= biggestCores | biggestServer == null) {
    		biggestServer = checkServer;
    	}
    }
    
    System.out.println("done "+biggestServer);
    
    String[] biggestServerPars = biggestServer.split(" ");
    String serverName = biggestServerPars[0];
    Integer totalServersOfType = Integer.parseInt(biggestServerPars[1]);
    Integer serverNumber = 0;
    
    dout.write(("OK\n").getBytes());
    
    //dout.write(("SCHD 0 "+serverName+" "+serverNumber+"\n").getBytes());
    
    //str = (String)bin.readLine();  
    //System.out.println("message = "+str); 
    
    Integer doJobs = 0;
    
    String jobPars = (String)bin.readLine();  
    System.out.println("What to do: "+jobPars); 
    	
    String[] checkJobPars = jobPars.split(" ");
    
    while(!checkJobPars[0].equals("NONE")) {
    	
    	dout.write(("REDY\n").getBytes());
    	
    	jobPars = (String)bin.readLine();  
    	System.out.println("What to do: "+jobPars); 
    	
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
    	System.out.println("message = "+str);
    }
    
    dout.write(("QUIT\n").getBytes()); 
    
    str=(String)bin.readLine();  
    System.out.println("message = "+str); 
    
    
    dout.flush();  
    dout.close();  
    socket.close();  
    }catch(Exception e){System.out.println(e);}  
    }  
    }  
