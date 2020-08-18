package third;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import pkg.MyClassLoader;

import java.lang.reflect.Method;

import static jdk.internal.org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static jdk.internal.org.objectweb.asm.Opcodes.ACC_SUPER;
import static jdk.internal.org.objectweb.asm.Opcodes.ALOAD;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static jdk.internal.org.objectweb.asm.Opcodes.V1_8;
import static jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode.RETURN;
import static org.objectweb.asm.Opcodes.GETSTATIC;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/22
 */
public class Main {

    public static void main(String[] args) throws Exception {
        byte[] bytes = generateImpl(EchoService.class);
        MyClassLoader classLoader = new MyClassLoader();
        Class aClass = classLoader.defineClass("EchoServiceImpl", bytes);
        System.out.println(aClass);

        //Method method = aClass.getMethod("echo", String.class, String.class);
//        String[] methodParameterNames = ParameterNameDiscover.getMethodParameterNames(method);
//        System.out.println(Arrays.toString(methodParameterNames));
    }

    private static byte[] generateImpl(Class clazz) {
        String name = clazz.getSimpleName();
        String className = name + "Impl";

        String interfaceName = clazz.getName().replaceAll("\\.", "/");

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, className, null,
                "java/lang/Object", new String[]{interfaceName});

        // 空构造
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null,
                null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // 实现接口中所有方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            makeMethod(cw, method.getName());
        }
        cw.visitEnd();

        //写入文件
        return cw.toByteArray();
    }

    private static void makeMethod(ClassWriter cw, String methodName) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, methodName, "()V", null, null);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("调用方法 [" + methodName + "]");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
}
