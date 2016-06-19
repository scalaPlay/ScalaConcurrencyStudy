import java.net.{Socket, ServerSocket}
import java.util.concurrent.{Executors, Executor, ExecutorService}

/**
  * Created by kohunmin on 2016. 6. 20..
  */
class NetworkService(port: Int, poolSize: Int) extends Runnable{
  val serverSocket = new ServerSocket(port)
  val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

  def run(): Unit = {
    try{
      while(true) {
        // This will block until a connection comes in
        val socket = serverSocket.accept()
        pool.execute(new Handler(socket))
      }
    }finally {
      pool.shutdown()
    }
  }
}

class Handler(socket: Socket) extends Runnable {
  def message = (Thread.currentThread().getName() + "\n").getBytes

  def run(): Unit = {
    socket.getOutputStream.write(message)
    socket.getOutputStream.close()
  }
}

(new NetworkService(2020,2)).run
