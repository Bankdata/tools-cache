package dk.bankdata.tools;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.bankdata.tools.domain.JedisNotReadyException;
import dk.bankdata.tools.factory.JedisSentinelPoolFactory;
import dk.bankdata.tools.objects.PersistentCookie;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@RunWith(MockitoJUnitRunner.class)
public class CacheHandlerImplTest {
    @InjectMocks @Spy
    private CacheHandlerImpl cacheHandler;

    @Mock
    private JedisSentinelPoolFactory jedisSentinelPoolFactory;

    @Test
    public void shouldGetCacheString() {
        Jedis jedis = mock(Jedis.class);
        when(jedis.exists("some-key")).thenReturn(true);
        when(jedis.get("some-key")).thenReturn("some-value");

        JedisSentinelPool jedisSentinelPool = mock(JedisSentinelPool.class);
        when(jedisSentinelPool.getResource()).thenReturn(jedis);

        when(jedisSentinelPoolFactory.getPool()).thenReturn(jedisSentinelPool);
        doNothing().when(cacheHandler).isJedisReady();
        Optional<String> s = cacheHandler.get("some-key");

        Assert.assertTrue(s.isPresent());
        Assert.assertEquals("some-value", s.get());
    }

    @Test
    public void shouldGetCacheByte() {
        byte[] bytes = "some-value".getBytes();

        Jedis jedis = mock(Jedis.class);
        when(jedis.exists("some-key".getBytes())).thenReturn(true);
        when(jedis.get("some-key".getBytes())).thenReturn(bytes);

        JedisSentinelPool jedisSentinelPool = mock(JedisSentinelPool.class);
        when(jedisSentinelPool.getResource()).thenReturn(jedis);

        when(jedisSentinelPoolFactory.getPool()).thenReturn(jedisSentinelPool);
        doNothing().when(cacheHandler).isJedisReady();

        Optional<byte[]> b = cacheHandler.get("some-key".getBytes());

        Assert.assertTrue(b.isPresent());
        Assert.assertEquals(bytes, b.get());
    }

    @Test
    public void shouldGetCacheObject() {
        Jedis jedis = mock(Jedis.class);
        when(jedis.exists("some-key")).thenReturn(true);
        when(jedis.get("some-key")).thenReturn("{\"testProp\":\"some-prop\"}");

        JedisSentinelPool jedisSentinelPool = mock(JedisSentinelPool.class);
        when(jedisSentinelPool.getResource()).thenReturn(jedis);

        when(jedisSentinelPoolFactory.getPool()).thenReturn(jedisSentinelPool);
        doNothing().when(cacheHandler).isJedisReady();

        Optional<TestObject> testObject = cacheHandler.get("some-key", TestObject.class);

        Assert.assertTrue(testObject.isPresent());
        Assert.assertEquals("some-prop", testObject.get().getTestProp());
    }

    @Test
    public void shouldGetCacheObjectBytes() {
        byte[] key = "some-key".getBytes();
        byte[] value = "{\"testProp\":\"some-prop\"}".getBytes();

        Jedis jedis = mock(Jedis.class);
        when(jedis.exists(key)).thenReturn(true);
        when(jedis.get(key)).thenReturn(value);

        JedisSentinelPool jedisSentinelPool = mock(JedisSentinelPool.class);
        when(jedisSentinelPool.getResource()).thenReturn(jedis);

        when(jedisSentinelPoolFactory.getPool()).thenReturn(jedisSentinelPool);
        doNothing().when(cacheHandler).isJedisReady();

        Optional<TestObject> testObject = cacheHandler.get(key, TestObject.class);

        Assert.assertTrue(testObject.isPresent());
        Assert.assertEquals("some-prop", testObject.get().getTestProp());
    }

    @Test
    public void shouldGetCacheListOfObjects() {
        Jedis jedis = mock(Jedis.class);
        when(jedis.exists("some-key")).thenReturn(true);
        when(jedis.get("some-key")).thenReturn("[{\"name\":\"some-name-1\",\"value\":\"some-value-1\"}" +
                ",{\"name\":\"some-name-2\",\"value\":\"some-value-2\"}]");

        JedisSentinelPool jedisSentinelPool = mock(JedisSentinelPool.class);
        when(jedisSentinelPool.getResource()).thenReturn(jedis);

        when(jedisSentinelPoolFactory.getPool()).thenReturn(jedisSentinelPool);
        doNothing().when(cacheHandler).isJedisReady();

        Optional<List<PersistentCookie>> list = cacheHandler.getList("some-key", PersistentCookie.class);

        Assert.assertTrue(list.isPresent());

        List<PersistentCookie> persistentCookies = list.get();

        Assert.assertEquals(2, persistentCookies.size());
        Assert.assertEquals("some-name-1", persistentCookies.get(0).getName());
        Assert.assertEquals("some-name-2", persistentCookies.get(1).getName());
        Assert.assertEquals("some-value-1", persistentCookies.get(0).getValue());
        Assert.assertEquals("some-value-2", persistentCookies.get(1).getValue());
    }

    @Test(expected = JedisNotReadyException.class)
    public void shouldThrowRedisNotInitializedException() {
        cacheHandler.get("error");
    }
}