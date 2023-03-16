package _main;

import ecs.components.Component;

import java.util.List;
import java.util.ArrayList;

public class TestComponent extends Component {
    private byte aByte = 0x01;
    private byte[] bytes = { 0x00, 0x01 };
    private boolean aBoolean = false;
    private boolean[] booleans = { true, false };
    private int anInt = 32;
    private int[] ints = { 32, 64 };
    private short aShort = 24;
    private short[] shorts = { 24, 48 };
    private long aLong = 512;
    private long[] longs = { 512, 1024 };
    private double aDouble = 24.0;
    private double[] doubles = { 24.0, 48.0 };
    private float aFloat = 32.0f;
    private float[] floats = { 32.0f, 64.0f };
    private char aChar = 'a';
    private char[] chars = { 'a', 'b' };
    private String aString = "string";
    private String[] strings = { "string", "string two!" };

    private List<Integer> list = new ArrayList<>();

    protected TestComponent(){
        list.add(32);
    }
}
