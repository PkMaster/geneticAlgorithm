package ga.aps.controller.myConnection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import ga.aps.controller.entity.APScs_Thread_parameters;

public class ThreadPool {	
	private ExecutorService threadPool = null;
	
	public ThreadPool(APScs_Thread_parameters ThreadPara) {
		//��������
		BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<Runnable>(ThreadPara.getWorkQueue());  
		//CallerRunsPolicy() ����>(����߳�+��������)�򴴽��µ����߳�
		RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();	
		//�̳߳�
		threadPool = new ThreadPoolExecutor(
				ThreadPara.getCorePoolSize(),     //�����߳���
				ThreadPara.getMaximumPoolSize(),  //����߳���
				ThreadPara.getKeepAliveTime(),	  //����ʱ��
				TimeUnit.MILLISECONDS,			  //��λ
				workingQueue, 					  //��������
				rejectedExecutionHandler);        //Exception
		
		//��ȡfuture�з���ֵ
		//completionService = new ExecutorCompletionService<String>(threadPool);
	}
	
	public ExecutorService getThreadPool() throws Exception{
		return threadPool;		
	}
	
	public void ThreadShutdown() throws Exception{
		threadPool.shutdown();
	}
}
