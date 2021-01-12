package com.hermanovich.accountingsystem.service.request;

import com.hermanovich.accountingsystem.model.request.Request;

import java.util.List;

public interface RequestService {

    void addRequest(Request request);

    void removeRequest(Integer id);

    void updateRequest(Request request);

    Request getRequest(Integer id);

    List<Request> getRequestList();
}
