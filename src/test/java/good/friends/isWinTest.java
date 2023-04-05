package good.friends;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

//测试isWin方法
public class isWinTest {
    Minesweeper ms = new Minesweeper();
    @Test
    public void testIsWin() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //测试用例：1.雷的数量等于标记正确的雷的数量，返回true
        //2.雷的数量不等于标记正确的雷的数量，返回false
        //3.剩余没有揭开的格子数等于雷的数量，返回true
        //4.剩余没有揭开的格子数不等于雷的数量，返回false
        //5.剩余没有揭开的格子数等于雷的数量，且雷的数量等于标记正确的雷的数量，返回true

        //测试用例1
        Field field=ms.getClass().getDeclaredField("MINE_NUM");
        field.setAccessible(true);
        int mine_num=field.getInt(ms);
        field=ms.getClass().getDeclaredField("hitNum");
        field.setAccessible(true);
        field.setInt(ms,mine_num);
        Method method=ms.getClass().getDeclaredMethod("isWin");
        method.setAccessible(true);
        assertEquals(true,method.invoke(ms));
        //测试用例2
        field.setInt(ms,mine_num-1);
        assertEquals(false,method.invoke(ms));
        //测试用例3

        field=ms.getClass().getDeclaredField("show");
        field.setAccessible(true);
        char[][] show=(char[][])field.get(ms);
        for(int i=0;i<show.length;i++){
            for(int j=0;j<show[0].length;j++){
                show[i][j]=' ';
            }
        }
        for(int i=1;i<=mine_num;i++){
            show[i][i]='*';
        }
        assertEquals(true,method.invoke(ms));


    }
}
