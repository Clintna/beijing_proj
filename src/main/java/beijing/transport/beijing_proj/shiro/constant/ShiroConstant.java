package beijing.transport.beijing_proj.shiro.constant;

public class ShiroConstant {
    /**
     * 随机盐的位数
     **/
    public static final int SALT_LENGTH = 8;
    /**
     * hash的散列次数
     **/
    public static final int HASH_ITERATORS = 1024;

    public interface HASH_ALGORITHM_NAME {
        String MD5 = "MD5";
    }
}
