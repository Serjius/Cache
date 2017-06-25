import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by pss on 20.06.17.
 */
public class HelloWorld {
    public static void main(String[] args) throws IgniteException {

      /*  CacheConfiguration cacheCfg = new CacheConfiguration();
        cacheCfg.setName("cacheName");
        cacheCfg.setCacheMode(CacheMode.PARTITIONED);
        cacheCfg.setBackups(1);
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setCacheConfiguration(cacheCfg);

        try (Ignite ignite = Ignition.start(cfg)) {
            //create cache
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cacheCfg); */
        try (Ignite ignite = Ignition.start("work/config/test-config.xml")) {
            //create cache
            IgniteCache<Integer, Integer> cache = ignite.getOrCreateCache("myCache");
            SecureRandom rnd = new SecureRandom();
            //put something to cache
            for (int i = 0; i < 1_000; i++) {
                cache.put(i, rnd.nextInt());
            }

            //try to read in infinity loop and reboot one node
            int i = 0;
            int n = 0;
            int k = 0;
            Integer j = 0;
            while (true) {
                i = rnd.nextInt(1_000);
                //ignite.compute().broadcast(() -> System.out.println(i + " " + cache.get(i)));
                j = cache.get(i);
                if (j == null) n++;
                k++;
                if (k % 1_000_000 == 0) {
                    System.out.println("Total gets: " + k + " failed: " + n);
                }

            }
        }
    }
}
