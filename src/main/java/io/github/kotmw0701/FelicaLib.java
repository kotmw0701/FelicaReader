package io.github.kotmw0701;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface FelicaLib extends Library {
    final FelicaLib instance = (FelicaLib) Native.loadLibrary("felicalib", FelicaLib.class);
    Pointer pasori_open(String dummy);
    void pasori_close(Pointer pasoriHandle);
    int pasori_init(Pointer pasoriHandle);
    Pointer felica_polling(Pointer pasoriHandle, short systemCode, byte rfu, byte timeSlot);
    void felica_free(Pointer felicaHandle);
    void felica_getidm(Pointer felicaHandle, byte[] buf);
    Pointer felica_enum_service(Pointer pasoriHandle, short systemCode);
    int felica_read_without_encryption02(Pointer felicaHandle, int serviceCode, int mode, byte addr, byte[] data);
}
