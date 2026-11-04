package order;

import eventbroker.Event;

import java.util.HashSet;
import java.util.Set;


public class BlacklistOrderProcessor extends OrderProcessor {

    private final Set<String> blacklist;

    public static synchronized BlacklistOrderProcessor make_BlacklistOrderProcessor(int numThreads){
        return new BlacklistOrderProcessor(numThreads);
    }
    private BlacklistOrderProcessor(int numThreads){
        super(numThreads);
        
        // some work to get to the blacklist data
        try {
            Thread.sleep(10);
        } catch (InterruptedException _) {}
       
        // add the blacklisted customers
        blacklist = new HashSet<String>();
        blacklist.add("Jan");
    }

    @Override
    public void handleEvent(Event e) {
        OrderEvent order = (OrderEvent) e;

        if(blacklist.contains(order.getCustomer())){
            System.out.println("Order of customer "+order.getCustomer()+" discarded");
            return;
        }
        super.threadPool.execute(new OrderProcessorTask(order));
        processedOrders++;
    }
}
