package com.bubbling.frame.base.tools;




import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class BaseUtils {
	public static final String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
	/**
	 * 判断是否为空
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午8:07:36
	 */
	public static boolean isNull(Object object) {
		if (object==null) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 判断是否为空
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午8:07:36
	 */
	public static boolean isNotNull(Object object) {
		if (object!=null) {
			return true;
		}else {
			return false;
		}
	}
	public static <T> boolean isNotNullList(List<T> objects) {
		if (objects!=null&&objects.size()!=0) {
			return true;
		}else {
			return false;
		}
	}
	public static <T> boolean isNullList(List<T> objects) {
		if (objects!=null&&objects.size()!=0) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 判断是否为空
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午8:07:36
	 */
	public static boolean isNull(String str) {
		if (str==null||str.equals("")) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 判断是否为空
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午8:07:36
	 */
	public static boolean isNotNull(String str) {
		if (str!=null&&!str.equals("")) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * md5加密
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午9:10:37
	 */
	public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }
    /**
     * base64加密
     * @author 杨文
     * @date 2020年4月4日
     * @param b
     * @return
     */
    public static byte[] base64Decoder(String b) {
    	Decoder decoder = Base64.getDecoder();
		return decoder.decode(b);
	}
    /**
     * base64解密
     * @author 杨文
     * @date 2020年4月4日
     * @param b
     * @return
     */
    public static String base64Encoder(byte[] b) {
    	Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(b);
	}
	/**
	 *获取uuid
	 *@Return:java.lang.String
	 *@Author:dc_yangwen
	 *@Date:2021-04-07 21:28
	 */
	public static String getUUID(){
    	return UUID.randomUUID().toString().replaceAll("-","");
	}
	/**
	 * 将map转换为object，转换全部属性（按照key和对象属性之间的关系进行转换）
	 * @param map
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> T  mapToObject(Map<String, Object> map, T t) {
		return mapToObject(map, t,null);
	}

	/**
	 * 将map转换为object，排除指定key
	 * @param map
	 * @param t
	 * @param excludeKeys
	 * @param <T>
	 * @return
	 */
	public static <T> T  mapToObject(Map<String, Object> map, T t, String[] excludeKeys) {
		DateConverter converter = new DateConverter();
		converter.setPattern(new String("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
		ConvertUtils.register(converter,Date.class);
		Class beanClass = t.getClass();
		String[] declaredFieldsName = getDeclaredFieldsName(beanClass);
		if (ArrayUtils.isNotEmpty(excludeKeys)) {
			removeEntries(map, excludeKeys);
		}
		for (Object k : map.keySet()) {
			Object v = map.get(k);
			if (ArrayUtils.contains(declaredFieldsName, k.toString())) {
				try {
					Field field = beanClass.getDeclaredField(k.toString());
					field.setAccessible(true);

					if (isNotNull(v)) {
						v= ConvertUtils.convert(v, field.getType());
					}
					field.set(t,v);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}
	/**
	 * 获取转换后的对象的所有属性名称，以字符串数组形式返回
	 * @param beanClass
	 * @return
	 */
	public static String[] getDeclaredFieldsName(Class beanClass) {
		Field[] fields = beanClass.getDeclaredFields();
		int size = fields.length;
		String[] fieldsName = new String[size];
		for (int i = 0; i < size; i++) {
			fieldsName[i] = fields[i].getName();
		}
		return fieldsName;
	}
	/**
	 * Map中根据key批量删除键值对
	 * @param map
	 * @param excludeKeys
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Map removeEntries(Map<K, V> map, K[] excludeKeys) {
		Iterator<K> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			K k = iterator.next();
			// 如果k刚好在要排除的key的范围中
			if (ArrayUtils.contains(excludeKeys, k)) {
				iterator.remove();
				map.remove(k);
			}
		}
		return map;
	}
	/**
	 *对象转map
	 *@param object 要转换的对象
	 *@Return:java.util.Map<java.lang.String,java.lang.Object>
	 *@Author:dc_yangwen
	 *@Date:2021-04-11 13:00
	 */
	public static Map<String,Object> objectToMap(Object object) throws Exception {
		return com.baomidou.mybatisplus.core.toolkit.BeanUtils.beanToMap(object);
	}
	/**
	 * 获取树根节点数据
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static <T> List<T> getRootNode(List<T> list, String idName, String pidName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<T> retList=new ArrayList<T>();
		List<T> treeList=list;
		List<T> treeList2=list;
		idName=initialsCapitalization(idName);
		pidName=initialsCapitalization(pidName);
		Method methodId;
		for (T t : treeList) {
			boolean isHave=false;
			methodId= t.getClass().getMethod("get"+idName);
			String id=(String) methodId.invoke(t);
			for (T t2 : treeList2) {
				methodId= t2.getClass().getMethod("get"+pidName);
				String pid=(String) methodId.invoke(t2);
				if (id.equals(pid)) {
					isHave=true;
				}
			}
			if (!isHave) {
				retList.add(t);
			}
		}
		return retList;
	}
	/**
	 * 首字母大写
	 */
	public static String initialsCapitalization(String str) {
		// 效率高的方法
		char[] chars = str.toCharArray();
		if (chars[0] >= 'a' && chars[0] <= 'z') {
			chars[0] = (char)(chars[0] - 32);
		}
		return new String(chars);
	}
}
