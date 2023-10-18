package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Myfirstserver {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(1234);
        System.out.println("je suis entrain d'attendre :D :D");
        Socket s=ss.accept();
        InputStream is=s.getInputStream();
        OutputStream os=s.getOutputStream();
        String address=s.getRemoteSocketAddress().toString();
        System.out.println("je l'attend de m'envoyer u num :D "+address);
        int number=is.read();
        System.out.println("il m'a envoyé un nbr :"+number);
        int calc=number * 5;
        System.out.println("je vais envoyé la réponse "+ calc);
os.write(calc);
s.close();
    }

}
