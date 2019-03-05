package testFormThinkingInJava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<String>> futureList = new ArrayList<>();
        for (int i=0;i<10;i++){
            futureList.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> f: futureList ){
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally {
                exec.shutdown();
            }
        }
    }
}

class  TaskWithResult implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "result is from :" + id;
    }
    private int id;
    public TaskWithResult(int id){
        this.id = id;
    }

}