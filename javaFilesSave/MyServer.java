    import java.io.*;  
    import java.net.*;  
    public class MyServer {  
    public static void main(String[] args){  
    try{  
    ServerSocket ss=new ServerSocket(6666);  
    Socket s=ss.accept();//establishes connection  
    DataOutputStream dout=new
DataOutputStream(s.getOutputStream()); 
 
    DataInputStream dis=new DataInputStream(s.getInputStream()); 
     
    String  str=(String)dis.readUTF();  
    System.out.println("message= "+str);  
    
    dout.writeUTF("G'DAY");
    
    str=(String)dis.readUTF();  
    System.out.println("message= "+str); 
    
    dout.writeUTF("BYE"); 
    
    dout.flush();  
    dout.close();  
    
    ss.close();  
    }catch(Exception e){System.out.println(e);}  
    }  
    }  
