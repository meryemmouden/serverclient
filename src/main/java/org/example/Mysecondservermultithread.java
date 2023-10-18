package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Mysecondservermultithread extends Thread {
    public int clientnbr;
    public static void main(String[] args) {

        new Mysecondservermultithread().start();
    }
    public void run(){
        try{
            ServerSocket ss=new ServerSocket(1234);
            System.out.println("le serveur essaie de démarrer ...");
while (true){
    Socket s=ss.accept();
    ++clientnbr;
    new  Communication(s,clientnbr).start();
}
        }catch (IOException e){
            e.printStackTrace();
        }
    }
public class Communication extends Thread{
private Socket s;
private int clientnbbr;
Communication(Socket s, int clientnbre){
    this.s=s;
this.clientnbbr=clientnbre;
}
@Override
    public void run(){
    try{
        InputStream is=s.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader br=new BufferedReader(isr);
        OutputStream os=s.getOutputStream();
       String IP= s.getRemoteSocketAddress().toString();
        System.out.println("le client numéro "+clientnbbr+"et son IP"+IP);
        PrintWriter pw=new PrintWriter(os,true);
        pw.println("vous etes le client"+clientnbbr);
        while (true){
            String UserRequest= br.readLine();
            pw.println("la taille de votre chaine de caractére est :"+UserRequest.length());
        }
    }catch(IOException e){
        throw new RuntimeException(e);
    }
}
}
}
