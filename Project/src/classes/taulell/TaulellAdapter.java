package classes.taulell;

/**
 *
 */

public class TaulellAdapter {

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public static <T> T createInstance(Class clazz) throws InstantiationException, IllegalAccessException{
        T t = (T) clazz.newInstance();
        return t;
    }
}