package good.friends;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

//测试isLose方法
public class isLoseTest {
    Minesweeper ms = new Minesweeper();
    @Test
    public void testIsLose() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        //测试用例1：没有点到雷
        //测试用例2：点到雷
        Method method = ms.getClass().getDeclaredMethod("isLose");
        method.setAccessible(true);
        Field field = ms.getClass().getDeclaredField("mine");
        field.setAccessible(true);
        int[][] mine = (int[][]) field.get(ms);
        field = ms.getClass().getDeclaredField("x");
        field.setAccessible(true);
        int x = field.getInt(ms);
        field = ms.getClass().getDeclaredField("y");
        field.setAccessible(true);
        int y = field.getInt(ms);
        //测试用例1,没有点到雷
        x=1;
        y=1;
        mine[x][y] = 0;
        field.set(ms, x);
        field.set(ms, y);
        assertEquals(false, method.invoke(ms));
        //测试用例2，点到雷
        x=3;
        y=3;
        //修改ms的x,y的值
        field=ms.getClass().getDeclaredField("x");
        field.setAccessible(true);
        field.setInt(ms,x);
        field=ms.getClass().getDeclaredField("y");
        field.setAccessible(true);
        field.setInt(ms,y);


        mine[x][y] = 1;
        assertEquals(true, method.invoke(ms));
    }
}
