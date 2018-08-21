package ru.seet61.tarantooltestapp;

import org.tarantool.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("10.78.221.57", 3301));
            TarantoolConnection con = new TarantoolConnection("tokens_user", "tokens_pwd", socket);
            List<?> list = con.select(1000,0, Collections.emptyList(),0,1024,0);
            System.out.println("list.size(): " + list.size());
            List tuple = new ArrayList();
            /* insert */
            /*tuple.add("service.sap.bpmint");
            tuple.add("BPMLOADER=syongzgtrtl5sy4iiryrj2l2");
            tuple.add(".ASPXAUTH=F0C6FABC73A8267DF4803C27729C51B03CBD8EA8C8161B534FD8201422E9C1DA8A3DAD");
            tuple.add("UserName=115, 101, 114, 118, 105, 99, 101, 46, 115, 97, 112, 46, 98, 112, 109, 105, 110, 116");
            tuple.add("");
            list = con.insert(1000, tuple);
            System.out.println("list: " + list);*/

            /* select */
            tuple = new ArrayList();
            tuple.add("service.bpmint");
            list = con.select(1000,0, tuple,0,1,0);
            if (list.size() > 0) {
                List value = (List) list.get(0);
                System.out.println("select: " + value.get(0) + " " + value.get(1));
            }

            /* update */
            List operation = new ArrayList();
            operation.add("=");
            operation.add(5);
            operation.add("bpmsessionid");
            list = con.update(1000, tuple, operation);
            System.out.println("update: " + list);

            /* delete by index after search */
            /*list = con.delete(1000, tuple);
            System.out.println("delete: " + list);*/


            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
