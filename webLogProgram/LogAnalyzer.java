
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         // complete constructor
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         // complete method
         FileResource fr = new FileResource(filename);
         for(String line : fr.lines()) {
             LogEntry le = WebLogParser.parseEntry(line);
             records.add(le);
         }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIPs() {
         ArrayList<String> ip = new ArrayList<String>();
         for (LogEntry le : records){
             String ipS = le.getIpAddress();
             if(!ip.contains(ipS))
                ip.add(ipS);
         }
         return ip.size();
     }
     
     public void printAllHigherThanNum(int num) {
         ArrayList<Integer> al = new ArrayList<Integer>();
         for(LogEntry le : records) {
             if(le.getStatusCode()>num){
                System.out.println(le);
                if(!al.contains(le.getStatusCode()))
                    al.add(le.getStatusCode());
             }
         }
         for(int i : al){
             System.out.println(i);
         }
     }
     public ArrayList<String> uniqueIPVisitsOnDay(String date) {
         ArrayList<String> al = new ArrayList<String>();
         for(LogEntry le : records) {
             String str = le.toString();
             if(str.contains(date)) {
                 String ip = le.getIpAddress();
                 if(!al.contains(ip))
                    al.add(ip);
             }
         }
         return al;
     }
     public int countUniqueIPsInRange(int low, int high){
         ArrayList<String> al = new ArrayList<String>();
         for(LogEntry le : records) {
             int sc = le.getStatusCode();
             if(sc >= low && sc <= high) {
                 String ip = le.getIpAddress();
                 if(!al.contains(ip))
                    al.add(ip);
             }
         }
         return al.size();
     }
     public HashMap<String,Integer> countVisitsPerIP () {
         HashMap<String,Integer> hm = new HashMap<String,Integer>();
         for(LogEntry le : records) {
             String ip = le.getIpAddress();
             if(hm.containsKey(ip))
                hm.put(ip,hm.get(ip)+1);
             else
                hm.put(ip,1);
         }
         return hm;
     }
     public int mostNumberVisitsByIP() {
         HashMap<String,Integer> hm = countVisitsPerIP();
         int mostVisits = 0;
         for(int value : hm.values()) {
             if(value > mostVisits)
                mostVisits = value;
         }
         return mostVisits;
     }
     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> hm){
         ArrayList<String> ip = new ArrayList<String>();
         int mostVisits = mostNumberVisitsByIP();
         for(String s : hm.keySet()) {
             if(hm.get(s) == mostVisits)
                ip.add(s);
         }
         return ip;
     }
     public HashMap<String,ArrayList<String>> iPsForDays() {
         HashMap<String,ArrayList<String>> hm = new HashMap<String,ArrayList<String>>();
         for(LogEntry le : records) {
             String ip = le.getIpAddress();
             String date = le.getAccessTime().toString().substring(4,10);
             if(hm.containsKey(date)) {
                 ArrayList<String> al = hm.get(date);
                 al.add(ip);
                 hm.put(date,al);
             }
             else {
                 ArrayList<String> al = new ArrayList<String>();
                 al.add(ip);
                 hm.put(date,al);
             }
         }
         return hm;
     }
     public String dayWithMostIPVisits ( HashMap<String,ArrayList<String>> hm) {
         String date = "";
         int counter = 0;
         for(String s : hm.keySet()) {
             if(hm.get(s).size() > counter){
                counter = hm.get(s).size();
                date = s;
             }
         }
         return date;
     }
     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> hm,String date) {
         ArrayList<String> ipsOnDate = hm.get(date);
         HashMap<String,Integer> hashSorter = new HashMap<String,Integer>();
         for(String s : ipsOnDate) {
             if(hashSorter.containsKey(s))
                hashSorter.put(s,hashSorter.get(s)+1);
             else
                hashSorter.put(s,1);
         }
         int ipCounter = 0;
         for(int i : hashSorter.values()) {
             if(i>ipCounter)
                ipCounter = i ;
         }
         ArrayList<String> answer = new ArrayList<String>();
         for(String s : hashSorter.keySet()) {
             if(hashSorter.get(s) == ipCounter)
                answer.add(s);
         }
         return answer;
     }
}
