package kr.osam.core.network;

public interface ApiCallback {
    void onSuccess(Object object);

    void onFailure(Throwable throwable);

}
