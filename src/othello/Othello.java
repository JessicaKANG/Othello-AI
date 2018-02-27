package othello;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
*
*   A B C D E F G H
* 1
* 2                	BLACK: 2
* 3       .
* 4     . o x      	WHITE: 2
* 5       x o .
* 6         .      	BLACKs turn!
* 7
* 8
* 
* 1: 5F	2: 6E	3: 3D	4: 4C
* Select move: 
*
* @author c00kiemon5ter
*/
public class Othello {

	private static final String HELP_OPT = "help";
	private static final String CLI_OPT = "cli";
	private static final String GUI_OPT = "gui";

	public static void main(String[] args) {
		Game othello = null;	
		CreateXLS();
		if (args.length == 0 || args[0].equals(GUI_OPT)) {
			othello = new GuiGame();
		} else if (args[0].equals(CLI_OPT)) {
			othello = new CliGame();
		} else if (args[0].equals(HELP_OPT)) {
			printUsage();
			System.exit(0);
		} else {
			System.err.printf("Wrong argument: %s\n", args[0]);
			printUsage();
			System.exit(1);
		}
		othello.play();
	}

	private static void printUsage() {
		System.err.printf("Usage: %s [options]\n"
				  + "\n\tOptions are:\n"
				  + "\t %s\tcommand line interface mode\n"
				  + "\t %s\tgraphical user interface mode [default]\n"
				  + "\t %s\tthis help message\n",
				  Othello.class.getSimpleName(), CLI_OPT, GUI_OPT, HELP_OPT);
	}
	
	public static void CreateXLS(){
		//标题行
        String title[]={"综合估值","score","子力估值","score","行动力估值","score"};
		try {
			WritableWorkbook book = Workbook.createWorkbook(new File("List.xls")); 
			//WritableWorkbook book= Workbook.createWorkbook(new File("List.xls"),wb); 
			WritableSheet sheet=book.createSheet("第一页",0); 
            
            //写入内容
            for(int i=0;i<6;i++)    //title
                sheet.addCell(new Label(i,0,title[i]));          
            //写入数据
            book.write(); 
            //关闭文件
            book.close(); 
		} catch (IOException | WriteException e) {
			e.printStackTrace();
		}
	}
	
	
}
/**
* TODO: Implement a server-client architecture and play over network
* TODO: Negascout + Efficient use of a Global Hash Table <Move, Max>
*/
