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
        Field field_show=ms.getClass().getDeclaredField("show");
        field_show.setAccessible(true);
        char[][] show=(char[][])field_show.get(ms);
        Field field_mine=ms.getClass().getDeclaredField("mine");
        field_mine.setAccessible(true);
        int[][] mine=(int[][])field_mine.get(ms);
        Field field_flagNum=ms.getClass().getDeclaredField("flagNum");
        field_flagNum.setAccessible(true);
        int flagNum=(int)field_flagNum.get(ms);
        Field field_hitNum=ms.getClass().getDeclaredField("hitNum");
        field_hitNum.setAccessible(true);
        int hitNum=(int)field_hitNum.get(ms);


        //标记到正确的雷，flagNum+1，hitNum+1，show变为*
        mine[1][1]=1;
        method.invoke(ms,1,1);
        flagNum=(int)field_flagNum.get(ms);
        hitNum=(int)field_hitNum.get(ms);
        assertEquals(1,flagNum);
        assertEquals(1,hitNum);
        assertEquals('?',show[1][1]);
        //标记到已经标记过的雷格子，flagNum-1，hitNum-1，show变为*
        method.invoke(ms,1,1);
        flagNum=(int)field_flagNum.get(ms);
        hitNum=(int)field_hitNum.get(ms);
        assertEquals(0,flagNum);
        assertEquals(0,hitNum);
        assertEquals('*',show[1][1]);


        //标记到错误的雷，flagNum+1，show变为*
        mine[2][2]=0;
        method.invoke(ms,2,2);
        flagNum=(int)field_flagNum.get(ms);
        hitNum=(int)field_hitNum.get(ms);
        assertEquals(1,flagNum);
        assertEquals(0,hitNum);
        assertEquals('?',show[2][2]);
        //标记到已经标记过的非雷格子，flagNum-1，show变为*
        method.invoke(ms,2,2);
        flagNum=(int)field_flagNum.get(ms);
        hitNum=(int)field_hitNum.get(ms);
        assertEquals(0,flagNum);
        assertEquals(0,hitNum);
        assertEquals('*',show[2][2]);
        //标记到已经打开的格子，flagNum不变，show不变
        show[3][3]='1';
        method.invoke(ms,3,3);
        flagNum=(int)field_flagNum.get(ms);
        hitNum=(int)field_hitNum.get(ms);
        assertEquals(0,flagNum);
        assertEquals(0,hitNum);
        assertEquals('1',show[3][3]);


    }
}
