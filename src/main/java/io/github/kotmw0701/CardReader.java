package io.github.kotmw0701;

import com.sun.jna.Pointer;

import java.nio.charset.StandardCharsets;

public class CardReader implements AutoCloseable{

    private Pointer pasoriHandle = null;

    private final short WILDCARD =           (short) 0xFFFF;//ワイルドカード
    private final short STUDENT_ID_AREA =    (short) 0x8E90;//学生証のエリアコード(サービスコード)
    private final short STUDENT_ID_SERVICE = (short) 0x200B;//学籍番号のブロックのコード

    /**
     * ポート接続
     *
     * @return 既に開いてる若しくは接続に成功したらtrue
     */
    public boolean open() {
        if (isOpened()) return true; //既に開いている

        pasoriHandle = FelicaLib.instance.pasori_open(null);

        if (pasoriHandle == Pointer.NULL) return false; //接続失敗

        if (FelicaLib.instance.pasori_init(pasoriHandle) != 0) { //ポート初期化失敗
            close(); //切断
            return false;
        }
        return true;
    }

    /**
     * ポート切断
     */
    @Override
    public void close() {
        System.out.println("close");
        if (isOpened())
            FelicaLib.instance.pasori_close(pasoriHandle);
        pasoriHandle = Pointer.NULL;
    }

    /**
     * ポートの接続判定
     *
     * @return
     */
    public boolean isOpened() {
        if (pasoriHandle == Pointer.NULL) return false;
        return true;
    }

    public String getIDM() {
        if (!isOpened())
            return "";
        Pointer felica = FelicaLib.instance.felica_polling(pasoriHandle, WILDCARD, (byte) 0, (byte) 0);
        if (felica == Pointer.NULL)
            return "";
        byte[] idm = new byte[8];
        FelicaLib.instance.felica_getidm(felica, idm);
        StringBuilder builder = new StringBuilder();
        for (byte b : idm) {
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) builder.append("0");
            if (hex.length() > 2) hex = hex.substring(hex.length() - 2);
            builder.append(hex);
        }
        return builder.toString().toUpperCase().trim();
    }

    public String getStudentID() {
        if (!isOpened())
            return "";
        Pointer felica = FelicaLib.instance.felica_enum_service(pasoriHandle, STUDENT_ID_AREA);
        if (felica == Pointer.NULL)
            return "";
        byte[] asciiArray = new byte[16];
        FelicaLib.instance.felica_read_without_encryption02(felica, STUDENT_ID_SERVICE, 0, (byte) 0, asciiArray);
        return new String(asciiArray, StandardCharsets.US_ASCII).substring(0, 9).trim();
    }
}
