package ga.test.main;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class test {

     public static void main(String[] args) {    

         ThreadPoolExecutor executor = new ThreadPoolExecutor(
        		 //corePoolSize=10  maximumPoolSize=13 
        		 //���̵߳���Ŀ����10����ʱ��,��һ���߳̾ͻ���뵽���������
                 //�˻��������Ĭ����5���߳�,����������е��̳߳���Ĭ��ֵ��ʱ��ͻ����´����߳�,
                 //�����߳���Ŀ���Ϊ13+Ĭ�ϻ�����е�ֵ''
        		 10,   
        		 13,  
        		 200, 
        		 TimeUnit.MILLISECONDS, 
                 new ArrayBlockingQueue<Runnable>(5)  
                 );

          

         for(int i=0;i<25;i++){
        	 int x = 0;
        	 int y = 0;
        	 
             MyTask myTask = new MyTask(i);
             
             y = executor.getPoolSize() + executor.getQueue().size();
             
             while(y > 17) {
            	 y = executor.getPoolSize() + executor.getQueue().size();
             }
             executor.execute(myTask);

             System.out.println("�̳߳����߳���Ŀ��"+executor.getPoolSize()+"�������еȴ�ִ�е�������Ŀ��"+

             executor.getQueue().size()+"����ִ������������Ŀ��"+executor.getCompletedTaskCount());

         }

         executor.shutdown();

     } 

}

 

 

class MyTask implements Runnable {

    private int taskNum;

     

    public MyTask(int num) {

        this.taskNum = num;

    }

     

    @Override

    public void run() {

        System.out.println("����ִ��task "+taskNum);

        try {

            Thread.currentThread();
			Thread.sleep(4000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        System.out.println("task "+taskNum+"ִ�����");

    }

}