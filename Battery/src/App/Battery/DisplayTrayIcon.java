/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.Battery;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class DisplayTrayIcon {

    static TrayIcon trayIcon;
    static BatteryJFrame frame = new BatteryJFrame();
    final static SystemTray tray = SystemTray.getSystemTray();
    final PopupMenu puMenu = new PopupMenu();

    public DisplayTrayIcon() {
        ShowTrayIcon();

    }

    public void ShowTrayIcon() {
        if (!SystemTray.isSupported()) {
            System.out.println("Error Your Pc");
            System.exit(0);
            return;
        }
        Battery.SYSTEM_POWER_STATUS batteryStatus = new Battery.SYSTEM_POWER_STATUS(); // informatio batttery
        Battery.INSTANCE.GetSystemPowerStatus(batteryStatus);  // informatio batttery

        trayIcon = new TrayIcon(CreateIcon("/img/battery_2.png", "Battery Charging Alarm"));

//        trayIcon.setToolTip("Version 1.0.0\nBattery Charging Alarm\nBattery charging status : " + batteryStatus.getACLineStatusString() + "\nBattery Life : " + batteryStatus.getBatteryLifePercent() + "\nBattery Left : " + batteryStatus.getBatteryLifeTime() + "");
        Menu Displaymenu = new Menu("Menu");
        //add Components / menuitems
        MenuItem OpenItems = new MenuItem("Open Main Window");
        MenuItem RunItems = new MenuItem("Run");
        MenuItem StopItems = new MenuItem("Stop");
        MenuItem AboutItems = new MenuItem("About");
        MenuItem ExitItems = new MenuItem("Exit");

        puMenu.add(OpenItems);
        puMenu.addSeparator();
        puMenu.add(RunItems);
        puMenu.add(StopItems);
        puMenu.addSeparator();
        puMenu.add(AboutItems);
        puMenu.addSeparator();
        puMenu.add(ExitItems);
        trayIcon.setPopupMenu(puMenu);

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //checke jframe minmize
                if (frame.getState() == Frame.ICONIFIED) {
                    frame.setState(Frame.NORMAL);
                }
                // checke visible hiddin or no
                if (!frame.isVisible()) {
                    frame.setVisible(true); // show FRame Hewan the Frame Closed
                }
            }
        });

        //populate the message display menu
        MenuItem ErrorItems = new MenuItem("Error Trigger");
        MenuItem WarningItems = new MenuItem("Warning Trigger");
        MenuItem NormalItems = new MenuItem("Normal Trigger");
        MenuItem InfoItems = new MenuItem("Info Trigger");

        Displaymenu.add(ErrorItems);
        Displaymenu.add(WarningItems);
        Displaymenu.add(NormalItems);
        Displaymenu.add(InfoItems);

        OpenItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //checke jframe minmize
                if (frame.getState() == Frame.ICONIFIED) {
                    frame.setState(Frame.NORMAL);
                }
                // checke visible hiddin or no
                if (!frame.isVisible()) {

                    frame.setVisible(true); // show FRame Hewan the Frame Closed
                }

            }
        });
        //Run Aplication
        RunItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!frame.t.isRunning()) {
                    frame.counter = 1;
                    frame.RunBattery();
                    frame.FirtsRun = 0;
                    frame.t.start();
                    puMenu.getItem(2).setEnabled(false);
                    puMenu.getItem(3).setEnabled(true);

                }
            }
        });

        //StopApplication
        StopItems.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (frame.t.isRunning()) {
                    frame.t.stop();
                    puMenu.getItem(3).setEnabled(false);
                    puMenu.getItem(2).setEnabled(true);
                }

            }
        });
        //About Application
        AboutItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Battery Charging Alarm\nMade by Ahmed Essam\nTo communicate with me\nAhmed.13esam@gmail.com", "Battery Charging Alarm", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        ExitItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frame.select) {
                    frame.f.delete();
                }
                tray.remove(trayIcon);
                System.exit(0);
            }
        });

        try {
            tray.add(trayIcon);
        } catch (Exception e) {
        }

    }

    protected static Image CreateIcon(String Path, String desc) {
        URL ImageUrl = DisplayTrayIcon.class.getResource(Path);
        return (new ImageIcon(ImageUrl, desc)).getImage();

    }
}
