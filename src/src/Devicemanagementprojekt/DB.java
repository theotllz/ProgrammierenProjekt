package src.Devicemanagementprojekt;

import java.util.ArrayList;
import java.util.List;

public class DB {
    private List<Device> deviceList;
    private List<User> userList;


    public DB() {
        List<Device> deviceslist = new ArrayList<>();
        this.deviceList = deviceslist;
        List<User> userList = new ArrayList<>();
        this.userList = userList;

        //hardcoded:
        Device device1 = new Device("iPhone 13pro");
        Device device2 = new Device("Dell XPS 15");
        deviceList.add(device1);
        deviceList.add(device2);
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public List<User> getUserList() {
        return userList;
    }

}
