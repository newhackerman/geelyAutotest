import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;

public class readlogfile {
	private String filepath = "";
	private String filename = "";
	private int line = 20;

	public readlogfile() {
		// TODO Auto-generated constructor stub
	}

	public String getlog(String filepath, String filename, int line) {
	//	this.filename = filename;
	//	this.line = line;
	//	this.filepath = filepath;
		List <String> response1;
		String file;
		if (filename == null) {
			response1 = readLastNLine(new File("/usr/local/test/apache-tomcat-8.0.17/logs/catalina.out"), line);
		} else if (filename.equals("test") ){
			response1 = readLastNLine(new File("D:\\Android\\eclipse\\workspace\\GetParameter\\readfileline.txt"), 5);
		} else {
			if(filepath.contains("\\") ){
				 file=filepath + "..\\..\\logs\\" + filename; 
			}else{ file=filepath + "/../../logs/" + filename;}
			
			System.out.println("file: \t "+file);
			response1 = readLastNLine(new File(file), line);
		}
		return listToString(response1,'\n');
	}

	public static List<String> readLastNLine(File file, long numRead) {
		// 定义结果集
		List<String> result = new ArrayList<String>();
		// 行数统计
		long count = 0;

		// 排除不可读状态
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return null;
		}

		// 使用随机读取
		RandomAccessFile fileRead = null;
		try {
			// 使用读模式
			fileRead = new RandomAccessFile(file, "r");
			// 读取文件长度
			long length = fileRead.length();
			// 如果是0，代表是空文件，直接返回空结果
			if (length == 0L) {
				return result;
			} else {
				// 初始化游标
				long pos = length - 1;
				while (pos > 0) {
					pos--;
					// 开始读取
					fileRead.seek(pos);
					// 如果读取到\n代表是读取到一行
					if (fileRead.readByte() == '\n') {
						// 使用readLine获取当前行
						String line = fileRead.readLine();
						// 保存结果
						result.add(line);

						// 打印当前行
						System.out.println(line);

						// 行数统计，如果到达了numRead指定的行数，就跳出循环
						count++;
						if (count == numRead) {
							fileRead.close();
							break;
						}
					}
				}
				if (pos == 0) {
					fileRead.seek(0);
					result.add(fileRead.readLine());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileRead != null) {
				try {
					// 关闭资源
					fileRead.close();
				} catch (Exception e) {
				}
			}
		}

		return result;
	}

	public String getfilepath() {

		return filepath;
	}

	public String listToString(List<String> list, char separator)  {
		StringBuilder sb = new StringBuilder();
		try {
		if(list.isEmpty())
		{System.out.println("list  is null");
		return null ;}
		for (int i = list.size()-1; i >=0; i--) {
			sb.append(list.get(i)).append(separator);
		}
		} catch (Exception e) {
			System.out.println("处理list 内容异常!!");
			e.printStackTrace();
		}
		return sb.toString().substring(0, sb.toString().length()-1);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readlogfile readlog = new readlogfile();
		String readContext = readlog.getlog("D:\\Android\\eclipse\\workspace\\GetParameter\\", "test", 5);
		System.out.println("读取的内容为:\n" + readContext);
	}

}
