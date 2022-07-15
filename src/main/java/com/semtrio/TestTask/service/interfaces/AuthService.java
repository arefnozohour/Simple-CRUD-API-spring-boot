package com.semtrio.TestTask.service.interfaces;

import com.semtrio.TestTask.data.request.ReqLoginData;
import com.semtrio.TestTask.data.response.ResLoginData;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    ResLoginData login( ReqLoginData loginData);
}
