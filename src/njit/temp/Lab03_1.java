package njit.temp;

/*
 * 编写一个应用程序，使用 String 类的方法 compareTo() 比较用户输入的两个字符串。
 * 打印出比较结果：小于，等于或大于。
 */
import java.io.*;

public class Lab03_1 {
	public static void main(String[] args) {
		try {
			//先提示一段文字，然后等待用户输入
			String str[] = new String[2];
			for(int i=0; i<2; i++) {
				str[i] = readUserInput("请输入您的年龄：");
			}
			System.out.println("您输入的是：" + str[0]);
			System.out.println("您输入的是：" + str[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 读取用户输入
	 *
	 * @param prompt 提示文字
	 * @return 用户输入
	 * @throws IOException 如果读取失败
	 */
	private static String readUserInput(String prompt) throws IOException {
		 //先定义接受用户输入的变量
		 String result;
		 do {
			  // 输出提示文字
			 System.out.print(prompt);
			 InputStreamReader is_reader = new InputStreamReader(System.in);
			 result = new BufferedReader(is_reader).readLine();
		 }while (isInvalid(result)); // 当用户输入无效的时候，反复提示要求用户输入
		 return result;
	}
	
	/**
	 * 检查用户输入的内容是否无效
	 *
	 * @param str 用户输入的内容
	 * @return 如果用户输入的内容无效，则返回 true
	 */
	private static boolean isInvalid(String str) {
		return str.equals("");
	}
}
