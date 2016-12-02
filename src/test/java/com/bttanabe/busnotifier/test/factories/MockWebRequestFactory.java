package com.bttanabe.busnotifier.test.factories;

import com.btanabe.busnotifier.tasks.WebRequest;
import lombok.AllArgsConstructor;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.Set;


/**
 * Created by Brian on 5/15/16.
 */
@AllArgsConstructor
public class MockWebRequestFactory implements FactoryBean<Set<String>> {

    private Map<String, String> urlMappedToPageContent;

    @Override
    public Set<String> getObject() throws Exception {
        PowerMockito.mockStatic(WebRequest.class);
        urlMappedToPageContent.forEach((url, pageContent) -> {
            BDDMockito.given(WebRequest.getPage(url)).willReturn(pageContent);
        });

        return urlMappedToPageContent.keySet();
    }

    @Override
    public Class<?> getObjectType() {
        return Set.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}