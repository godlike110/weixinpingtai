package cn.focus.dc.focuswap.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.focus.dc.focuswap.model.HomeFocusPic;


public class ObjectUtil {
	
	

	 /**
	  * 对象系列化
	  * @param value
	  * @return
	  */
	 public static byte[] object2Bytes(Object value) {
	        if (value == null)
	            return null;
	        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
	        ObjectOutputStream outputStream;
	        try {
	            outputStream = new ObjectOutputStream(arrayOutputStream);
	            outputStream.writeObject(value);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                arrayOutputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return arrayOutputStream.toByteArray();
	    }
	 
	 
	 /**
	  * 反系列化
	  * @param bytes
	  * @return
	  */
	  public static Object byte2Object(byte[] bytes) {
	        if (bytes == null || bytes.length == 0)
	            return null;
	        try {
	            ObjectInputStream inputStream;
	            inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
	            Object obj = inputStream.readObject();
	            return obj;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	  
	  public static byte[] filetoBytes(File file) {
		  
		  ByteArrayOutputStream bos = new ByteArrayOutputStream((int)file.length());
		  BufferedInputStream in = null;  
		  try {
			  in = new BufferedInputStream(new FileInputStream(file));
	          int buf_size = 1024;  
	          byte[] buffer = new byte[buf_size];  
	          int len = 0;  
	          while(-1 != (len = in.read(buffer,0,buf_size))){  
	                bos.write(buffer,0,len);  
	          } 
	          return bos.toByteArray();
		  } catch (Exception e) {
			  return null;
		  }
		  
	  }
	  
	  public static void main(String argv[]) {
//		  HomeFocusPic hfp = new HomeFocusPic();
//		  hfp.setPicUrl("http://www.baidu.com");
//		  hfp.setTitle("测试");
//		  hfp.setUrl("http://m.focus.cn");
//		  byte[] a = object2Bytes(hfp);
//		  HomeFocusPic b = null;
//		  b = (HomeFocusPic) byte2Object(a);
//		  int c = 1;
		  
		  File file = new File("/home/zhiweiwen/workspace/focus-wap/src/main/webapp/a.html");
		  byte[] a = object2Bytes(file);
		  File b = null;
		  b = (File) byte2Object(a);
		  int c = 0;
		  
	  }
}
