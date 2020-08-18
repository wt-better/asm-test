package pkg;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/21
 */
public class MyClassLoader extends ClassLoader {

    public Class defineClass(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length, null);
    }
}
