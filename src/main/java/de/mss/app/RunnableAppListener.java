package de.mss.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;

public class RunnableAppListener implements Runnable {

   private RunnableApp runApp   = null;
   private String      bindIp   = "localhost";
   private BigInteger  bindPort = BigInteger.valueOf(1234);


   public RunnableAppListener(RunnableApp a) {
      this.runApp = a;
   }


   public void setBindIp(String i) {
      this.bindIp = i;
   }


   public void setBindPort(BigInteger i) {
      this.bindPort = i;
   }


   @Override
   public void run() {
      try (ServerSocket serverSocket = new ServerSocket(this.bindPort.intValue(), 100, InetAddress.getByName(this.bindIp))) {
         while (this.runApp.checkRunning()) {
            handleSocket(serverSocket.accept());
         }
      }
      catch (IOException e) {
         LogManager.getLogger().error("Error while handling Socket", e);
      }
   }


   private void handleSocket(Socket socket) throws IOException {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
         String command = null;
         if ((command = reader.readLine()) != null && "quit".equalsIgnoreCase(command))
            this.runApp.stop();
      }
   }
}
