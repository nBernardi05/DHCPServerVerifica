package dhcpsender;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author bernardi.nicola
 */
public class MsgBernardiNicola {
    byte op, htype, hlen, hops;
    int xid;
    short sec, flags;
    String CIAddr="", YIAddr="", SIAddr="", GIAddr="", CHAddr="", SName="", BFN="";
    byte[] opt;

    public MsgBernardiNicola(byte [] arr) {
        ByteBuffer bb = ByteBuffer.wrap(arr);
        bb.position(0);
        
        op = (byte)(bb.get()&0xff);
        htype = (byte)(bb.get()&0xff);
        hlen = (byte)(bb.get()&0xff);
        hops = (byte)(bb.get()&0xff);
        
        xid = bb.getInt() & 0xffffffff;
        
        sec = (short)(bb.getShort());
        flags = (short)(bb.getShort());
        
        for(int i=0; i<4; i++){
            CIAddr += bb.get()&0xff;
            if(i!=3)
                CIAddr += ".";
        }
        for(int i=0; i<4; i++){
            YIAddr += bb.get()&0xff;
            if(i!=3)
                YIAddr += ".";
        }
        for(int i=0; i<4; i++){
            SIAddr += bb.get()&0xff;
            if(i!=3)
                SIAddr += ".";
        }
        for(int i=0; i<4; i++){
            GIAddr += bb.get()&0xff;
            if(i!=3)
                GIAddr += ".";
        }
        
        byte [] array = new byte[16];
        bb.get(array);
        for(int i=0; i<hlen; i++){
            CHAddr += Integer.toHexString(array[i]&0xff);
            if(i!=hlen-1)
                CHAddr += "-";
        }
        CHAddr = CHAddr.toUpperCase();
        array = new byte[64];
        bb.get(array);
        for(int i=0; i<array.length; i++){
            if(array[i]==0){
                SName = new String(Arrays.copyOfRange(array, 0, i));
            }
            
        }
        array = new byte[128];
        bb.get(array);
        for(int i=0; i<array.length; i++){
            if(array[i]==0){
                BFN = new String(Arrays.copyOfRange(array, 0, i));
            }
            
        }
        
        opt = Arrays.copyOfRange(arr, bb.position(), arr.length);
        int last=0;
        /**
         * Controlla qual è l'ultima variabile con un valore diverso da 0 e, sapendo l'opzione finale di chiusura deve essere 255
         * Successivamente l'array viene tagliato.
         */
        for(int i=0; i<opt.length; i++){
            if((opt[i]&0xff)!=0)
                last = i;
        }
        opt = Arrays.copyOfRange(opt, 0, last+1);
        
    }
    
    public void stampa() {
        System.out.println("\t\t STAMPA");
        System.out.println("OP: " + (op & 0xff));
        System.out.println("HTYPE: " + (htype & 0xff));
        System.out.println("HLEN: " + (hlen & 0xff));
        System.out.println("HOPS: " + (hops & 0xff));
        
        System.out.println("XID: " + (xid & 0xffffffff));
        
        System.out.println("SEC: " + (sec & 0xffff));
        System.out.println("FLAGS: " + (flags & 0xffff));
        
        System.out.println("CIAddr: " + CIAddr);
        System.out.println("YIAddr: " + YIAddr);
        System.out.println("SIAddr: " + SIAddr);
        System.out.println("GIAddr: " + GIAddr);
        
        System.out.println("CHAddr: " + CHAddr);
        
        System.out.println("SName: " + SName);
        System.out.println("BFN: " + BFN);
        
        System.out.println("\t OPTIONS:");
        
        for(int i=0; i<opt.length; i++){
            System.out.println((opt[i]&0xff));
        }
        System.out.println("\n\n");
        
    }
    
    
}
