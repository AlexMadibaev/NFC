package online.madibaev.nfccard;
import android.app.Activity;
import android.content.*;
import android.graphics.*;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
public class MainActivity extends Activity {
 private LinearLayout root;
 private static final int BG=Color.rgb(13,17,27),FG=Color.WHITE,MUTED=Color.rgb(173,184,202),ACCENT=Color.rgb(90,163,255);
 @Override public void onCreate(Bundle state){super.onCreate(state);getWindow().setStatusBarColor(BG);getWindow().setNavigationBarColor(BG);
 ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);scroll.setBackgroundColor(BG);
 root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(28,42,28,40);scroll.addView(root);setContentView(scroll);
 text("NFC BUSINESS CARD",16,ACCENT);text(CardData.NAME,29,FG);text(CardData.ROLE,17,MUTED);
 text(CardData.PHONE,17,FG);text(CardData.WEB,14,MUTED);text(CardData.TG,14,MUTED);
 text("Сканируйте QR для сохранения контакта",16,FG);
 ImageView qr=new ImageView(this);
 try{BitMatrix bits=new MultiFormatWriter().encode(CardData.vcard(),BarcodeFormat.QR_CODE,650,650);Bitmap bitmap=Bitmap.createBitmap(650,650,Bitmap.Config.ARGB_8888);for(int y=0;y<650;y++)for(int x=0;x<650;x++)bitmap.setPixel(x,y,bits.get(x,y)?Color.BLACK:Color.WHITE);qr.setImageBitmap(bitmap);}
 catch(Exception e){text("Ошибка QR: "+e.getMessage(),14,MUTED);}
 root.addView(qr,new LinearLayout.LayoutParams(-1,(int)(285*getResources().getDisplayMetrics().density)));
 button("Поделиться контактом",()->{Intent i=new Intent(Intent.ACTION_SEND);i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,CardData.vcard());startActivity(Intent.createChooser(i,"Поделиться визиткой"));});
 button("Сохранить vCard (.vcf)",()->{Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);i.setType("text/x-vcard");i.addCategory(Intent.CATEGORY_OPENABLE);i.putExtra(Intent.EXTRA_TITLE,"Alexandr_Madibaev.vcf");startActivityForResult(i,1);});
 button("Настройки NFC",()->startActivity(new Intent(Settings.ACTION_NFC_SETTINGS)));
 NfcAdapter nfc=NfcAdapter.getDefaultAdapter(this);boolean hce=getPackageManager().hasSystemFeature("android.hardware.nfc.hce");
 text("NFC: "+(nfc==null?"отсутствует":nfc.isEnabled()?"включён":"выключен")+"\nHCE: "+(hce?"поддерживается":"не поддерживается"),16,MUTED);
 text("HCE работает только с совместимым APDU-считывателем. Для универсального обмена используйте QR-код или vCard.",13,MUTED);
 }
 private void text(String s,int size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setPadding(0,0,0,16);root.addView(t);}
 private void button(String s,Runnable run){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setOnClickListener(v->run.run());root.addView(b,new LinearLayout.LayoutParams(-1,-2));}
 @Override protected void onActivityResult(int request,int result,Intent data){super.onActivityResult(request,result,data);if(request==1&&result==RESULT_OK&&data!=null){try(OutputStream out=getContentResolver().openOutputStream(data.getData())){out.write(CardData.vcard().getBytes(StandardCharsets.UTF_8));Toast.makeText(this,"Контакт сохранён",Toast.LENGTH_SHORT).show();}catch(Exception e){Toast.makeText(this,"Ошибка: "+e.getMessage(),Toast.LENGTH_LONG).show();}}}
}