/**
 * Created by wujie on 2017/3/14.
 * 用来写单例模式的
 */


public class Singleton {

    public static Singleton instance;
    private Singleton () {};

    /**
     * 懒汉式，线程不安全
     * 存在的问题，多线程调用，会创建多个实例，在多线程下不能正常工作
     */
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    /**
     * 懒汉式，线程安全
     * 任何时候都只能有一个线程调用该方法
     */
    public static synchronized Singleton getInstace() {
        if (instance == null) {
            instance = new Singleton();
        }
        return  instance;
    }

    /**
     * 双重检验锁,防止多线程的时候一起进入同步块，生成多个实例
     * 构造方法并不是原子操作，
     * 1 给instance 分配内存
     * 2 调用Singleton的构造函数来初始化成员变量
     * 3 将instance对象指向分配的内存空间
     */
    public static Singleton getInstance1() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return  instance;
    }

    /**
     * 饿汉式
     * 单列的实例被申明成static和final变量，在第一次加载类到内存中就会初始化，
     * 所以创建实例本身是线程安全的。
     *
     * 缺点，只要加载类，就会初始化
     */
    private static final Singleton instache2 = new Singleton();
    public static Singleton getInstache2() {
        return  instache2;
    }


    /**
     * 静态内部类
     * 利用jvm本身机制保证线程安全，
     */
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private static final Singleton getINSTANCE() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 枚举 enum
     */
    public enum  EasySinleton {
        INSTANCE;
    }
}


