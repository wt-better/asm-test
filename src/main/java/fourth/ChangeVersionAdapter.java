package fourth;


import org.objectweb.asm.ClassVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM4;
import static jdk.internal.org.objectweb.asm.Opcodes.V1_5;

/**
 * 动态修改JDK版本
 *
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/22
 */
public class ChangeVersionAdapter extends ClassVisitor {

    public ChangeVersionAdapter(ClassVisitor classVisitor) {
        super(ASM4, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //super.visit(version, access, name, signature, superName, interfaces);
        cv.visit(V1_5, access, name, signature, superName, interfaces);
    }
}
