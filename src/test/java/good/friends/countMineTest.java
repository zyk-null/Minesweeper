package good.friends;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

//测试计算周围雷的数量的方法
public class countMineTest {
    Minesweeper ms=new Minesweeper();
    @Test
    public void testCountMine() throws Exception {
        //使用反射机制，调用私有方法
        Method method=ms.getClass().getDeclaredMethod("countMine");
        method.setAccessible(true);

        //获取count数组
        Field field=ms.getClass().getDeclaredField("count");
        field.setAccessible(true);
        int[][] count=(int[][])field.get(ms);
        //获取mine数组
        field=ms.getClass().getDeclaredField("mine");
        field.setAccessible(true);
        int[][] mine=(int[][])field.get(ms);

        //三个测试用例：1.周围没有雷；2.周围有一个雷；3.周围有多个雷
        //数组的大小是11*11
        //测试用例1,周围没有雷
        int[][] mine1=new int[11][11];
        for(int i=0;i<mine1.length;i++){
            for(int j=0;j<mine1[i].length;j++){
                mine1[i][j]=0;
            }
        }
        //测试用例2,周围有一个雷
        int[][] mine2={
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,0,0}
        };
        //预期结果
        int[][] expected2={
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0,0},
                {0,1,2,1,1,0,0,0,0,0,0},
                {0,0,1,0,1,0,0,0,0,0,0},
                {0,0,1,1,2,1,1,0,0,0,0},
                {0,0,0,0,1,0,1,0,0,0,0},
                {0,0,0,0,1,1,2,1,1,0,0},
                {0,0,0,0,0,0,1,0,1,0,0},
                {0,0,0,0,0,0,1,1,2,1,0},
                {0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,0,0,1,0}
        };
        //测试用例3,周围有多个雷
        int[][] mine3= {
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0}
        };
        //预期结果
        int[][] expected3={
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,3,5,5,5,5,5,5,5,3,0},
                {0,5,8,8,8,8,8,8,8,5,0},
                {0,5,8,8,8,8,8,8,8,5,0},
                {0,5,8,8,8,8,8,8,8,5,0},
                {0,5,8,8,8,8,8,8,8,5,0},
                {0,5,8,8,8,8,8,8,8,5,0},
                {0,5,8,8,8,8,8,8,8,5,0},
                {0,5,8,8,8,8,8,8,8,5,0},
                {0,3,5,5,5,5,5,5,5,3,0},
                {0,0,0,0,0,0,0,0,0,0,0}
        };
        //测试用例1
        for(int i=0;i<mine.length;i++){
            for(int j=0;j<mine[i].length;j++){
                mine[i][j]=mine1[i][j];
                count[i][j]=0;
            }
        }
        method.invoke(ms);

        for(int i=0;i<mine.length;i++){
            for(int j=0;j<mine[i].length;j++){
                assertEquals(0,count[i][j]);
            }
        }
        //测试用例2
        for(int i=0;i<mine.length;i++){
            for(int j=0;j<mine[i].length;j++){
                mine[i][j]=mine2[i][j];
                count[i][j]=0;
            }
        }
        method.invoke(ms);
        for(int i=1;i<mine.length-1;i++){
            for(int j=1;j<mine[i].length-1;j++){
                assertEquals(expected2[i][j],count[i][j]);
            }
        }
        //测试用例3
        for(int i=0;i<mine.length;i++){
            for(int j=0;j<mine[i].length;j++){
                mine[i][j]=mine3[i][j];
                count[i][j]=0;
            }
        }
        method.invoke(ms);
        for(int i=1;i<mine.length-1;i++){
            for(int j=1;j<mine[i].length-1;j++){
                assertEquals(expected3[i][j],count[i][j]);
            }
        }

    }
}
