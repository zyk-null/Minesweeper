package good.friends;
import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

//测试isFormat方法，使用反射机制，调用私有方法，测试方法的正确性
public class isFormatTest {
    Minesweeper ms=new Minesweeper();
    //允许的输入格式为：o x y,其中o为操作符，x为行，y为列,且x和y的范围为1-9
    //或者：m x y,其中m为操作符，x为行，y为列,且x和y的范围为1-9
    @Test
    public void testIsFormat() throws Exception {
        Method method=ms.getClass().getDeclaredMethod("isFormat",String.class);
        method.setAccessible(true);
        assertEquals(true,method.invoke(ms,"o 1 1"));
        assertEquals(true,method.invoke(ms,"m 1 1"));
        assertEquals(false,method.invoke(ms,"o 0 1"));
        assertEquals(false,method.invoke(ms,"o 1 0"));
        assertEquals(false,method.invoke(ms,"o 10 1"));
        assertEquals(false,method.invoke(ms,"o 1 10"));
        assertEquals(false,method.invoke(ms,"o 1 1 1"));
        assertEquals(false,method.invoke(ms,"o 1"));
        assertEquals(false,method.invoke(ms,"o 1 1 1"));
        assertEquals(false,method.invoke(ms,"o -1 -1"));

    }
}
