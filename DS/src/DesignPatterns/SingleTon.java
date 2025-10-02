package DesignPatterns;

public class SingleTon {
    private SingleTon(){
    }
    public static class Holder {
        private static final SingleTon INSTANCE = new SingleTon();
    }
    public static SingleTon singleTon() {
        return Holder.INSTANCE;
    }
}
