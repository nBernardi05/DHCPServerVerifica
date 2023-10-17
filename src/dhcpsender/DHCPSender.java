package dhcpsender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DHCPSender {

    public static void main(String[] args) {
        try {
            DatagramSocket socket=new DatagramSocket();
            byte[] option={99,(byte)130,83,99,53,1,5,1,4,(byte)255,(byte)255,0,0,3,4,(byte)172,16,(byte)255,(byte)245,6,8,8,8,8,8,8,8,4,4,51,4,0,10,1,100,54,4,(byte)172,16,(byte)255,(byte)253,(byte)255};
            byte[] fix={2,1,6,0,0,0,30,97,0,0,(byte)255,(byte)255,0,0,0,0,(byte)172,16,(byte)255,2,0,0,0,0,0,0,0,0,(byte)254,(byte)237,(byte)220,(byte)203,(byte)186,(byte)169,0,0,0,0,0,0,0,0,0,0,108,101,118,105,46,101,100,117};
            byte[] invio=new byte[236+option.length];
            System.arraycopy(fix, 0, invio, 0, fix.length);
            Arrays.fill(invio, fix.length, 235, (byte)0); 
            System.arraycopy(option, 0, invio, 236, option.length);            
            DatagramPacket dp;
            try {
                dp = new DatagramPacket(invio,invio.length,InetAddress.getByName("localhost"),52000);       // Se fosse un vero pacchetto dhcp avrebbe porta 67
                try {
                    socket.send(dp);
                } catch (IOException ex) {
                    Logger.getLogger(DHCPSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(DHCPSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SocketException ex) {
            Logger.getLogger(DHCPSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
