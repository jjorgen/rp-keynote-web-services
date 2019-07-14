package com.nationwide.nf.rp.service;

import com.nationwide.nf.rp.bean.AllDocuSignConfigurations;
import com.nationwide.nf.rp.bean.DocuSignConfiguration;
import com.nationwide.nf.rp.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.nationwide.nf.rp.data.dao.dao.JdbcDocuSignSubscriberFeedDao;

import java.util.Date;

import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:rest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DocuSignSubscriptionServiceImplTest {

    public static final String UPDATED_DIRECTORY_NAME = "updated directory";

    @Autowired
    private JdbcDocuSignSubscriberFeedDao jdbcDocuSignSubscriberFeedDao;

    @Autowired
    DocuSignSubscriptionService docuSignSubscriptionService;

    @Autowired
    DateUtil dateUtil;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDocuSignSubscription() {
        DocuSignConfiguration docuSignConfiguration = docuSignSubscriptionService.getDocuSignSubscriptionForId("1");
        assertTrue("A docusign configurations was returned", StringUtils.isNotBlank(docuSignConfiguration.getSubscriptionName()));
    }

    @Test
    public void getAllDocuSignSubscriptions() {
        AllDocuSignConfigurations allDocuSignConfigurations = docuSignSubscriptionService.getAllDocuSignSubscriptions();
        DocuSignConfiguration[] docuSignConfigurations = allDocuSignConfigurations.getDocuSignConfigurations();
        assertTrue("Docusign configurations were returned", docuSignConfigurations.length > 0);

        for (DocuSignConfiguration docuSignConfiguration : docuSignConfigurations) {
            System.out.println(docuSignConfiguration);
        }
    }

    @Test
    public void updateDocuSignConfiguration() {
        DocuSignConfiguration docuSignConfiguration = docuSignSubscriptionService.getDocuSignSubscriptionForId("1");
        docuSignConfiguration.setFileTransferDirectory(UPDATED_DIRECTORY_NAME);
        String dateAsString = dateUtil.getDateAsString(new Date());
        docuSignConfiguration.setSubscriptionBeginDate(dateAsString);
        docuSignConfiguration.setSubscriptionEndDate(dateAsString);

        int numberOfRowsUpdated = docuSignSubscriptionService.updateDocuSignConfiguration(docuSignConfiguration);
        assertTrue("Number of rows updated should be one", numberOfRowsUpdated == 1);

        DocuSignConfiguration docuSignSubscription = docuSignSubscriptionService.getDocuSignSubscription("1");
        System.out.println(docuSignConfiguration);
    }

    @Test
    public void createDocuSignConfiguration() {
        DocuSignConfiguration docuSignConfiguration = new DocuSignConfiguration();
        docuSignConfiguration.setSubscriptionName("Subscription Name");
    }

    @Test
    public void deleteDocuSignSubscription() {
        docuSignSubscriptionService.deleteDocuSignSubscription("10");
    }
}