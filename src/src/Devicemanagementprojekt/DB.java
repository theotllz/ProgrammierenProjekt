package src.Devicemanagementprojekt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DB implements Serializable {
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
        Device device6 = new Device("iPhone 15pro");
        Device device5 = new Device("iPhone 15");
        Device device4 = new Device("Siemens Fridge");
        Device device3 = new Device("Dyson airwrap");
        deviceList.add(device1);
        deviceList.add(device2);
        deviceList.add(device6);
        deviceList.add(device5);
        deviceList.add(device4);
        deviceList.add(device3);

    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public List<User> getUserList() {
        return userList;
    }

}
