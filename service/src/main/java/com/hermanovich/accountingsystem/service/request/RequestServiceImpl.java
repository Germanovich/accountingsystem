package com.hermanovich.accountingsystem.service.request;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.dao.user.UserDao;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

    @Value("${setting.configurationNameForCreationRequest:requestFunctionEnabled}")
    private String configurationNameForCreationRequest;
    private final RequestDao requestDao;
    private final CarDao carDao;
    private final UserDao userDao;
    private final SettingDao settingDao;

    @Transactional
    @Override
    public void addRequest(Request request) {
        if (Boolean.FALSE.equals(settingDao.getSettingByName(configurationNameForCreationRequest).getAccess())) {
            throw new BusinessException(MessageForUser.MESSAGE_REQUEST_CREATION_IS_PROHIBITED.get());
        }
        verifyRequestData(request);
        request.setCar(carDao.getById(request.getCar().getId()));
        request.setUser(userDao.getByLogin(request.getUser().getLogin()));
        if (requestDao.getRequestByCarIdAndUserId(request.getCar().getId(), request.getUser().getId()) != null) {
            throw new BusinessException(MessageForUser.MESSAGE_USER_ALREADY_HAS_A_REQUEST.get());
        }
        requestDao.save(request);
    }

    @Transactional
    @Override
    public void removeRequest(Integer id) {
        Request request = requestDao.getById(id);
        if (request == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_REQUEST_EXISTS.get());
        }
        requestDao.delete(id);
    }

    @Transactional
    @Override
    public void updateRequest(Request request) {
        if (request.getId() == null || requestDao.getById(request.getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_REQUEST_IS_NOT_FOUND.get());
        }
        if (userDao.getByLogin(request.getUser().getLogin()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_USER_EXISTS.get());
        }
        verifyRequestData(request);
        request.setCar(carDao.getById(request.getCar().getId()));
        request.setUser(userDao.getById(request.getUser().getId()));
        requestDao.update(request);
    }

    @Transactional
    @Override
    public Request getRequest(Integer id) {
        Request request = requestDao.getById(id);
        if (request == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_REQUEST_EXISTS.get());
        }
        return request;
    }

    @Transactional
    @Override
    public List<Request> getRequestList() {
        return requestDao.getAll();
    }

    private void verifyRequestData(Request request) {
        if (request.getCar() == null ||
                request.getUser() == null ||
                request.getCar().getId() == null ||
                request.getUser().getLogin() == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_REQUEST_DATA.get());
        }
        request.setCar(carDao.getById(request.getCar().getId()));
        if (request.getCar() == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CAR_EXISTS.get());
        }
        if (request.getCar().getCarStatus() == CarStatus.AVAILABLE) {
            throw new BusinessException(MessageForUser.MESSAGE_CAR_AVAILABLE.get());
        }
    }
}
