package good.friends;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class markTest {
    Minesweeper ms=new Minesweeper();
    //测试mark方法，使用反射机制，调用私有方法，测试方法的正确性
    @Test
    public void testMark() throws Exception {
        Method method=ms.getClass().getDeclaredMethod("mark",int.class,int.class);
        method.setAccessible(true);
        Field field=ms.getClass().getDeclaredField("show");
        field.setAccessible(true);
        char[][] show=(char[][])field.get(ms);
        field=ms.getClass().getDeclaredField("mine");
        field.setAccessible(true);
        int[][] mine=(int[][])field.get(ms);
        field=ms.getClass().getDeclaredField("count");
        field.setAccessible(true);
        int[][] count=(int[][])field.get(ms);
        //测试标记雷的情况
        method.invoke(ms,1,1);
        assertEquals('*',show[1][1]);
        //测试标记非雷的情况
        method.invoke(ms,1,2);
        assertEquals('?',show[1][2]);
        //测试标记已标记的情况
        method.invoke(ms,1,1);
        assertEquals('*',show[1][1]);
        //测试标记已标记的情况
        method.invoke(ms,1,2);
        assertEquals('*',show[1][2]);
        //测试标记已打开的情况
        show[1][3]='1';
        method.invoke(ms,1,3);
        assertEquals('1',show[1][3]);
    }
}
