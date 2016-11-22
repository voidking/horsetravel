package njit.temp;

/*
 * ��дһ��Ӧ�ó���ʹ�� String ��ķ��� compareTo() �Ƚ��û�����������ַ�����
 * ��ӡ���ȽϽ����С�ڣ����ڻ���ڡ�
 */
import java.io.*;

public class Lab03_1 {
	public static void main(String[] args) {
		try {
			//����ʾһ�����֣�Ȼ��ȴ��û�����
			String str[] = new String[2];
			for(int i=0; i<2; i++) {
				str[i] = readUserInput("�������������䣺");
			}
			System.out.println("��������ǣ�" + str[0]);
			System.out.println("��������ǣ�" + str[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ��ȡ�û�����
	 *
	 * @param prompt ��ʾ����
	 * @return �û�����
	 * @throws IOException �����ȡʧ��
	 */
	private static String readUserInput(String prompt) throws IOException {
		 //�ȶ�������û�����ı���
		 String result;
		 do {
			  // �����ʾ����
			 System.out.print(prompt);
			 InputStreamReader is_reader = new InputStreamReader(System.in);
			 result = new BufferedReader(is_reader).readLine();
		 }while (isInvalid(result)); // ���û�������Ч��ʱ�򣬷�����ʾҪ���û�����
		 return result;
	}
	
	/**
	 * ����û�����������Ƿ���Ч
	 *
	 * @param str �û����������
	 * @return ����û������������Ч���򷵻� true
	 */
	private static boolean isInvalid(String str) {
		return str.equals("");
	}
}
