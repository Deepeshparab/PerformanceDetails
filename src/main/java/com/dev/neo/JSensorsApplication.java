package com.dev.neo;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.dev.neo.standalone.ConsoleOutput;
import com.dev.neo.util.DBUtil;

@SpringBootApplication
public class JSensorsApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(JSensorsApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(JSensorsApplication.class);
	}

	public void run(String... args) throws Exception {
		System.out.println("Performance Details Started..........");
		Properties prop=load();
		DBUtil dbUtil = new DBUtil(prop);
		//long startTime=System.currentTimeMillis() ;
		try {
			/*-----------System Information--------*/
			String computername=InetAddress.getLocalHost().getHostName();
			
			String command = "powershell.exe  Get-WmiObject Win32_NetworkAdapterConfiguration | Where-Object { $_.IPAddress -ne $Null } | Select-Object -ExpandProperty IPAddress";
			Process powerShellProcess = Runtime.getRuntime().exec(command);
			powerShellProcess.getOutputStream().close();
			String ipaddress;
			InputStreamReader input = new InputStreamReader(powerShellProcess.getInputStream());
			BufferedReader stdout = new BufferedReader(input);
			ipaddress = stdout.readLine();
			System.out.println(ipaddress);
			stdout.close();
			
//			String commandForProcessorName = "powershell.exe Get-WMIObject win32_Processor | select name";
//			Process powerShellProcessForProcessorName = Runtime.getRuntime().exec(commandForProcessorName);
//			powerShellProcessForProcessorName.getOutputStream().close();
//			String processorName;
//			InputStreamReader inputString = new InputStreamReader(powerShellProcessForProcessorName.getInputStream());
//			BufferedReader outputString = new BufferedReader(input);
//			processorName = outputString.readLine();
//			System.out.println(powerShellProcessForProcessorName);
//			outputString.close();
//			stdout.close();
			
			com.sun.security.auth.module.NTSystem NTSystem = new com.sun.security.auth.module.NTSystem();
			String currentUser = NTSystem.getName();
			
			
			System.out.println("System Name : "+computername);
			System.out.println("IP Address : "+ipaddress);
			System.out.println("Current User : "+currentUser);
			System.out.println();
			
			/*-------------------------------------*/
			
			/*--------------------Memory and Drive Space Details------------------------*/
			int mb = 1024*1024;
	        int gb = 1024*1024*1024;
	        
	         /* PHYSICAL MEMORY USAGE */
	        
	      //  System.out.println("\n**** Sizes in Mega Bytes ****\n");
	        com.sun.management.OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
	        com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean)
	        		
	        java.lang.management.ManagementFactory.getOperatingSystemMXBean();
	        
	       // System.out.println("PHYSICAL MEMORY DETAILS \n");

	        long physicalMemorySize = os.getTotalPhysicalMemorySize();
	       // System.out.println("total physical memory : " + physicalMemorySize / mb + "MB ");
	        long physicalMemory=physicalMemorySize / mb;
	        
	        long physicalfreeMemorySize = os.getFreePhysicalMemorySize();
	      //  System.out.println("total free physical memory : " + physicalfreeMemorySize / mb + "MB");
	        long freephysicalMemory=physicalfreeMemorySize / mb;
	        
	        double freephysicalMemoryPercentage = freephysicalMemory * 100 / physicalMemory ;
	        
	        /* DISC SPACE DETAILS */
	        File diskPartition = new File("C:");
	        File diskPartition1 = new File("D:");
	        File diskPartition2 = new File("E:");
	        long totalCapacity = diskPartition.getTotalSpace() / gb;
	        long totalCapacity1 = diskPartition1.getTotalSpace() / gb;
	        long totalCapacity2 = diskPartition2.getTotalSpace() / gb;
	        
	        double freePartitionSpace = diskPartition.getFreeSpace() / gb;
	        double freePartitionSpace1 = diskPartition1.getFreeSpace() / gb;
	        double freePartitionSpace2 = diskPartition2.getFreeSpace() / gb;

	        if(freePartitionSpace <= totalCapacity%10)
	        {
	        	System.out.println("Alert : Low Disk Space in C:");
	        	dbUtil.insertMailDetails(computername, diskPartition.toString(), totalCapacity, freePartitionSpace);
	        }
	        else if(freePartitionSpace1 <= totalCapacity1%10)
	        {
	        	System.out.println("Alert : Low Disk Space in D:");
	        	dbUtil.insertMailDetails(computername, diskPartition1.toString(), totalCapacity1, freePartitionSpace1);
	        }
	        else if(freePartitionSpace1 <= totalCapacity2%10)
	        {
	        	System.out.println("Alert : Low Disk Space in E:");
	        	dbUtil.insertMailDetails(computername, diskPartition2.toString(), totalCapacity2, freePartitionSpace2);
	        }
	        else
	        {
	            System.out.println("Sufficient Disk Space");
	        } 
	        
	       // System.out.println("\n-------------------------");
			/*--------------------------------------------------------------------------*/
	        
	        /*---------------------------------CPU Usage---------------------------------------*/
                long start = System.nanoTime();
               //number of available processors;
                int cpuCount = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
                Random random = new Random(start);
                int seed = Math.abs(random.nextInt());
               // System.out.println("\n \n CPU USAGE DETAILS \n\n");
              //  System.out.println("Starting Test with " + cpuCount + " CPUs and random number:" + seed);
                int primes = 10000;
                //
                long startCPUTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
                start = System.nanoTime();
                while(primes != 0)
                {
                   if(isPrime(seed))
                   {
                       primes--;
                   }
                   seed++;

               }
                float cpuPercent = calcCPU(startCPUTime, start, cpuCount);
               // System.out.println("CPU USAGE : " + cpuPercent + " % ");


                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) 
                {
                	System.out.println(e);
                }
	        
	        
	        /*----------------------------CPU Temperature-------------------------------*/
                
                //boolean guiMode = false;
        		Map<String, String> overriddenConfig = new HashMap<String, String>();

        		ConsoleOutput.showOutput(overriddenConfig);
        		Double averagecputemp=ConsoleOutput.showOutput(overriddenConfig);
        		//System.out.println("CPU Temperature :" + averagecputemp+"° C");

            /*--------------------------------------------------------------------------------------*/    
                
                   RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
                   int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
                   long prevUpTime = runtimeMXBean.getUptime();
                   long prevProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();
                   double cpuUsage;
                   try 
                   {
                       Thread.sleep(500);
                   } 
                   catch (Exception ignored) { }

                   operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                   long upTime = runtimeMXBean.getUptime();
                   long processCpuTime = operatingSystemMXBean.getProcessCpuTime();
                   long elapsedCpu = processCpuTime - prevProcessCpuTime;
                   long elapsedTime = upTime - prevUpTime;

                   cpuUsage = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * availableProcessors));
                /*-------------------------------------------------------------------------------*/
                   
                   long date = new java.util.Date().getTime();
			
			dbUtil.insertRecords(computername,ipaddress,currentUser,physicalMemory,freephysicalMemory,freephysicalMemoryPercentage,totalCapacity,freePartitionSpace,totalCapacity1,freePartitionSpace1,totalCapacity2,freePartitionSpace2,cpuPercent,averagecputemp,date);
		}	
		catch(Exception e)
		{   
			e.printStackTrace();

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
		}
		
		/*long endTime=System.currentTimeMillis() ;
		long seconds=(endTime-startTime)/1000;
		System.out.println("Duration in Seconds :" +seconds );*/
		
		File file=new File("null");
		file.delete();
	}
	
	public static Properties load(){
		Properties props = new Properties();
		try{

			InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
			props.load(resourceStream);
		}
		catch(Exception e){
			e.printStackTrace();

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
		}
		return props;
	}
	
	
	
	public static int calcCPU(long cpuStartTime, long elapsedStartTime, int cpuCount)
    {
         long end = System.nanoTime();
         long totalAvailCPUTime = cpuCount * (end-elapsedStartTime);
         long totalUsedCPUTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime()-cpuStartTime;
         System.out.println("Total CPU Time:" + totalUsedCPUTime + " nanoseconds");
         System.out.println("Total Avail CPU Time:" + totalAvailCPUTime + " nanoseconds");
         float per = ((float)totalUsedCPUTime*100)/(float)totalAvailCPUTime;
         System.out.println("Percentage : "+per);
         return (int)per;
    }

    static boolean isPrime(int n)
    {
        if (n <= 2)
        {
            return n == 2;
        }
        if (n % 2 == 0)
        {
            return false;
        }
        
        for (int i = 3, end = (int)Math.sqrt(n); i <= end; i += 2)
        {
        	if (n % i == 0)
        	{
        		return false;
        	}
        }
        return true;
    }
}
