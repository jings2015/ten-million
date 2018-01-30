package top.godtm.core.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * date: 2018/1/30
 * author: wt
 */
@Slf4j
public class CacheUtil {

    public static String DLT_PREFIX = "DLT_";

    private static LoadingCache<String, Object> loadingCache = CacheBuilder.newBuilder().initialCapacity(100)
            .maximumSize(1000).expireAfterAccess(12, TimeUnit.HOURS).build(
                    new CacheLoader<String, Object>() {
                        @Override
                        public String load(String s) throws Exception {
                            return "null";
                        }
                    }
            );

    public static void set(String key, Object value) {
        loadingCache.put(key, value);
    }

    public static Object get(String key) {
        try {
            Object value = loadingCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            log.error("localCache get Error", e);
        }
        return null;
    }
}
