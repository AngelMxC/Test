package com.angelmxc.test.test;

import java.util.List;

public abstract class RequestCallback {
    public abstract void onSuccess(List<Animal> listaDeAnimales);
    public abstract void onError(String str_error);
}
