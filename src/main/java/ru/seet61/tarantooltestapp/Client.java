package ru.seet61.tarantooltestapp;

import org.tarantool.SocketChannelProvider;
import org.tarantool.TarantoolClient;
import org.tarantool.TarantoolClientConfig;
import org.tarantool.TarantoolClientImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TarantoolClientConfig config = new TarantoolClientConfig();
        config.username = "root";
        config.password = "secret";

        SocketChannelProvider socketChannelProvider = new SocketChannelProvider() {
            @Override
            public SocketChannel get(int retryNumber, Throwable lastError) {
                if (lastError != null) {
                    lastError.printStackTrace(System.out);
                }
                try {
                    return SocketChannel.open(new InetSocketAddress("10.78.221.57", 3301));
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        };

        TarantoolClient client = new TarantoolClientImpl(socketChannelProvider, config);


        List l = client.syncOps().call("search_space_id", "tokens");
        int id =(int) ((List) l.get(0)).get(0);
        System.out.println(id);

        List<?> list = client.syncOps().select(id,0, Collections.emptyList(),0,1024,0);
        System.out.println("list.size(): " + list.size());
        List tuple = new ArrayList();
        /* insert */
        tuple.add("service.sap.bpmint");
        tuple.add("BPMLOADER=syongzgtrtl5sy4iiryrj2l2");
        tuple.add(".ASPXAUTH=F0C6FABC73A8267DF4803C27729C51B03CBD8EA8C8161B534FD8201422E9C1DA8A3DAD");
        tuple.add("UserName=115, 101, 114, 118, 105, 99, 101, 46, 115, 97, 112, 46, 98, 112, 109, 105, 110, 116");
        tuple.add("");
        list = client.syncOps().insert(id, tuple);
        System.out.println("list: " + list);


    }


}
