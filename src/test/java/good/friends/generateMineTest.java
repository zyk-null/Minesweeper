package good.friends;

import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

//测试generateMine方法，使用反射机制，调用私有方法，测试方法的正确性
//generateMine方法的功能是生成地雷,将地雷的位置用1表示，其他位置用0表示
public class generateMineTest {
    @Test
    public void testGenerateMine() {
        Minesweeper ms=new Minesweeper();
        try {
            //需要先调用init方法，初始化show，mine，count
            Method method1=ms.getClass().getDeclaredMethod("init");
            //使用反射机制，调用私有方法
            Method method=ms.getClass().getDeclaredMethod("generateMine");
            method.setAccessible(true);
            method.invoke(ms);
            //使用反射机制，调用私有变量
            Field field=ms.getClass().getDeclaredField("mine");
            field.setAccessible(true);
            int[][] mine=(int[][])field.get(ms);
            field=ms.getClass().getDeclaredField("MINE_NUM");
            field.setAccessible(true);
            int mine_num=(int)field.get(ms);
            int count=0;//定义实际地雷的数量
            for(int i=0;i<mine.length;i++) {
                for(int j=0;j<mine[i].length;j++) {
                    if(mine[i][j]==1) {
                        count++;
                    }
                }
            }
            assertEquals(mine_num,count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
