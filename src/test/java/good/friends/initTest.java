package good.friends;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.Assert.assertEquals;

//测试init方法，使用反射机制，调用私有方法，测试方法的正确性
public class initTest {
    Minesweeper ms=new Minesweeper();
    @Test
    public void testInit() throws Exception {
        //使用反射机制，调用私有方法
        Minesweeper ms=new Minesweeper();
        Method method=ms.getClass().getDeclaredMethod("init");
        method.setAccessible(true);
        method.invoke(ms);
        //使用反射机制，调用私有变量
        Field field=ms.getClass().getDeclaredField("show");
        field.setAccessible(true);
        char[][] show=(char[][])field.get(ms);
        field=ms.getClass().getDeclaredField("mine");
        field.setAccessible(true);
        int[][] mine=(int[][])field.get(ms);
        field=ms.getClass().getDeclaredField("count");
        field.setAccessible(true);
        int[][] count=(int[][])field.get(ms);
        for(int i=0;i<show.length;i++){
            for(int j=0;j<show[i].length;j++){
                assertEquals('*',show[i][j]);
                assertEquals(0,mine[i][j]);
                assertEquals(0,count[i][j]);
            }
        }

    }
}
