package second;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/21
 */
public class ParameterNameDiscover {

    public static void main(String[] args) throws Exception {
        Method method = EchoService.class.getMethod("echo", String.class, String.class);
        String[] methodParameterNames = getMethodParameterNames(method);
        System.out.println(Arrays.toString(methodParameterNames));
    }


    public static String[] getMethodParameterNames(final Method method) {
        final String[] paramNames = new String[method.getParameterTypes().length];
        final String n = method.getDeclaringClass().getName();

        ClassReader cr;
        try {
            cr = new ClassReader(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cr.accept(new ClassVisitor(Opcodes.ASM5) {
            @Override
            public MethodVisitor visitMethod(final int access,
                                             final String name, final String desc,
                                             final String signature, final String[] exceptions) {

                final Type[] args = Type.getArgumentTypes(desc);
                // 方法名相同并且参数个数相同
                if (!name.equals(method.getName()) || !sameType(args, method.getParameterTypes())) {
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }

                MethodVisitor v = super.visitMethod(access, name, desc, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM4, v) {
                    @Override
                    public void visitLocalVariable(String name, String desc,
                                                   String signature, Label start, Label end, int index) {
                        int i = index - 1;
                        // 如果是静态方法，则第一就是参数
                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                        if (Modifier.isStatic(method.getModifiers())) {
                            i = index;
                        }
                        if (i >= 0 && i < paramNames.length) {
                            paramNames[i] = name;
                        }
                        super.visitLocalVariable(name, desc, signature, start, end, index);
                    }

                };
            }
        }, 0);
        return paramNames;
    }

    private static boolean sameType(Type[] types, Class<?>[] classes) {
        // 个数不同
        if (types.length != classes.length) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(classes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }
}
