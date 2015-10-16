package com.frame.common.util.channels;

/**
 * 
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.frame.common.util.PropUtil;

/**
 * @author jiong.peng
 * 
 */
public class SDKUtil {

	private static final String transformation = "RSA/ECB/PKCS1Padding";

	/**
	 * RSA最大加密明文大小(117)
	 */
	public static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * RSA最大解密密文大小(128)
	 */
	public static final int MAX_DECRYPT_BLOCK = 128;

	/** */
	/**
	 * 获取公钥的key
	 */
	public static final String PUBLIC_KEY = "RSAPublicKey";

	/** */
	/**
	 * 获取私钥的key
	 */
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

	/**
	 * 缓存密钥
	 */
	private static final Map<String, Object> keyMap = new HashMap<String, Object>();

	/**
	 * 公钥文件路径
	 */
	public static String PUBLIC_KEY_PATH = "E:/data/9fwlc_public.crt";

	/**
	 * 私钥文件路径
	 */
	public static String PRIVATE_KEY_PATH = "E:/data/9fwlc_private.pfx";
	
	public static final String PASSWORD = "howbuytest";
	
	static{
		String path = SDKUtil.class.getClassLoader().getResource("").getPath();
		path = path.substring(1,path.length());//path.length()-24);
		System.out.println(path);
//		String path = PropUtil.getValue("keyPath");
		PUBLIC_KEY_PATH = File.separator+path+ "9fwlc_public.crt";
		PRIVATE_KEY_PATH = File.separator+path + "9fwlc_private.pfx";
	}
	/**
	 * 加载指定路径证书文件，获取公钥
	 * 
	 * @param keyPath
	 *            证书文件路径
	 * @return 公钥对象
	 * @throws RuntimeException
	 */
	public static PublicKey loadPublicKey(String keyPath)
			throws RuntimeException {
		X509Certificate cert = certDispose(keyPath);
		PublicKey key = cert.getPublicKey();
		keyMap.put(PUBLIC_KEY, key);
		return key;
	}

	/**
	 * 获取私钥
	 * 
	 * @param filePath
	 * @param passwd
	 * @return
	 * @throws SecurityException
	 */
	public static PrivateKey loadPrivateKey(String filePath, String passwd)
			throws SecurityException {
		PfxInputStream readpfxbyte = new PfxInputStream();
		PrivateKey key = readpfxbyte.readPfx2Cert(filePath, passwd);
		keyMap.put(PRIVATE_KEY, key);
		return key;
	}

	/**
	 * 获取私钥
	 * 
	 * @return
	 */
	public static PrivateKey getPrivateKey() {
		return (PrivateKey) keyMap.get(PRIVATE_KEY);
	}

	/**
	 * 获取公钥
	 * 
	 * @return
	 */
	public static PublicKey getPublicKey() {
		return (PublicKey) keyMap.get(PUBLIC_KEY);
	}

	/**
	 * 读取X509证书
	 * 
	 * @param file
	 * @return
	 * @throws SecurityException
	 */
	private static X509Certificate certDispose(String file)
			throws SecurityException {
		InputStream input = null;
		X509Certificate x509certificate;
		try {
			input = new BufferedInputStream(new FileInputStream(file));
			CertificateFactory certificatefactory = CertificateFactory
					.getInstance("X.509");

			x509certificate = (X509Certificate) certificatefactory
					.generateCertificate(input);

			if (null != input)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} catch (Exception e) {
			throw new SecurityException("读取证书失败", e);
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return x509certificate;
	}

	/**
	 * 加密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            源数据bytes
	 * @return
	 * @throws RuntimeException
	 */
	public static String encrypt(String keyType, String data)
			throws RuntimeException {
		Key key = (Key) keyMap.get(keyType);
		return encrypt(key, data);
	}

	/**
	 * 加密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            源数据bytes
	 * @return
	 * @throws RuntimeException
	 */
	public static String encrypt(Key key, String dataStr)
			throws RuntimeException {
		try {
			byte[] data = dataStr.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % MAX_ENCRYPT_BLOCK;
			int blocksSize = leavedSize != 0 ? data.length / MAX_ENCRYPT_BLOCK
					+ 1 : data.length / MAX_ENCRYPT_BLOCK;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * MAX_ENCRYPT_BLOCK > 0) {
				if (data.length - i * MAX_ENCRYPT_BLOCK > MAX_ENCRYPT_BLOCK)
					cipher.doFinal(data, i * MAX_ENCRYPT_BLOCK,
							MAX_ENCRYPT_BLOCK, raw, i * outputSize);
				else
					cipher.doFinal(data, i * MAX_ENCRYPT_BLOCK, data.length - i
							* MAX_ENCRYPT_BLOCK, raw, i * outputSize);
				i++;
			}
			return Base64.encodeBase64String(raw);

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (ShortBufferException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            需要解密的bytes
	 * @return
	 * @throws RuntimeException
	 */
	public static String decrypt(String keyType, String data)
			throws RuntimeException {
		Key key = (Key) keyMap.get(keyType);
		return decrypt(key, data);
	}

	/**
	 * 解密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            需要解密的bytes
	 * @return
	 * @throws RuntimeException
	 */
	public static String decrypt(Key key, String dataStr)
			throws RuntimeException {
		try {
			byte[] data = Base64.decodeBase64(dataStr);
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.DECRYPT_MODE, key);
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (data.length - j * MAX_DECRYPT_BLOCK > 0) {
				bout.write(cipher.doFinal(data, j * MAX_DECRYPT_BLOCK,
						MAX_DECRYPT_BLOCK));
				j++;
			}
			return new String(bout.toByteArray(), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成签名
	 * 
	 * @param data
	 *            源数据bytes
	 * @param prikey
	 *            私钥
	 * @return 签名bytes
	 * @throws RuntimeException
	 */
	public static String generateSignature(String data, PrivateKey prikey)
			throws RuntimeException {
		try {
			Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
			sig.initSign(prikey);
			sig.update(Base64.decodeBase64(data));
			return Base64.encodeBase64String(sig.sign());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 验签
	 * 
	 * @param data
	 *            源数据bytes
	 * @param pubKey
	 *            公钥
	 * @param signature
	 *            签名bytes
	 * @return 验签结果
	 * @throws RuntimeException
	 */
	public static boolean verifySignature(String data, PublicKey pubKey,
			String signature) throws RuntimeException {
		try {
			Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);

			sig.initVerify(pubKey);

			sig.update(Base64.decodeBase64(data));

			return sig.verify(Base64.decodeBase64(signature));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			RuntimeException {

		PrivateKey qqstockPriKey = SDKUtil.loadPrivateKey(PRIVATE_KEY_PATH,
				PASSWORD);
		PublicKey qqstockPubKey = SDKUtil.loadPublicKey(PUBLIC_KEY_PATH);

		System.out.println("qqstockPriKey:"
				+ Base64.encodeBase64String(qqstockPriKey.getEncoded()));
		System.out.println("qqstockPubKey:"
				+ Base64.encodeBase64String(qqstockPubKey.getEncoded()));

		String data = "加密测试数据";
		System.out.println("data:" + data);

		// 加密
//		String encryptData = SDKUtil.encrypt(qqstockPriKey, data);
//		String encryptData = "Sn0Mz0Xd1WRjN6YFneTQiwZhbHkaLV6sA98Z0vuIeJodO2iOJ/Xg9NRtbNOmjrVNQTNp3OLJONmLd/TRRLLJ0h62ZGC9JT+0nDLufBDOJWZwBERemCpeBzSPnsphlnTLfG4lcGSu9oh13HxJhT4fJw2O+LyixTn3I/I5rDLo2Jg=";
		String encryptData = "nU8jT6jRt7bR9fkObD31QO/C+cfodopJZTLQPCAUnEEHR3Ddtmd2nH/0POs+pfk42To7Z6tVymagoWJUOE3me0NZxHCM/utVfHis6Hy49wcvS42iZOVZw5UJjXpfkKnE0/URxwb/v3/otNQZBx86392btAyuEuTo2yALzzplZiwrl/57KbLDeMhRRpgv/S5+xlg+DdvpR4WgQy/VeIj2QeRwU4UTMbMX4UuY4vNEo0DHdXdXXnAtMOsZBsBT2cXg8qfew/2/7C8eidRPnLkxvU8gO3N4yvdKLtb4x9gbtNTuilIOq/2/0dnGxWn5HlIGJN6LwqMVP3+MxfZtn40yag==";
		System.out.println("encryptDate:" + encryptData);

		// 加签
		String signature = SDKUtil
				.generateSignature(encryptData, qqstockPriKey);
		System.out.println("sign:" + signature);

		// 验签
		System.out
				.println("vrfy sign status:"
						+ SDKUtil.verifySignature(encryptData, qqstockPubKey,
								signature));

		// 解密
		String decryptData = SDKUtil.decryptData(encryptData);//decrypt(qqstockPubKey, encryptData);
		System.out.println("datas:" + decryptData);

	}

	/**
	 * 加签
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws RuntimeException
	 */
	public static String generateSignature(String encryptData) {

		String signature = "";
		if (StringUtils.isNotBlank(encryptData)) {
			PrivateKey qqstockPriKey = SDKUtil.loadPrivateKey(PRIVATE_KEY_PATH,
					PASSWORD);
			try {
				// 加签
				signature = SDKUtil.generateSignature(encryptData,
						qqstockPriKey);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return signature;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws RuntimeException
	 */
	public static String encryptData(String data) {

		String encryptData = "";
		if (StringUtils.isNotBlank(data)) {
			PublicKey qqstockPubKey = SDKUtil.loadPublicKey(PUBLIC_KEY_PATH);
			try {
				// 加密
				encryptData = SDKUtil.encrypt(qqstockPubKey, data);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return encryptData;
	}

	/**
	 * 验签
	 * 
	 * @param data
	 * @param sign
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws RuntimeException
	 */
	public static boolean verifySignature(String data, String sign) {

		boolean flag = false;
		if (StringUtils.isNotBlank(data) && StringUtils.isNotBlank(sign)) {
			PublicKey qqstockPubKey = SDKUtil.loadPublicKey(PUBLIC_KEY_PATH);
			try {
				// 验签
				flag = SDKUtil.verifySignature(data, qqstockPubKey, sign);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws RuntimeException
	 */
	public static String decryptData(String encryptData) {

		String decryptData = "";
		if (StringUtils.isNotBlank(encryptData)) {
			PrivateKey qqstockPriKey = SDKUtil.loadPrivateKey(PRIVATE_KEY_PATH,
					PASSWORD);
			try {
				// 解密
				decryptData = SDKUtil.decrypt(qqstockPriKey, encryptData);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return decryptData;
	}

}
