package com.cs371group2;

import com.cs371group2.account.Account;
import com.cs371group2.concern.Concern;
import com.cs371group2.facility.Facility;
import com.cs371group2.facility.FacilityDao;
import com.googlecode.objectify.ObjectifyService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Init context listener called whenever a new instance starts up to automatically register all
 * entity classes with the objectify server.
 *
 * Created on 2017-01-19.
 */
public class InitContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectifyService.register(Concern.class);
        ObjectifyService.register(Account.class);
        ObjectifyService.register(Facility.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
