package com.ding.frame.servlet;

import com.ding.frame.annotation.DingAutowired;
import com.ding.frame.annotation.DingRequestMapping;
import com.ding.frame.annotation.DingRestController;
import com.ding.frame.annotation.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingzhongfa
 * @email : 71964899@qq.com
 * @date 2019-07-01 下午10:01
 */
public class DispacherServlet extends HttpServlet {

    private static final List<String> CLASS_NAMES = new ArrayList<String>();

    private static final Map<String, Object> beans = new HashMap<String, Object>();

    @Override
    public void init() throws ServletException {
        doBasePackage("com.ding");
        doInnstance();
        doAutowired();
        urlHanding();
    }

    private void urlHanding() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instannce = entry.getValue();
            Class clazz = instannce.getClass();
            if (clazz.isAnnotationPresent(DingRestController.class)){
                DingRequestMapping mapping = (DingRequestMapping) clazz.getAnnotation(DingRequestMapping.class);
                String requestPath = mapping.value();
                Method [] methods = clazz.getMethods();
            }
        }
    }

    private void doAutowired() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instannce = entry.getValue();
            Class clazz = instannce.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(DingAutowired.class)) {
                    DingAutowired dingAutowired = field.getAnnotation(DingAutowired.class);
                    String key = dingAutowired.value();
                    field.setAccessible(true);
                    try {
                        field.set(instannce, beans.get(key));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void doInnstance() {

        for (String classname : CLASS_NAMES) {
            String cn = classname.replace(".class", "");
            try {
                Class clazz = Class.forName(cn);
                if (clazz.isAnnotationPresent(DingRestController.class)) {
                    Object instance = clazz.newInstance();

                    // key
                    DingRestController map1 = (DingRestController) clazz.getAnnotation(DingRestController.class);
                    String key = map1.value();
                    beans.put(key, instance);
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Object instance = clazz.newInstance();
                    Service service = (Service) clazz.getAnnotation(Service.class);
                    String key = service.value();
                    beans.put(key, instance);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

    }

    private void doBasePackage(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
        String fileStr = url.getFile();
        File file = new File(fileStr);
        String[] filesStr = file.list();
        for (String path : filesStr) {
            File filePath = new File(fileStr + path);
            if (filePath.isDirectory()) {
                doBasePackage(basePackage + "." + path);
            } else {
                CLASS_NAMES.add(basePackage + filePath.getName());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
