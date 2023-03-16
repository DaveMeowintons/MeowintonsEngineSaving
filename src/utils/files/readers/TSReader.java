package utils.files.readers;

import utils.Loader;
import utils.logging.LogLevel;
import utils.logging.Logger;
import utils.serialisation.types.TSDataType;

import java.io.BufferedInputStream;
import java.io.File;

public class TSReader extends FileReader {
    protected BufferedInputStream reader;
    protected byte[]              data;

    @Override
    public void open(Loader loader, String file){
        this.fileName = file;
        this.reader = loader.getSerialisedData(file);

        readData();
    }

    @Override
    public void open(Loader loader, File file){
        this.fileName = file.getName();
        this.reader = loader.getSerialisedData(file);

        readData();
    }

    @Override
    public void close(){
        try{
            this.reader.close();
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), "Error closing reader for file: " + this.fileName);
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), error);
        }
    }

    /**
     * Read all data in file and store it into an array
     */
    private void readData(){
        try{
            this.data = new byte[reader.available()];
            this.reader.read(this.data);
        }catch(Exception error){
            Logger.log(LogLevel.ERROR, getClass().getSimpleName(), error);
        }
    }

    public static byte      readByte       (byte[] src, int pointer){ return src[pointer]; }
    public static byte[]    readBytes      (byte[] src, int pointer, int length){
        byte[] dest = new byte[length];
        for (int i = 0; i < length; i++) dest[i] = readByte(src, pointer + i);
        return dest;
    }

    public static short     readShort      (byte[] src, int pointer){ return (short)((src[pointer] & 0xff) << 8 | (src[pointer + 1] & 0xff)); }
    public static short[]   readShorts     (byte[] src, int pointer, int length){
        short[] dest = new short[length];
        for (int i = 0; i < length; i++){ dest[i] = readShort(src, pointer); pointer += TSDataType.getSize(TSDataType.SHORT); }
        return dest;
    }

    public static int       readInt        (byte[] src, int pointer){ return (int)((src[pointer] & 0xff) << 24 | (src[pointer + 1] & 0xff) << 16 | (src[pointer + 2] & 0xff) << 8 | (src[pointer + 3] & 0xff)); }
    public static int[]     readInts       (byte[] src, int pointer, int length){
        int[] dest = new int[length];
        for (int i = 0; i < length; i++){ dest[i] = readInt(src, pointer); pointer += TSDataType.getSize(TSDataType.INTEGER); }
        return dest;
    }

    public static long      readLong       (byte[] src, int pointer){ return (long)((src[pointer] & 0xff) << 56 | (src[pointer + 1] & 0xff) << 48 | (src[pointer + 2] & 0xff) << 40 | (src[pointer + 3] & 0xff) << 32 | (src[pointer + 4] & 0xff) << 24 | (src[pointer + 5] & 0xff) << 16 | (src[pointer + 6] & 0xff) << 8 | (src[pointer + 7] & 0xff)); }
    public static long[]    readLongs      (byte[] src, int pointer, int length){
        long[] dest = new long[length];
        for (int i = 0; i < length; i++){ dest[i] = readLong(src, pointer); pointer += TSDataType.getSize(TSDataType.LONG); }
        return dest;
    }

    public static float     readFloat      (byte[] src, int pointer){ return Float.intBitsToFloat(readInt(src, pointer)); }
    public static float[]   readFloats     (byte[] src, int pointer, int length){
        float[] dest = new float[length];
        for (int i = 0; i < length; i++){ dest[i] = readFloat(src, pointer); pointer += TSDataType.getSize(TSDataType.FLOAT); }
        return dest;
    }

    public static double    readDouble    (byte[] src, int pointer){ return Double.longBitsToDouble(readLong(src, pointer)); }
    public static double[]  readDoubles   (byte[] src, int pointer, int length){
        double[] dest = new double[length];
        for (int i = 0; i < length; i++){ dest[i] = readDouble(src, pointer); pointer += TSDataType.getSize(TSDataType.DOUBLE); }
        return dest;
    }

    public static boolean   readBoolean  (byte[] src, int pointer){ return src[pointer] != 0; }
    public static boolean[] readBooleans (byte[] src, int pointer, int length){
        boolean[] dest = new boolean[length];
        for (int i = 0; i < length; i++){ dest[i] = readBoolean(src, pointer); pointer += TSDataType.getSize(TSDataType.BOOLEAN); }
        return dest;
    }

    public static char      readChar      (byte[] src, int pointer){ return (char)((src[pointer] & 0xff) << 8 | (src[pointer + 1] & 0xff)); }
    public static char[]    readChars     (byte[] src, int pointer, int length){
        char[] dest = new char[length];
        for (int i = 0; i < length; i++){ dest[i] = readChar(src, pointer); pointer += TSDataType.getSize(TSDataType.CHAR); }
        return dest;
    }

    public static String    readString   (byte[] src, int pointer){ return String.valueOf(readChars(src, pointer + TSDataType.getSize(TSDataType.SHORT), readShort(src, pointer))); }
    public static String[]  readStrings  (byte[] src, int pointer, int length){
        String[] dest = new String[length];
        for (int i = 0; i < length; i++){ dest[i] = readString(src, pointer); pointer += TSDataType.getSize(TSDataType.SHORT) + ((TSDataType.getSize(TSDataType.STRING) * dest[i].length())); }
        return dest;
    }

    public static String    readString    (byte[] src, int pointer, int length){ return new String(src, pointer, length); }

    /**Getters**/

    public byte[] getData(){ return data; }
}
