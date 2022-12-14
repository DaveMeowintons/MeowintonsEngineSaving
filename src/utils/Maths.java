package utils;

public class Maths {
    /**
     * Check if two byte arrays contain the same data
     * @param in1 Byte Array One
     * @param in2 Byte Array Two
     * @return True if byte arrays same length and values identical
     */
    public static boolean checkEqual(byte[] in1, byte[] in2){
        if(in1.length != in2.length) return false;
        for(int i = 0; i < in1.length; i++) if(in1[i] != in2[i]) return false;

        return true;
    }
}
