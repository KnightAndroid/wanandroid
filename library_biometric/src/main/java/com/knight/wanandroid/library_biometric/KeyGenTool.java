package com.knight.wanandroid.library_biometric;

import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import androidx.annotation.RequiresApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/15 19:02
 * @descript:
 */
public class KeyGenTool {

    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    private static final String KEY_BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    private static final String KEY_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    private static final String TRANSFORMATION = KEY_ALGORITHM + "/" + KEY_BLOCK_MODE + "/" + KEY_PADDING;
    private final String KEY_ALIAS;

    public KeyGenTool(Context context) {
        KEY_ALIAS = context.getPackageName()+"fingerprint";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Cipher getEncryptCipher() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipher;
    }

    /**
     * 获取解密的cipher
     *  加密cipher的一些参数
     *  包括initialize vector(AES加密中 以CBC模式加密需要一个初始的数据块，解密时同样需要这个初始块)
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Cipher getDecryptCipher(byte[] initializeVector) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initializeVector);
            cipher.init(Cipher.DECRYPT_MODE, getKey(), ivParameterSpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipher;
    }

    /**
     * 获取key，首先从秘钥库中根据别名获取key，如果秘钥库中不存在，则创建一个key，并存入秘钥库中
     * @return
     * @throws Exception
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private SecretKey getKey() throws Exception {
        SecretKey secretKey = null;
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);
        if (keyStore.isKeyEntry(KEY_ALIAS)) {
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            secretKey = secretKeyEntry.getSecretKey();
        } else {
            secretKey = createKey();
        }
        return secretKey;
    }


    /**
     * 在Android中，key的创建之后必须存储在秘钥库才能使用
     * @return
     * @throws Exception
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private SecretKey createKey() throws Exception {
        //在创建KeyGenerator的时候，第二个参数指定provider为AndroidKeyStore，这样创建的key就会被存放在这个秘钥库中
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM, ANDROID_KEY_STORE);
        KeyGenParameterSpec spec = new KeyGenParameterSpec
                .Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KEY_BLOCK_MODE)
                .setEncryptionPaddings(KEY_PADDING)
                //这个设置为true，表示这个key必须是通过了用户认证才可以使用
                .setUserAuthenticationRequired(true)
                .build();
        keyGenerator.init(spec);
        return keyGenerator.generateKey();
    }
}
