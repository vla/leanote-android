package org.houxg.leamonax.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * SslUtils
 * Created by v.la@live.cn on 2017/2/28.
 */
public class SslUtils {
    public static SSLContext sslContext = null;

    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new MyTrustManager();
        trustAllCerts[0] = tm;
        sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }

    static class MyTrustManager implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

    public static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 忽略HTTPS请求的SSL证书，
     * 个人架设自签名服务端，并不需要SSL验证
     *
     * @throws Exception
     */
    public static void ignoreSsl() throws Exception {
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(DO_NOT_VERIFY);
    }
}
