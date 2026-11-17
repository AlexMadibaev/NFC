package online.madibaev.nfccard;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
public class CardHceService extends HostApduService {
 private static final byte[] SELECT={0x00,(byte)0xA4,0x04,0x00,0x09,(byte)0xF0,0x4E,0x46,0x43,0x42,0x41,0x52,0x44,0x01};
 @Override public byte[] processCommandApdu(byte[] command,Bundle extras){
  if(command!=null && command.length>=SELECT.length && Arrays.equals(Arrays.copyOf(command,SELECT.length),SELECT)){
   byte[] data=CardData.vcard().getBytes(StandardCharsets.UTF_8);
   byte[] result=Arrays.copyOf(data,data.length+2);result[data.length]=(byte)0x90;result[data.length+1]=0x00;return result;
  }
  return new byte[]{(byte)0x6A,(byte)0x82};
 }
 @Override public void onDeactivated(int reason){}
}