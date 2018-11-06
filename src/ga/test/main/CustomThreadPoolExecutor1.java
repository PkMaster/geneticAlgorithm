package ga.test.main;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPoolExecutor1 {  
	  
      
    private ThreadPoolExecutor pool = null;  
      
      
    /** 
     * �̳߳س�ʼ������ 
     *  
     * corePoolSize �����̳߳ش�С----1 
     * maximumPoolSize ����̳߳ش�С----3 
     * keepAliveTime �̳߳��г���corePoolSize��Ŀ�Ŀ����߳������ʱ��----30+��λTimeUnit 
     * TimeUnit keepAliveTimeʱ�䵥λ----TimeUnit.MINUTES 
     * workQueue ��������----new ArrayBlockingQueue<Runnable>(5)====5�������������� 
     * threadFactory �½��̹߳���----new CustomThreadFactory()====���Ƶ��̹߳��� 
     * rejectedExecutionHandler ���ύ����������maxmumPoolSize+workQueue֮��ʱ, 
     *                          �����ύ��41������ʱ(ǰ���̶߳�û��ִ����,�˲��Է�������sleep(100)), 
     *                                ����ύ��RejectedExecutionHandler������ 
     */  
    public void init() {  
        pool = new ThreadPoolExecutor(  
                1,  
                3,  
                30,  
                TimeUnit.MINUTES,  
                new ArrayBlockingQueue<Runnable>(5),  
                new CustomThreadFactory(),  
                new CustomRejectedExecutionHandler());  
    }  
  
      
    public void destory() {  
        if(pool != null) {  
            pool.shutdownNow();  
        }  
    }  
      
      
    public ExecutorService getCustomThreadPoolExecutor() {  
        return this.pool;  
    }  
      
    private class CustomThreadFactory implements ThreadFactory {  
  
        private AtomicInteger count = new AtomicInteger(0);  
          
        @Override  
        public Thread newThread(Runnable r) {  
            Thread t = new Thread(r);  
            String threadName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);  
            System.out.println(threadName);  
            t.setName(threadName);  
            return t;  
        }  
    }  
      
      
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {  
  
        @Override  
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {  
        	try {
                                // ���ĸ���㣬��blockingqueue��offer�ĳ�put��������
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }  
    }  
      
      
      
    // ���Թ�����̳߳�  
    public static void main(String[] args) {  
    	
        CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();  
        // 1.��ʼ��  
        exec.init();  
          
        ExecutorService pool = exec.getCustomThreadPoolExecutor();  
        for(int i=1; i<100; i++) {  
            System.out.println("�ύ��" + i + "������!");  
            pool.execute(new Runnable() {  
                @Override  
                public void run() {  
                    try {  
                    	System.out.println(">>>task is running====="); 
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            });  
        }  
          
          
        // 2.����----�˴���������,��Ϊ����û���ύִ����,��������̳߳�,����Ҳ���޷�ִ����  
        // exec.destory();  
          
        try {  
            Thread.sleep(10000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
} 

