package online.madibaev.nfccard;
import android.service.quicksettings.TileService;
import android.content.Intent;
import android.app.PendingIntent;
import android.os.Build;
public class CardTileService extends TileService {
 @Override public void onClick(){super.onClick();Intent i=new Intent(this,MainActivity.class);i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
 if(Build.VERSION.SDK_INT>=34){startActivityAndCollapse(PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE));}
 else {startActivityAndCollapse(i);}
 }
}