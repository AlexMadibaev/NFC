package online.madibaev.nfccard;
public final class CardData {
 public static final String NAME="Александр Мадибаев";
 public static final String ROLE="Графический и веб-дизайнер";
 public static final String PHONE="+992900013307";
 public static final String WEB="https://www.madibaevstudio.online/";
 public static final String TG="https://t.me/Alexandr_Madibaev";
 public static String vcard(){return "BEGIN:VCARD\r\nVERSION:3.0\r\nFN:"+NAME+"\r\nTITLE:"+ROLE+"\r\nTEL;TYPE=CELL:"+PHONE+"\r\nURL:"+WEB+"\r\nX-SOCIALPROFILE;TYPE=telegram:"+TG+"\r\nEND:VCARD\r\n";}
 private CardData(){}
}