package test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class testLogger {
    static Logger LOG;
    
    public static String create(){
        String str = null;
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
        byte[] b = new byte[2];
        b[0] = (new Integer(hightPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        try {
            str = new String(b, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }//转成中文
        return str;
    }
    
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";   //生成字符串从此序列中取
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
     }
    
    public static long getTS() {
        Date now = new Date();
        return now.getTime();
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println(getRandomString(10));
        DailyRollingFileAppender appender = new DailyRollingFileAppender();
        appender.setLayout(new PatternLayout("%m%n"));
        appender.setFile(args[0]);
        appender.setDatePattern("'.'yyyy-MM-dd.HH.mm");
        appender.setThreshold(Level.DEBUG);
        appender.activateOptions();
        Logger.getRootLogger().addAppender(appender);
        LOG = Logger.getLogger(testLogger.class);

        int i =0;
        while (true) {
            StringBuffer sb = new StringBuffer();
            i++;
            sb.append(i);
            sb.append(' ');
            sb.append(Long.toString(testLogger.getTS()));
            sb.append(' ');
            for (int j = 0; j < Integer.parseInt(args[1]); j++) {
                sb.append(getRandomString(1));
            }
            LOG.debug(sb.toString());
            Thread.sleep(Integer.parseInt(args[2]));
        }
    }
}

