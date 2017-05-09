package zwp.quickly.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>describe：集合操作
 * <p>    note：
 * <p>  author：zwp on 2017/4/7 mail：1101558280@qq.com web: http://www.zwping.win</p>
 * 常用集合类的继承结构如下：
 * Collection<--List<--Vector
 * Collection<--List<--ArrayList
 * Collection<--List<--LinkedList
 * Collection<--Set<--HashSet
 * Collection<--Set<--HashSet<--LinkedHashSet
 * Collection<--Set<--SortedSet<--TreeSet
 * Map<--SortedMap<--TreeMap
 * Map<--HashMap
 * <table>
 * <tr>
 * <th>判断集合是否为空或size为0{@link CollectionUtils#isEmpty(Collection)}</th>
 * </tr>
 * <tr>
 * <th>遍历集合{@link CollectionUtils#traverseCollection(Collection)}</th>
 * </tr>
 * <tr>
 * <th>遍历集合{@link CollectionUtils#join(Iterable)}</th>
 * </tr>
 * <tr>
 * <th>根据position查询item{@link CollectionUtils#findList(List, int)}</th>
 * </tr>
 * <tr>
 * <th>去除list集合中某一位置的值{@link CollectionUtils#removePositionList(List, int)}</th>
 * </tr>
 * <tr>
 * <th>判断某个集合中是否包含某个元素{@link CollectionUtils#listIfContainElement(List, Object)}</th>
 * </tr>
 * <tr>
 * <th>数组转换为集合List{@link CollectionUtils#arrayToList(Object[])}</th>
 * </tr>
 * <tr>
 * <th>数组转换为集合Set{@link CollectionUtils#arrayToSet(Object[])}</th>
 * </tr>
 * <tr>
 * <th>集合list转换为数组{@link CollectionUtils#listToArray(Collection)}</th>
 * </tr>
 * <tr>
 * <th>list转换为set{@link CollectionUtils#listToSet(List)}</th>
 * </tr>
 * <tr>
 * <th>set转换为list{@link CollectionUtils#setToList(Set)}</th>
 * </tr>
 * </table>
 */

public class CollectionUtils {


    private static final String DELIMITER = ",";

    /**
     * Judge whether a collection is null or size is 0
     *
     * @param c
     * @param <V>
     * @return
     */
    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }

    public static <V> boolean isNotEmpty(Collection<V> c) {
        return !isEmpty(c);
    }

    /**
     * Join collection to string, separator is {@link #DELIMITER}
     *
     * @param tokens
     * @return
     */
    public static String join(Iterable tokens) {
        return tokens == null ? "" : TextUtils.join(DELIMITER, tokens);
    }

    /**
     * Convert array to list
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> arrayToList(T... array) {
        return Arrays.asList(array);
    }

    /**
     * Convert array to set
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> Set<T> arrayToSet(T... array) {
        return new HashSet<T>(arrayToList(array));
    }

    /**
     * Convert collection to array
     *
     * @param c
     * @return
     */
    public static Object[] listToArray(Collection<?> c) {
        if (!isEmpty(c)) {
            return c.toArray();
        }
        return null;
    }

    /**
     * Convert list to set
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Set<T> listToSet(List<T> list) {
        return new HashSet<T>(list);
    }

    /**
     * Convert set to list
     *
     * @param set
     * @param <T>
     * @return
     */
    public static <T> List<T> setToList(Set<T> set) {
        return new ArrayList<T>(set);
    }

    /**
     * Traverse collection
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> String traverseCollection(Collection<T> c) {
        if (!isEmpty(c)) {
            int len = c.size();
            StringBuilder builder = new StringBuilder(len);
            int i = 0;
            for (T t : c) {
                if (t == null) {
                    continue;
                }
                builder.append(t.toString());
                i++;
                if (i < len) {
                    builder.append(DELIMITER);
                }
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 根据position查询item
     */
    public static <T> T findList(List<T> list, int position) {
        T data = null;
        for (int i = 0; i < list.size(); i++) {
            if (position == i) {
                data = list.get(i);
                break;
            }
        }
        return data;
    }

    /**
     * 去除list集合中某一位置的值
     *
     * @param list
     * @param position
     * @param <T>
     * @return
     */
    public static <T> List<T> removePositionList(List<T> list, int position) {
        List<T> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                continue;
            }
            data.add(list.get(i));
        }
        return data;
    }

    /**
     * 判断某个集合中是否包含某个元素
     *
     * @param list
     * @param element
     * @param <T>
     * @return
     */
    public static <T> boolean listIfContainElement(List<T> list, T element) {
        if (isEmpty(list)) return false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == element) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个集合中是否不包含某个元素
     *
     * @param list
     * @param element
     * @param <T>
     * @return
     */
    public static <T> boolean listIfNoContainElement(List<T> list, T element) {
        return !listIfContainElement(list, element);
    }
}
