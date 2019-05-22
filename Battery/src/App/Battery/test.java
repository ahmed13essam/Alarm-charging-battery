/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.Battery;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * @author PC
 */
public class test {

    private static File f;
    private static FileChannel channel;
    private static FileLock lock;
    private BatteryJFrame frame;

    public void Run() {
        try {
            f = new File("Battery.lock");
            if (f.exists()) {
                f.delete();
            }
            channel = new RandomAccessFile(f, "rw").getChannel();
            lock = channel.tryLock();
            if (lock == null) {
                channel.close();
                throw new RuntimeException("the Application Battery is Ruuning");
            }

            Thread shutdown = new Thread(new Runnable() {
                @Override
                public void run() {
                    unlock();
                }
            });

            Runtime.getRuntime().addShutdownHook(shutdown);
            frame = new BatteryJFrame();
            frame.read();
            if (frame.dti.frame.t.isRunning()) {
                frame.setVisible(false);
            } else {
                frame.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    //check if Application Is Not Ruunig
    public static void unlock() {
        try {
            if (lock != null) {
                lock.release(); // Clear the Lock
                channel.close(); //Close The Channel
                f.delete(); //Delete The File
            }
        } catch (Exception e) {
        }
    }

}
