package com.genertech.phm.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <b>DES加密介绍</b><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。
 * DES加密算法出自IBM的研究， 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 * 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现 。 <br>
 *  <br><b>注意：</b>DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DESUtil {

	// 测试
	public static void main(String args[]) {
		// 待加密内容
		String str = "ComponentRowID,2017-02-09 00:00:00,2017-02-15 23:59:59";
		// 密码，长度要是8的倍数
		String password = "ImportantFault2017";

		String encryptResult = DESUtil.encryptToString(str, password);
		//"2051F1AE74BA5142E51BE7911DCFFB58BB1FCFBCBC90521808350C2C9CEA9192E9871A9FB9347482AE9910570E1B6B0C7FC20B737829D5EA";
		System.out.println("加密后：" + encryptResult);
		encryptResult = "2051F1AE74BA51428CD4DA4C33444C90606AD07165896402B93A794AA2DF8A62047DAF81775DA7EBEACCFD4643D310EBD8DF4DC4BD6573B9";
		System.out.println("解密后：" + DESUtil.decryptToString(encryptResult, password));
	}
	
	/**
	 * 将字符串进行DES加密
	 * @param rawData 
	 * 				String
	 * @param password
	 * 				String
	 * @return String
	 */
	public static String encryptToString(String rawData, String password) {
		if(rawData != null && !rawData.isEmpty()){
			return parseByte2HexStr(encrypt(rawData, password));
		}
		return null;
	}
	
	/**
	 * 将DES加密的字符串解密
	 * @param rawData 
	 * 				String
	 * @param password
	 * 				String
	 * @return String
	 */
	public static String decryptToString(String rawData, String password) {
		if(rawData != null && !rawData.isEmpty()){
			return new String(decrypt(parseHexStr2Byte(rawData), password));
		}
		return null;
	}

	/**
	 * 加密
	 * 
	 * @param datasource
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 */
	private static byte[] encrypt(String datasource, String password) {
		try {
		    String keyHash =  DigestUtils.sha1Hex(DigestUtils.md5Hex(password).substring(0, 8).toUpperCase()).substring(0, 8).toUpperCase();
	        String ivHash =  DigestUtils.md5Hex(DigestUtils.md5Hex(password).substring(0, 8).toUpperCase()).substring(0, 8).toUpperCase();
	        
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成SecretKey
			SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keyHash.getBytes("UTF-8")));
            IvParameterSpec iv = new IvParameterSpec(ivHash.getBytes("UTF-8")); 
			
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource.getBytes("UTF-8"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * 
	 * @param src
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 */
	private static byte[] decrypt(byte[] src, String password) {
		try {

		    String keyHash =  DigestUtils.sha1Hex(DigestUtils.md5Hex(password).substring(0, 8).toUpperCase()).substring(0, 8).toUpperCase();
            String ivHash =  DigestUtils.md5Hex(DigestUtils.md5Hex(password).substring(0, 8).toUpperCase()).substring(0, 8).toUpperCase();

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成SecretKey
            SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keyHash.getBytes("UTF-8")));
            IvParameterSpec iv = new IvParameterSpec(ivHash.getBytes("UTF-8")); 
            
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			// 真正开始解密操作
			return cipher.doFinal(src);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**将二进制转换成16进制 
     * @param buf byte[]
     * @return 	String
     */  
    public static String parseByte2HexStr(byte buf[]) {  
            StringBuffer sb = new StringBuffer();  
            for (int i = 0; i < buf.length; i++) {  
                    String hex = Integer.toHexString(buf[i] & 0xFF);  
                    if (hex.length() == 1) {  
                            hex = '0' + hex;  
                    }  
                    sb.append(hex.toUpperCase());  
            }  
            return sb.toString();  
    } 
    

    /**将16进制转换为二进制 
     * @param hexStr String
     * @return byte[]
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
            if (hexStr.length() < 1)  
                    return null;  
            byte[] result = new byte[hexStr.length()/2];  
            for (int i = 0;i< hexStr.length()/2; i++) {  
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                    result[i] = (byte) (high * 16 + low);  
            }  
            return result;  
    } 
}
