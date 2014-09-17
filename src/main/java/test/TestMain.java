package test;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class TestMain {

    /**
     * @param args
     * @throws URISyntaxException 
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws URISyntaxException, UnknownHostException {
        // TODO Auto-generated method stub
        String value = "[\"123\",\"456\",\"789\"]";
        System.out.println(value.charAt(0) + " " + value.charAt(value.length() -1));
        String[] tmp = getStringListOfLionValue(value);
        for (int i = 0; i < tmp.length; i++) {
            System.out.println(tmp[i]);
        }
        
        String value2 = "{\"value类型\":\"请使用map\",\"WATCH_FILE\":\"需要监控的日志文件本地绝对路径（必填）\",\"ROLL_PERIOD\":\"日志rotate周期，默认为3600秒（选填）\",\"BUFFER_SIZE\":\"一次读磁盘的量，默认4KB（选填）\"}";
        String[][] res = getStringMapOfLionValue(value2);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                System.out.println(res[i][j]);
            }
        }
        
        long date = new Date().getTime();
        System.out.println(date);
        long result = getClosestRollTs(date, 3600);
        System.out.println(result);
        
        
        SortedSet<String> hahaSet = new TreeSet<String>();
        hahaSet.add("cdafe");
        hahaSet.add("123fcf");
        hahaSet.add("cedsf");
        hahaSet.add("fewe");
        for (String string : hahaSet) {
            System.out.println(string);
        }
        System.out.println("hour");
        areaOfRecovery(3600l, 1385276400000l, 1385316000000l);
        System.out.println("min");
        areaOfRecovery(60l, 1385276400000l, 1385277960000l);
        
        
        System.out.println("========test jar:file=======================");
        
//        URL jarURL =  TestMain.class.getProtectionDomain().getCodeSource().getLocation();
//        String locationString = jarURL.toString() + ":blackhole.log";
        URL fileURL=TestMain.class.getClass().getResource("/blackhole.log");
        System.out.println(fileURL);
        
        try {
            throw new Exception("message pass");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("========getLatestRotateRollTsUnderTimeBuf=======================");
        long testTs2 = 1386921595000l; //2013-12-13 15:59:55
        long testTs3 = 1386921594000l; //2013-12-13 15:59:54
        long testTs4 = 1386921601000l; //2013-12-13 16:00:01
        result = getLatestRotateRollTsUnderTimeBuf(testTs2, 3600, 5000);
        System.out.println(result);//1386918000000
        result = getLatestRotateRollTsUnderTimeBuf(testTs3, 3600, 5000);
        System.out.println(result);//1386914400000
        result = getLatestRotateRollTsUnderTimeBuf(testTs4, 3600, 5000);
        System.out.println(result);//1386918000000
        
        String supervisorHostsStr = "".replaceAll("\\s*", "");
        String[] supervisorHosts = supervisorHostsStr.split(",");
        for (int j = 0; j < supervisorHosts.length; j++) {
            System.out.println(supervisorHosts[j]);
        }
        System.out.println("========test applog file name after gz=======================");
        String hostName = "mobile-wap-m-web01.nh";
        String appTailFile = "/data/applogs/mobile-wap-web/logs/marinlog.log";
        String rollIdent = "2013-12-17.04";
        File realFile = new File(appTailFile.substring(0, appTailFile.lastIndexOf('/') + 1) + hostName + "__"
                + hostName.substring(0, hostName.lastIndexOf('.') - 2)
                + "." + appTailFile.substring(appTailFile.lastIndexOf('/') + 1)
                + "." + rollIdent + ".gz");
        System.out.println(realFile.getPath());
        
        System.out.println("========test2 applog file name after gz=======================");
        appTailFile = "/tmp/testlogs/redirect_log.log";
        File dirFile = new File("/tmp/testlogs");
        File testFile = new File("/tmp/testlogs/cps-redirect-web01.nh__open-cps-service.redirect_log.log.2013-12-17.04.gz");
        try {
            dirFile.mkdir();
            testFile.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        File iwantFile = findGZFileByIdent("/tmp/testlogs/redirect_log.log", rollIdent);
        System.out.println(iwantFile);
        testFile.delete();
        dirFile.delete();
        
        System.out.println("========test seek  file connent is '012345'=======================");
        try {
            RandomAccessFile reader = new RandomAccessFile("/tmp/testseek", "r");
            reader.seek(5);
            System.out.println(reader.read() + " 5");
            reader.seek(0);
            System.out.println(reader.read() + " 0");
            reader.seek(7);
            System.out.println(reader.read() + " 7");
            reader.seek(-1);
            System.out.println(reader.read() + " -1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("========test getloaclhost=======================");
        System.out.println(InetAddress.getLocalHost().getHostName());
        System.out.println("========test TimeZone=======================");
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        System.out.println(timeZone.getRawOffset());
        System.out.println("========test random sleep=======================");
        int confLoopFactor = 1;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            if (confLoopFactor < 30) {
                confLoopFactor = confLoopFactor << 1;
                System.out.println("confLoopFactor is " + confLoopFactor);
            }
            int randomSecond = confLoopFactor * (random.nextInt(21) + 40) * 1000;
            System.out.println("Configurations not ready, sleep " + randomSecond/1000 + " second.");
        }
        System.out.println("========test arrays copyof=======================");
        String[] oldHosts = {"1","2","3"};
        String[] newHosts = Arrays.copyOf(oldHosts, oldHosts.length + 1);
        newHosts[newHosts.length - 1] = "4";
        System.out.println(Arrays.toString(newHosts));
        
        System.out.println("========test string[0][0]=======================");
        
        String[][] testarrayStrings = new String[0][0];
        for (int i = 0; i < testarrayStrings.length; i++) {
            System.out.println("hah");
        }
        
        System.out.println("========test \"\".split=======================");
        String emptyString= "\"\"";
        String[] empty = emptyString.split(",");
        
        for (int i = 0; i < empty.length; i++) {
            System.out.println("haha:" + empty[i].substring(1, empty[i].length() -1 ));
        }
        
        System.out.println("========test 1 day last rotate ts under time buf============");
        result = getLatestRotateRollTsUnderTimeBuf(1397564318000l, 86400, 5000);
        System.out.println(result);
        
        System.out.println("========test split=======================");
        emptyString= "haha";
        empty = emptyString.split(",");
        System.out.println(empty.length + Arrays.toString(empty));
        
        System.out.println("========test disjoint=======================");
        List<String> aList = new ArrayList<String>();
        aList.add("test1");
        aList.add("test2");
        aList.add("test3");
        aList.add("test4");
        List<String> bList = new ArrayList<String>();
        bList.add("test1");
        bList.add("test2");
        bList.add("test7");
        List<String> cList = new ArrayList<String>();
        cList.add("test5");
        cList.add("test6");
        cList.add("test7");
        System.out.println(Collections.disjoint(aList, bList));
        System.out.println(Collections.disjoint(aList, cList));
        System.out.println(aList);
        System.out.println(bList);
        System.out.println(cList);
        aList.retainAll(bList);
        System.out.println(aList.size());
        aList = new ArrayList<String>();
        aList.add("test1");
        aList.add("test2");
        aList.add("test3");
        aList.add("test4");
        aList.retainAll(cList);
        System.out.println(aList.size());
        System.out.println("========test ip hostname=======================");
        try {
            InetAddress host;
            if (args.length == 0) {
              host = InetAddress.getLocalHost();
            } else {
              host = InetAddress.getByName(args[0]);
            }
            System.out.println("Host:'" + host.getHostName()
                + "' has address: " + host.getHostAddress());

          } catch (UnknownHostException e) {
            e.printStackTrace();
          }
        System.out.println("=============test linkedlist remove===============");
        List<String> linkList = new LinkedList<String>();
        linkList.add("a");
        linkList.add("b");
        linkList.add("c");
        linkList.add("d");
        linkList.add("e");
        linkList.add("f");
        Map<String,List<String>> map = new HashMap<String, List<String>>();
        map.put("test", linkList);
        removeOdd(map.get("test"));
        for (String now : map.get("test")) {
            System.out.println(now);
        }
        System.out.println("=============test \"\" length===============");
        String emptyString2 = "";
        System.out.println(emptyString2.trim().length());
        emptyString2 = "    ";
        System.out.println(emptyString2.trim().length());
        System.out.println("=============test wildcard===============");
        String wildcard = "/var/lib/docker/devicemapper/mnt/%s/rootfs";
        System.out.println(String.format(wildcard, "sadfaf"));
        System.out.println("=============test sortedSet===============");
        StringBuilder sb = new StringBuilder();
        List<ConsumerGroup> consumerGroups = new ArrayList<ConsumerGroup>();
        consumerGroups.add(new ConsumerGroup("group1", "topic1"));
        consumerGroups.add(new ConsumerGroup("group2", "topic1"));
        consumerGroups.add(new ConsumerGroup("group3", "topic2"));
        consumerGroups.add(new ConsumerGroup("group4", "topic2"));
        consumerGroups.add(new ConsumerGroup("group5", "topic2"));
        consumerGroups.add(new ConsumerGroup("group7", "topic3"));
        consumerGroups.add(new ConsumerGroup("group6", "topic4"));
        consumerGroups.add(new ConsumerGroup("group8", "topic1"));
        SortedSet<ConsumerGroup> groupsSorted  = new TreeSet<ConsumerGroup>(new Comparator<ConsumerGroup>() {
            @Override
            public int compare(ConsumerGroup o1, ConsumerGroup o2) {
                int topicCompareResult = o1.getTopic().compareTo(o2.getTopic());
                return topicCompareResult == 0 ? o1.getGroupId().compareTo(o2.getGroupId()) : topicCompareResult;
            }
        });
        for (ConsumerGroup group : consumerGroups) {
            groupsSorted.add(group);
        }
        for (ConsumerGroup group : groupsSorted) {
            sb.append(group).append("\n");
        }
        System.out.println(sb.toString());
        System.out.println("=============test xmlencode with bean===============");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(bos);
        SimpleBean bean = new SimpleBean(0, 0);
        encoder.writeObject(bean);
        encoder.close();
        System.out.println(bos);
    }
    
    private static List<String> removeOdd(List<String> linkList) {
        for (int j = 0; j < linkList.size(); j++) {
            if (j%2==0) {
                linkList.remove(j);
            }
        }
        return linkList;
    }

    public static File findGZFileByIdent(final String appTailFile, final String rollIdent) {
        try {
            final int indexOfLastSlash = appTailFile.lastIndexOf('/');
            File root = new File(appTailFile.substring(0, indexOfLastSlash + 1)); 
            File[] files = root.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    String gzFileRegex = "[_a-z0-9-\\.]+"
                            + "__"
                            + "[_a-z0-9-\\.]+"
                            + appTailFile.substring(indexOfLastSlash + 1)
                            + "\\."
                            + rollIdent
                            + "\\.gz";
                    System.out.println(gzFileRegex);
                    Pattern p = Pattern.compile(gzFileRegex);
                    return p.matcher(name).matches();
                }
            });
            if (files == null || files.length == 0) {
                return null;
            } else {
                return files[0];
            }
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public static String[] getStringListOfLionValue(String value) {
        if (value.charAt(0) != '[' || value.charAt(value.length() - 1) != ']') {
            return null;
        }
        String[] tmp = value.substring(1, value.length() - 1).split(",");
        String[] result = new String[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            result[i] = tmp[i].substring(1, tmp[i].length() -1 );
        }
        return result;
    }
    
    public static String[][] getStringMapOfLionValue(String value) {
        if (value.charAt(0) != '{' || value.charAt(value.length() - 1) != '}') {
            return null;
        }
        String[] tmp = value.substring(1, value.length() - 1).split(",");
        if (tmp.length == 0) {
            return null;
        }
        String[][] result = new String[tmp.length][2];
        for (int i = 0; i < tmp.length; i++) {
            String[] tmp2 = tmp[i].split(":");
            for (int j = 0; j < 2; j++) {
                result[i][j] = tmp2[j].substring(1, tmp2[j].length() -1 );
            }
        }
        return result;
    }
    
    public static long getClosestRollTs(long ts, long rollPeriod) {
        rollPeriod = rollPeriod * 1000;
        
        if ((ts % rollPeriod) < (rollPeriod/2)) {
            ts = (ts / rollPeriod -1) * rollPeriod;
        } else {
            ts = (ts / rollPeriod) * rollPeriod;
        }
        
        //TODO 1378443602000 will get wrong result
        if (rollPeriod >= 1378443602000l) {
            ts = ts - 1378443602000l;
        }

        return ts;
    }
    
    /*
     * get the stage roll timestamp under a forward delay, for example
     * timebuf is 5000, now is 15:59:56, and rollPeroid is 1 hour, then
     * return ts of 15:00;
     * timebuf is 5000, now is 15:59:54, and rollPeroid is 1 hour, then
     * return ts of 14:00;
     */
    public static long getLatestRotateRollTsUnderTimeBuf(
            long ts, long rollPeriod, long clockSyncBufMillis) {
        rollPeriod = rollPeriod * 1000;
        ts = ts + TimeZone.getTimeZone("Asia/Shanghai").getRawOffset();
        long ret = ((ts + clockSyncBufMillis) / rollPeriod -1) * rollPeriod;
        ret = ret - TimeZone.getTimeZone("Asia/Shanghai").getRawOffset();
        return ret;
    }
    
    public static void areaOfRecovery(long period, long startRollTs, long endRollTs) {
        long recoveryStageCount = (endRollTs - startRollTs) / period / 1000;
        for (int i = 0; i<= recoveryStageCount; i++) {
            long rollTs = startRollTs + period * 1000 * (i);
            System.out.println(rollTs);
        }
    }

}
