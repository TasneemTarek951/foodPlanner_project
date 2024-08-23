package Search;

import java.util.List;

public interface ListCallback<T>{
    void onSuccess(List<T> list);
    void onFailure(Throwable t);
}
