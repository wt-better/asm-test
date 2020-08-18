package pkg;

import org.objectweb.asm.ClassWriter;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.V1_8;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/21
 */
public class Main {

    private static final String OBJECT = "java/lang/Object";
    private static final String NAME = "pkg/Comparable";


    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);

        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                NAME, null, OBJECT, null);

        /*
           接口中的字段是public static final
         */
        cw.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "LESS", "I", null, -1).visitEnd();

        /*
           接口中方法是public abstract
                    注意：这里是/分割，而不是.分割
         */
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null).visitEnd();
        byte[] bytes = cw.toByteArray();

        MyClassLoader myClassLoader = new MyClassLoader();
        Class aClass = myClassLoader.defineClass("pkg.Comparable", bytes);
        System.out.println(aClass);
    }
}
