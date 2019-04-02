package concurrency;

import generics.Fibonacci;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<String>> result = new ArrayList<>();
        for (int i=0;i<5;i++){
            result.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> future : result){
              try {
                System.out.println(future.get());
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


class TaskWithResult implements Callable<String> {

    private int id ;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call(){
        Fibonacci gen = new Fibonacci();
        int result =0;
        for (int i = 0;i<id+1;i++)
            result += gen.next();
        return "Result from :"+id +"  fiboSum:"+ result;
    }
}