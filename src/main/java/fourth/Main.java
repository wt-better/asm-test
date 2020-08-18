package fourth;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/22
 */
public class Main {

    public static void main(String[] args) throws Exception {
        byte[] bytes = new byte[]{};

        ClassWriter cw = new ClassWriter(0);
        ClassReader cr = new ClassReader(bytes);

        ClassVisitor cv = new ChangeVersionAdapter(cw);
        cr.accept(cv, 0);
        byte[] chancedBytes = cw.toByteArray();
    }
}
