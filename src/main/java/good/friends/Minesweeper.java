/*
这是一个通过命令行操作的扫雷程序：
1、扫雷游戏的规则就是，在一块9*9的网格中，点开所有没有雷的网格（雷数默认10颗）。
2、输入格式为：op x y,其中op为操作符，x为行，y为列，op有两种，一种为点开，一种为标记。
3、op=o(open),点开网格，若该网格为雷则失败
4、若该网格周边八个网格中有雷，则显示雷数，如果没有雷，则显示空白，同时该网格周边八个网格也会被点开，直到周边八个网格中有雷为止。
5、op=m(mark),标记网格。即插旗，表明你确定该网格有雷，在改变插旗状态前该网格无法被点开；
6、如果网格已经被标记，则再次标记则取消标记，网格恢复原状。
7、如果已经标记了所有的雷，则游戏胜利。
8、如果已经点开所有的非雷网格，则游戏胜利。
9、输入格式错误或者输入的行列数超出范围则重新输入。
10、输入exit退出游戏。
11、输入restart重新开始游戏。

 */


package good.friends;
import java.io.PrintStream;
import java.util.Scanner;
public class Minesweeper{
    final private int ROW=9;//游戏界面的行数
    final private int COL=9;//游戏界面的列数
    final private int ROWS=ROW+2;//用来存放雷的数组的行数,比界面行数大2,因为要在第一行和最后一行加一行,用来存放边界
    final private int COLS=COL+2;//用来存放雷的数组的列数,比界面列数大2,因为要在第一列和最后一列加一列,用来存放边界
    final private int MINE_NUM=9;//雷的数量
    private char[][] show=new char[ROWS][COLS];//用来显示雷的数组，*表示未点开，?表示插旗，数字表示周围雷的数量
    private int[][] mine=new int[ROWS][COLS];//用来存放雷的数组,0表示没有雷，1表示有雷
    private int[][] count=new int[ROWS][COLS];//用来存放周围雷的数量
    private int flagNum=0;//插旗数
    private int hitNum=0;//标记正确的雷数
    private int x;//输入的行和列
    private int y;
    private char op;//输入的操作符
    private Scanner input=new Scanner(System.in);

    public Minesweeper() {
        init();
    }
    //初始化数组
    private void init(){
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                show[i][j]='*';
                mine[i][j]=0;
                count[i][j]=0;
            }
        }
    }
    //显示游戏界面
    public void show(){
        System.out.print("   ");
        for(int i=1;i<COLS-1;i++){
            System.out.print(i+"  ");
        }
        System.out.println();
        for(int i=1;i<ROWS-1;i++){
            System.out.print(i+"  ");
            for(int j=1;j<COLS-1;j++){
                System.out.print(show[i][j]+"  ");
            }
            System.out.println();
        }
    }
    //生成雷
    private void generateMine(){
        int x,y;
        for(int i=0;i<MINE_NUM;i++){
            x=(int)(Math.random()*ROW)+1;
            y=(int)(Math.random()*COL)+1;
            if(mine[x][y]==1){
                i--;
            }else{
                mine[x][y]=1;
            }
        }
        showMine();//作弊，显示雷的位置
    }
    //计算周围雷的数量
    private void countMine(){
        for(int i=1;i<ROWS-1;i++){
            for(int j=1;j<COLS-1;j++){
                if(mine[i][j]==1){
                    count[i-1][j-1]++;
                    count[i-1][j]++;
                    count[i-1][j+1]++;
                    count[i][j-1]++;
                    count[i][j+1]++;
                    count[i+1][j-1]++;
                    count[i+1][j]++;
                    count[i+1][j+1]++;
                }
            }
        }
    }
    //判断输入的行列数是否超出范围
    private boolean isOut(int x,int y){
        if(x<1||x>ROW||y<1||y>COL){
            return true;
        }else{
            return false;
        }
    }
    //判断输入的操作符是否正确
    private boolean isOp(char op){
        if(op=='o'||op=='m'){
            return true;
        }else{
            return false;
        }
    }
    //判断输入的格式是否正确，输入格式为：op x y,其中op为操作符，x为行，y为列，op有两种，一种为点开，一种为标记。
    // 或者输入exit退出游戏，输入restart重新开始游戏
    private boolean isFormat(String str){
        if(str.length()!=5){
            return false;
        }
        if(str.charAt(1)!=' '||str.charAt(3)!=' '){
            return false;
        }
        if(!isOp(str.charAt(0))){
            return false;
        }
        try{
            x=Integer.parseInt(str.substring(2,3));
            y=Integer.parseInt(str.substring(4,5));
        }catch(Exception e){
            return false;
        }
        if(isOut(x,y)){
            return false;
        }
        return true;
    }
    //判断是否胜利，如果插旗数等于雷的数量，则胜利，如果点开的网格数等于总网格数减去雷的数量，则胜利
    private boolean isWin(){
        if(hitNum==MINE_NUM){
            return true;
        }
        int count=0;
        for(int i=1;i<ROWS-1;i++){
            for(int j=1;j<COLS-1;j++){
                if(show[i][j]=='*'){
                    count++;
                }
            }
        }
        if(count==MINE_NUM){
            return true;
        }else{
            return false;
        }
    }
    //判断是否失败，如果点开的网格有雷，则失败
    private boolean isLose(){
        if(mine[x][y]==1){
            return true;
        }else{
            return false;
        }
    }
    //点开网格
    private void open(int x,int y){
        if(isOut(x,y)){
            return;
        }
        if(show[x][y]=='*'){

            if(count[x][y]==0){
                show[x][y]=' ';
                open(x-1,y-1);
                open(x-1,y);
                open(x-1,y+1);
                open(x,y-1);
                open(x,y+1);
                open(x+1,y-1);
                open(x+1,y);
                open(x+1,y+1);
            }else{
                show[x][y]=(char)(count[x][y]+'0');
            }
        }
    }
    //标记网格
    private void mark(int x,int y){
        //如果插旗数等于雷的数量，则不能再插旗
        if(flagNum==MINE_NUM){
            return;
        }
        //只有正确标记的雷才算插旗数
        if(show[x][y]=='*'){
            show[x][y]='?';
            flagNum++;
            if(mine[x][y]==1){
                hitNum++;
            }
            System.out.println(hitNum);
        }else if(show[x][y]=='?'){
            show[x][y]='*';
            flagNum--;
            if(mine[x][y]==1){
                hitNum--;
            }
        }


    }
    //游戏开始,为了防止输出的中文乱码，将输出流的编码设置为utf-8
    public void start(){
        System.setOut(new PrintStream(System.out,true));
//        System.out.println("欢迎来到扫雷游戏！");
//        System.out.println("输入格式为：op x y,其中op为操作符，x为行，y为列，op有两种，一种为点开，一种为标记。");
//        System.out.println("输入exit退出游戏，输入restart重新开始游戏");
//        System.out.println("游戏开始！");
        generateMine();
        countMine();

        while(true){
            show();
            System.out.println("imput:");
            String str=input.nextLine();
            if(str.equals("exit")){
                System.out.println("over");
                break;
            }else if(str.equals("restart")){
                init();
                generateMine();
                countMine();
                flagNum=0;
                continue;
            }else if(!isFormat(str)){
                System.out.println("input error!");
                continue;
            }
            if(isOp(str.charAt(0))){
                if(str.charAt(0)=='o'){
                    open(x,y);
                    if(isLose()){
                        System.out.println("bomb");
                        break;
                    }
                    if(isWin()){
                        System.out.println("win");
                        break;
                    }
                }else if(str.charAt(0)=='m'){
                    mark(x,y);
                    if(isWin()){
                        System.out.println("win");
                        break;
                    }
                }
            }
        }
    }
//为了便于调试，网格的内容输出
    public void showMine(){
        for(int i=1;i<ROWS-1;i++){
            for(int j=1;j<COLS-1;j++){
                System.out.print(mine[i][j]+" ");
            }
            System.out.println();
        }
    }
}