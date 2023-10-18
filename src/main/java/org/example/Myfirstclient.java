package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Myfirstclient {
    public static void main(String[] args) throws IOException {
        System.out.println("je vais me connecter maintenant au serveur");
        Socket s=new Socket("localhost",1234);
        InputStream is=s.getInputStream();
        OutputStream os=s.getOutputStream();
        Scanner sc=new Scanner(System.in);
        System.out.println("Donne moi un num");
        int nbre=sc.nextInt();
        System.out.println("je vais envoyer au serveur le nombre"+nbre);
        os.write(nbre);
        System.out.println("j'attend le serveur de me répondre :D");
        int jawab= is.read();
        System.out.println("la réponse du serveur c'est :D"+jawab);

    }

}
