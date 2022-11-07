package beijing.transport.beijing_proj.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author Jinglin
 */
public class GongjiaoResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public GongjiaoResponse code(HttpStatus status) {
        put("code", status.value());
        return this;
    }

    public GongjiaoResponse message(String message) {
        put("message", message);
        return this;
    }

    public GongjiaoResponse data(Object data) {
        put("data", data);
        return this;
    }

    public GongjiaoResponse success() {
        code(HttpStatus.OK);
        put("success", true);
        return this;
    }

    public GongjiaoResponse fail() {
        code(HttpStatus.INTERNAL_SERVER_ERROR);
        put("success", false);
        return this;
    }

    @Override
    public GongjiaoResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
